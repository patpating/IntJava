package ttl.larku.app;

import ttl.larku.dao.TheFactory;
import ttl.larku.domain.Student;
import ttl.larku.service.StudentService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author whynot
 */
public class Streams {

    //    StudentService service = new StudentService();
    public static void main(String[] args) {
        Streams ra = new Streams();
//        ra.filtering();
        //ra.filtering2();
        ra.streams1();

    }


    public void streams1() {
        StudentService service = TheFactory.studentService();
        initStudentService(service);
        List<Student> students = service.getAllStudents();


        List<String> namesOver40FromStreams = students.stream()
//        var x = students.stream()
                .peek(s -> System.out.println("Peek 1: " + s))
                .filter(s -> s.getDob().until(LocalDate.now(), ChronoUnit.YEARS) > 40)
                .peek(s -> System.out.println("Peek 2: " + s))
                .map(s -> s.getName())
                .peek(s -> System.out.println("Peek 3: " + s))
                .collect(toList());
//                .forEach(s -> System.out.println(s));

        String l1 = students.stream()
//        var x = students.stream()
                .filter(s -> s.getDob().until(LocalDate.now(), ChronoUnit.YEARS) > 40)
                .map(s -> s.getName())
                .collect(Collectors.joining(", "));

        System.out.println("l1: " + l1);
        //namesOver40FromStreams.forEach(System.out::println);
    }

    public void terminalOps() {
        StudentService service = TheFactory.studentService();
        initStudentService(service);
        List<Student> students = service.getAllStudents();


        long numAbove40 = students.stream()
//        var x = students.stream()
                .filter(s -> s.getDob().until(LocalDate.now(), ChronoUnit.YEARS) > 40)
                .count();
//                .forEach(s -> System.out.println(s));

        String l1 = students.stream()
//        var x = students.stream()
                .filter(s -> s.getDob().until(LocalDate.now(), ChronoUnit.YEARS) > 40)
                .map(Student::getName)
                .collect(Collectors.joining(", "));

        System.out.println("l1: " + l1);
        //namesOver40FromStreams.forEach(System.out::println);
    }

    public interface GenericExtractor<T, R> {
        public R extract(T s);
    }


    public <T> List<T> bestFilter(List<T> input, Predicate<T> checker) {
        List<T> result = new ArrayList<>();
        for (T s : input) {
            if (checker.test(s)) {
                result.add(s);
            }
        }
        return result;
    }

    public <T, R> List<R> bestExtractor(List<T> input, Function<T, R> extractor) {
        List<R> names = new ArrayList<>();
        for (T s : input) {
            names.add(extractor.apply(s));
        }
        return names;
    }

    public <T, R> List<R> betterGetProperties(List<T> input, GenericExtractor<T, R> extractor) {
        List<R> names = new ArrayList<>();
        for (T s : input) {
            names.add(extractor.extract(s));
        }
        return names;
    }

    public interface Extractor {
        public String extract(Student s);
    }

    public List<String> getProperties(List<Student> input, Extractor extractor) {
        List<String> names = new ArrayList<>();
        for (Student s : input) {
            names.add(extractor.extract(s));
        }
        return names;
    }

    public List<String> getStudentNames(List<Student> input) {
        List<String> names = new ArrayList<>();
        for (Student s : input) {
            names.add(s.getName());
        }
        return names;
    }

    public List<String> getStudentOtherProp(List<Student> input) {
        List<String> names = new ArrayList<>();
        for (Student s : input) {
            names.add(s.getOtherProp());
        }
        return names;
    }


    public static void initStudentService(StudentService service) {

        Student student1 = new Student(1, "Joe", LocalDate.of(1950, 10, 10), Student.Status.FullTime,
                "282 929 9292", "393 9393 0303");
        service.createStudent(student1);

        student1 = new Student(2, "Sammy", LocalDate.of(1978, 10, 10), Student.Status.PartTime,
                "282 929 9292", "393 9393 0303");
        service.createStudent(student1);

        student1 = new Student(3, "Manoj", LocalDate.of(2000, 10, 10), Student.Status.Hibernating,
                "282 929 9292", "393 9393 0303");
        service.createStudent(student1);

        student1 = new Student(4, "Cynthia", LocalDate.of(1988, 10, 10), Student.Status.FullTime,
                "282 929 9292", "393 9393 0303");
        service.createStudent(student1);
    }
}
