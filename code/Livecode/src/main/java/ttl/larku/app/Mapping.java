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

/**
 * @author whynot
 */
public class Mapping {

    //    StudentService service = new StudentService();
    public static void main(String[] args) {
        Mapping ra = new Mapping();
//        ra.filtering();
        //ra.filtering2();
        ra.mapping1();

    }


    public void mapping1() {
        StudentService service = TheFactory.studentService();
        initStudentService(service);
        List<Student> students = service.getAllStudents();


        List<Student> r1 = bestFilter(students, s -> s.getName().startsWith("M"));

        List<String> names = getStudentNames(students);


        List<LocalDate> dobs = betterGetProperties(students, s -> s.getDob());
        dobs.forEach(System.out::println);

        List<Student> over40 = bestFilter(students, s -> s.getDob().until(LocalDate.now(), ChronoUnit.YEARS) > 40);
        List<String> namesOver40 = betterGetProperties(over40, s -> s.getName());

        List<String> namesOver40Also = betterGetProperties(bestFilter(students, s -> s.getDob().until(LocalDate.now(), ChronoUnit.YEARS) > 40),
                s -> s.getName());

        List<String> namesOver40FromStreams = students.stream()
                .filter(s -> s.getDob().until(LocalDate.now(), ChronoUnit.YEARS) > 40)
                .map(s -> s.getName())
                .collect(Collectors.toList());

        namesOver40FromStreams.forEach(System.out::println);

        List<String> names2 = getProperties(students, Student::getName);

        List<String> names10 = getProperties(students, this::prettyPrint);

        List<String> names3 = betterGetProperties(students, s -> s.getName());
        names3.forEach(System.out::println);
    }

    public interface Extractor{
        public String extract(Student s);
    }

    public List<String> getProperties(List<Student> input, Extractor extractor) {
        List<String> names = new ArrayList<>();
        for(Student s : input) {
            names.add(extractor.extract(s));
        }
        return names;
    }

    public String prettyPrint(Student s) {
        return s.getName();
    }

    public interface GenericExtractor<T, R>{
        public R extract(T s);
    }


    public <T> List<T> bestFilter(List<T> input, Predicate<T> checker) {
        List<T> result = new ArrayList<>();
        for(T s : input) {
            if(checker.test(s)) {
                result.add(s);
            }
        }
        return result;
    }

    public <T, R> List<R> bestExtractor(List<T> input, Function<T, R> extractor) {
        List<R> names = new ArrayList<>();
        for(T s : input) {
            names.add(extractor.apply(s));
        }
        return names;
    }

    public <T, R> List<R> betterGetProperties(List<T> input, GenericExtractor<T, R> extractor) {
        List<R> names = new ArrayList<>();
        for(T s : input) {
            names.add(extractor.extract(s));
        }
        return names;
    }


    public List<String> getStudentNames(List<Student> input) {
       List<String> names = new ArrayList<>();
       for(Student s : input) {
           names.add(s.getName());
       }
       return names;
    }

    public List<String> getStudentOtherProp(List<Student> input) {
        List<String> names = new ArrayList<>();
        for(Student s : input) {
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
