package ttl.larku.app;

import ttl.larku.dao.TheFactory;
import ttl.larku.domain.Student;
import ttl.larku.service.StudentService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

/**
 * @author whynot
 */
public class Grouping {

    //    StudentService service = new StudentService();
    public static void main(String[] args) {
        Grouping ra = new Grouping();
//        ra.filtering();
        //ra.filtering2();
//        ra.flatMapping();
//        ra.toMap();
        //ra.grouping();
        ra.phoneNumbersByStudentId();

    }


    public void toMapDemo() {
        StudentService service = TheFactory.studentService();
        initStudentService(service);
        List<Student> students = service.getAllStudents();

        var studentMap = students.stream()
                .collect(Collectors.toMap(s -> s.getId(), s -> s));

//        studentMap.forEach((k, v) -> System.out.println(k + ": " + v));

        var studentsByStatus = students.stream()
                .collect(Collectors.toMap(s -> s.getStatus(),
                        s -> s,
                        (v1, v2) -> v1));

        studentsByStatus.forEach((k, v) -> System.out.println(k + ": " + v));

    }

    public void grouping() {
        StudentService service = TheFactory.studentService();
        initStudentService(service);
        List<Student> students = service.getAllStudents();

        Map<Student.Status, List<Student>> studentsByStatus = students.stream()
                .collect(Collectors.groupingBy(Student::getStatus));

        studentsByStatus.forEach((k, v) -> System.out.println(k + ": " + v));

        Map<Student.Status, Set<Student>> studentSetByStatus = students.stream()
                .collect(Collectors.groupingBy(Student::getStatus, Collectors.toSet()));

        Map<Student.Status, Long> studentCountByStatus = students.stream()
                .collect(Collectors.groupingBy(Student::getStatus, Collectors.counting()));

        studentsByStatus.forEach((k, v) -> System.out.println(k + ": " + v));

    }

    public void phoneNumbersByStudentId() {
        StudentService service = TheFactory.studentService();
        initStudentService(service);
        List<Student> students = service.getAllStudents();

        Map<Integer, List<String>> phoneNumbersByStudentId = students.stream()
                .collect(toMap(s -> s.getId(), s -> s.getPhoneNumbers()));

        phoneNumbersByStudentId.forEach((k, v) -> System.out.println(k + ": " + v));

    }


    public static void initStudentService(StudentService service) {

        Student student1 = new Student(1, "Joe", LocalDate.of(1950, 10, 10), Student.Status.FullTime,
                "282 929 9292", "393 9393 0303");
        service.createStudent(student1);

        student1 = new Student(2, "Sammy", LocalDate.of(1978, 10, 10), Student.Status.PartTime);
        service.createStudent(student1);

        student1 = new Student(3, "Manoj", LocalDate.of(2000, 10, 10), Student.Status.Hibernating,
                "282 929 9292", "999 9282828 8 0303");
        service.createStudent(student1);

        student1 = new Student(4, "Cynthia", LocalDate.of(1988, 10, 10), Student.Status.FullTime,
                "282 929 9292", "393 9393 0303", "3839 030 030303");
        service.createStudent(student1);
    }
}
