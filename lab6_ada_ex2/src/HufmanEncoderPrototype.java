import java.util.PriorityQueue;

public class HufmanEncoderPrototype {
    // alphabet size of extended ASCII
    private static int R = 256;

    private static class HufmanNode implements Comparable<HufmanNode> {
        char ch;
        int freq;
        HufmanNode left, right;

        HufmanNode(char ch, int freq, HufmanNode left, HufmanNode right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        /**
         *
         * @return true if the node is a leaf node
         */
        boolean isLeaf() {
            return (left == null) && (right == null);
        }

        /**
         * Compares this HufmanNode with that HufmanNode given as argument
         * Comparison of nodes is done by comparing their freq attribute.
         * We need this operation defined in the Comparable interface because
         * we will put HufmanNodes in a PriorityQueue when building the Hufman tree.
         * @param that the object to be compared.
         * @return a negative integer, zero, or a positive integer
         * as this object is less than, equal to, or greater than the specified object.
         */
        public int compareTo(HufmanNode that) {
            return this.freq - that.freq;
        }
    }

    /**
     * A CompressionResult object simulates a compressed file.
     * It contains the bits resulting after encoding and the used Huffman codes.
     * In this demo version the encoded "Bits" are a string where chars are  '1' or '0'
     * The encode operation returns as result a CompressionResult.
     * The decode operation takes as input argument a CompressionResult.
     */
    private static class CompressionResult {
        HufmanNode huffmanCodes;
        String Bits;

        public CompressionResult(String bits, HufmanNode tree) {
            this.huffmanCodes = tree;
            this.Bits = bits;
        }
    }

    /**
     * Encodes a string using Hufman codes.
     * The CompressionResult contains the encoded message and
     * the Hufman tree used to generate the codes
     *
     * @param s - the string to be encoded
     * @return a CompressionResult
     */
    public static CompressionResult encode(String s) {
        char[] input = s.toCharArray();

        // compute frequencies of characters
        int[] freq = new int[R];
        for (int i = 0; i < input.length; i++)
            freq[input[i]]++;

        // for demo purpose only displays frequency counts
        for (int i = 0; i < R; i++)
            if (freq[i] > 0)
                System.out.println("freq " + (char) i + " " + freq[i]);

        // build Huffman tree
        HufmanNode root = buildHufmanTree(freq);

        // build code table
        String[] st = new String[R];
        buildCodeTable(st, root, "");

        // use Huffman code to encode input
        StringBuilder encodedString = new StringBuilder();
        for (char c : input) {
            encodedString.append(st[c]);
        }

        CompressionResult result = new CompressionResult(encodedString.toString(), root);
        return result;
    }

    private static void buildCodeTable(String[] st, HufmanNode x, String s) {
        if (!x.isLeaf()) {
            buildCodeTable(st, x.left, s + '0');
            buildCodeTable(st, x.right, s + '1');
        } else {
            st[x.ch] = s;
            // for demo purpose prints codes
            System.out.println("Code of " + x.ch + " is " + s);
        }
    }

    /**
     * Build the Hufman tree from given frequencies
     * @param freq - the array of character frequencies
     * @return the root node of the Hufman tree
     */
    private static HufmanNode buildHufmanTree(int[] freq) {
        // initialize priority queue with singleton trees
        PriorityQueue<HufmanNode> pq = new PriorityQueue<>();
        for (char c = 0; c < R; c++)
            if (freq[c] > 0)
                pq.add(new HufmanNode(c, freq[c], null, null));
        // repeat merge two smallest trees
        while (pq.size() > 1) {
            HufmanNode left = pq.remove();
            HufmanNode right = pq.remove();
            HufmanNode parent = new HufmanNode('\0', left.freq + right.freq, left, right);
            pq.add(parent);
        }
        return pq.remove();
    }

    /**
     * Build (and print) lookup table for symbols and their Hufman codes
     * @param st - the lookup  table. st[c] contains the code for character c as a string
     * @param x - the root node of the Hufman tree
     * @param s - a buffer collecting the  path encoding  from the root to the current node
     */
    private static void printCodeTable(String[] st, HufmanNode x, String s) {
        if (!x.isLeaf()) {
            printCodeTable(st, x.left, s + '0');
            printCodeTable(st, x.right, s + '1');
        } else {
            st[x.ch] = s;
            // for demo purpose prints codes
            System.out.println("Code of " + x.ch + " is " + s);
        }
    }
    public static String decode(CompressionResult encoded) {
        StringBuilder decodedString = new StringBuilder();
        HufmanNode current = encoded.huffmanCodes;
        for (char bit : encoded.Bits.toCharArray()) {
            if (bit == '0') {
                current = current.left;
            } else {
                current = current.right;
            }

            if (current.isLeaf()) {
                decodedString.append(current.ch);
                current = encoded.huffmanCodes;
            }
        }
        return decodedString.toString();
    }


    public static void main(String[] args) {
        String original = "ABRACABABRA";
        System.out.println("Original message to be encoded: " + original);

        CompressionResult result = encode(original);

        System.out.println("Encoded message has " + result.Bits.length() + " bits");
        System.out.println("Encoded message is: " + result.Bits);

        System.out.println();

        String recovered = decode(result);
        System.out.println("Decoded message is: " + recovered);
    }
}