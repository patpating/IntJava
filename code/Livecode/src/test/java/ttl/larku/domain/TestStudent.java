package ttl.larku.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author whynot
 */
public class TestStudent {

    @Test
    public void testStudentListConstructor() {
//        public Student(int id, String name, LocalDate dob, Student.Status status, List<String> phoneNumbers) {
        Student student1 = new Student(1, "Joe", LocalDate.of(1950, 10, 10), Student.Status.FullTime,
                List.of("282 929 9292"));

        assertEquals("Joe", student1.getName());
    }

    @Test
    public void testStudentVarArgsConstructor() {
//        public Student(int id, String name, LocalDate dob, Student.Status status, List<String> phoneNumbers) {
        Student student1 = new Student(1, "Joe", LocalDate.of(1950, 10, 10), Student.Status.FullTime,
                "282 929 9292", "393 9393 0303");

        assertEquals("Joe", student1.getName());
        assertEquals(2, student1.getPhoneNumbers().size());
    }

    @Test
    public void testStudentZeroPhoneNumbers() {
        Student student1 = new Student(1, "Joe", LocalDate.of(1950, 10, 10), Student.Status.FullTime);

        assertEquals("Joe", student1.getName());
        assertEquals(0, student1.getPhoneNumbers().size());
    }
}
