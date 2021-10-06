package ttl.larku.junit5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ttl.larku.app.RegistrationApp;
import ttl.larku.domain.Student;
import ttl.larku.service.StudentService;

import java.time.LocalDate;

import static java.time.Duration.ofMillis;
import static java.time.Duration.ofMinutes;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Assertion demos. Adapted from https://junit.org/junit5/docs/current/user-guide/
 */

public class Assertions {

    private final StudentService studentService = new StudentService();
    private Student student;

    @BeforeEach
    public void init() {
        RegistrationApp.init(studentService);
        student = new Student("Karl", LocalDate.of(1987, 2, 25), Student.Status.PART_TIME, "292 929 9292");
    }

    @Test
    void standardAssertions() {
        assertEquals(4, studentService.getAllStudents().size());
        studentService.createStudent("Pinky", LocalDate.of(1987, 10, 2), Student.Status.HIBERNATING, "383 939 9393");
        assertEquals(5, studentService.getAllStudents().size(),
                "The optional failure message is now the last parameter");
        assertTrue('a' < 'b', () -> "Assertion messages can be lazily evaluated -- "
                + "to avoid constructing complex messages unnecessarily.");
    }

    @Test
    void groupedAssertions() {
        // In a grouped assertion all assertions are executed, and all
        // failures will be reported together.
        assertAll("student",
                () -> assertEquals("Karl", student.getName()),
                () -> assertEquals(Student.Status.PART_TIME, student.getStatus())
        );
    }

    @Test
    void dependentAssertions() {
        // Within a code block, if an assertion fails the
        // subsequent code in the same block will be skipped.
        assertAll("properties",
                () -> {
                    String name = student.getName();
                    assertNotNull(name);

                    // Executed only if the previous assertion is valid.
                    assertAll("Student name",
                            () -> assertTrue(name.startsWith("K")),
                            () -> assertTrue(name.endsWith("l"))
                    );
                },
                () -> {
                    // Grouped assertion, so processed independently
                    // of results of first name assertions.
                    Student.Status status = student.getStatus();
                    assertNotNull(status);

                    // Executed only if the previous assertion is valid.
                    assertAll("Student status",
                            () -> assertTrue(status == Student.Status.PART_TIME),
                            () -> assertTrue(student.getPhoneNumbers().get(0).contains("292"))
                    );
                }
        );
    }

    @Test
    void exceptionTesting() {
        Exception exception = assertThrows(ArithmeticException.class, () -> {
            int x = 1 / 0;
        });
        assertEquals("/ by zero", exception.getMessage());
    }

    @Test
    void timeoutNotExceeded() {
        // The following assertion succeeds.
        assertTimeout(ofMinutes(2), () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
        });
    }

    @Test
    void timeoutNotExceededWithResult() {
        // The following assertion succeeds, and returns the supplied object.
        String actualResult = assertTimeout(ofMinutes(2), () -> {
            return "a result";
        });
        assertEquals("a result", actualResult);
    }

    @Test
    void timeoutNotExceededWithMethod() {
        // The following assertion invokes a method reference and returns an object.
        String actualGreeting = assertTimeout(ofMinutes(2), Assertions::greeting);
        assertEquals("Hello, World!", actualGreeting);
    }

    @Test
    void timeoutExceeded() {
        // The following assertion fails with an error message similar to:
        // execution exceeded timeout of 10 ms by 1991 ms
        assertTimeout(ofMillis(10), () -> {
            // Simulate task that takes more than 10 ms.
            Thread.sleep(2000);
        });

    }

    /**
     * Use this one with caution.  The task is run in a seperate thread
     * So ThreadLocal variables many not work as expected.
     */
    @Test
    void timeoutExceededWithPreemptiveTermination() {
        // The following assertion fails with an error message similar to:
        // execution timed out after 10 ms
        assertTimeoutPreemptively(ofMillis(10), () -> {
            // Simulate task that takes more than 10 ms.
            Thread.sleep(20000);
        });
    }


    private static String greeting() {
        return "Hello, World!";
    }
}
