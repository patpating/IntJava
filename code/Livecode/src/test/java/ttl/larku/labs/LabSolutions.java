package ttl.larku.labs;

import org.junit.jupiter.api.Test;
import ttl.larku.app.Mapping;
import ttl.larku.app.Streams;
import ttl.larku.dao.TheFactory;
import ttl.larku.domain.Student;
import ttl.larku.service.StudentService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * @author whynot
 */
public class LabSolutions {

    /*
        Write a method to return the names of customers who have a status of Privileged. Use
        Streams .
    */
    @Test
    public void lab4() {
        StudentService service = TheFactory.studentService();
        Mapping.initStudentService(service);
        List<Student> students = service.getAllStudents();

        Set<String> names = students.stream()
                .filter(s -> s.getStatus() == Student.Status.Hibernating)
                .map(s -> s.getName())
                .collect(toSet());
    }

    /*
    5. Write a method to return a list of the ages of all Customers who have a status of
        Normal.
        */
    @Test
    public void lab5() {
        StudentService service = TheFactory.studentService();
        Mapping.initStudentService(service);
        List<Student> students = service.getAllStudents();

        List<Long> names = students.stream()
                .filter(s -> s.getStatus() == Student.Status.Hibernating)
                .map(s -> s.getDob().until(LocalDate.now(), ChronoUnit.YEARS))
                .collect(toList());
    }
    /*
6. Write a method to return the number of customers who are 20 years or older. To
    calculate number of years from a LocalDate use:
            myDate.until(LocalDate.now(), ChronoUnit.YEARS)
 */
    @Test
    public void lab6() {
        StudentService service = TheFactory.studentService();
        Mapping.initStudentService(service);
        List<Student> students = service.getAllStudents();

        long twentyOrOlder = students.stream()
                .filter(s -> s.getDob().until(LocalDate.now(), ChronoUnit.YEARS) > 20)
                .count();
    }
}
