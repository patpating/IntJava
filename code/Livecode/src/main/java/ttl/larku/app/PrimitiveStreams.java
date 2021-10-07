package ttl.larku.app;

import ttl.larku.dao.TheFactory;
import ttl.larku.domain.Student;
import ttl.larku.service.StudentService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author whynot
 */
public class PrimitiveStreams {

    //    StudentService service = new StudentService();
    public static void main(String[] args) {
        PrimitiveStreams ra = new PrimitiveStreams();
//        ra.filtering();
        //ra.filtering2();
//        ra.flatMapping();
        ra.primitiveStreams();

    }


    public void primitiveStreams() {
        StudentService service = TheFactory.studentService();
        initStudentService(service);
        List<Student> students = service.getAllStudents();

        var x = students.stream()
                .mapToLong(s -> s.getDob().until(LocalDate.now(), ChronoUnit.YEARS))
                        .average();

        var y = students.stream()
                .mapToLong(s -> s.getDob().until(LocalDate.now(), ChronoUnit.YEARS))
                .reduce((left, right) -> left + right);

        var z = students.stream()
                .mapToLong(s -> s.getDob().until(LocalDate.now(), ChronoUnit.YEARS))
                .reduce(0, (accumulator, newElement) -> accumulator + newElement);

        System.out.println("x: " + x);

    }

    public void getOnePhoneNumber() {
        StudentService service = TheFactory.studentService();
        initStudentService(service);
        List<Student> students = service.getAllStudents();

        var x = students.stream()
                .peek(s -> System.out.println("Peek 1: " + s))
                .filter(s -> s.getPhoneNumbers().size() > 0)
                .map(s -> s.getPhoneNumbers().get(0))
                .peek(s -> System.out.println("Peek 2: " + s))
                .collect(toList());

        var y = students.stream()
                .map(s -> s.getPhoneNumbers().stream().findFirst())
                .filter(opt -> opt.isPresent())
                .map(opt -> opt.get())
                .collect(toList());

        var z = students.stream()
                .flatMap(s -> s.getPhoneNumbers().stream().findFirst().stream())
                .collect(toList());
    }

    public void getFirstStudentWithStatusFullTime() {
        StudentService service = TheFactory.studentService();
        initStudentService(service);
        List<Student> students = service.getAllStudents();

        var studentOptional = students.stream()
                .filter(s -> s.getPhoneNumbers().size() > 10)
                .findFirst();

        Student s1 = studentOptional.orElse(new Student());
        Student s3 = studentOptional.orElse(null);

        Student s2 = studentOptional.orElseGet(() -> new Student());

        students.stream()
                .filter(s -> s.getPhoneNumbers().size() > 10)
                .findFirst().ifPresentOrElse(s -> System.out.println(s),
                        () -> System.out.println("No Result"));

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
