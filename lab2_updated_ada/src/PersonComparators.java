
import java.util.Comparator;
    public class PersonComparators {
        public static Comparator<Person> byLastName() {
            return Comparator.comparing(Person::getLastName);
        }

        public static Comparator<Person> byFirstName() {
            return Comparator.comparing(Person::getFirstName);
        }

        public static Comparator<Person> byAge() {
            return Comparator.comparingInt(Person::getAge);
        }
    }
