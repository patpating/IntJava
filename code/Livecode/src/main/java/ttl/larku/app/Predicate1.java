package ttl.larku.app;

import ttl.larku.dao.TheFactory;
import ttl.larku.domain.Student;
import ttl.larku.service.StudentService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author whynot
 */
public class Predicate1 {

    //    StudentService service = new StudentService();
    public static void main(String[] args) {
        Predicate1 ra = new Predicate1();
//        ra.filtering();
        ra.filtering2();

    }

    public void filtering() {
        StudentService service = TheFactory.studentService();
        initStudentService(service);
        List<Student> students = service.getAllStudents();
        List<Student> r1 = getNameStartingWithM(students, "M");
        r1.forEach(System.out::println);
    }

    public void filtering2() {
        StudentService service = TheFactory.studentService();
        initStudentService(service);
        List<Student> students = service.getAllStudents();

        NameChecker nc = new NameChecker();
        OtherPropChecker oc = new OtherPropChecker();

        List<Student> r1 = slightlyBetterFilter(students, s -> s.getName().startsWith("M"));

        List<Student> r2 = slightlyBetterFilter(students, s -> s.getOtherProp().endsWith("X"));

        r1.forEach(System.out::println);
    }

    interface Checker {
        public boolean check(Student s);
    }

    public List<Student> slightlyBetterFilter(List<Student> input, Checker checker) {
        List<Student> result = new ArrayList<>();
        for(Student s : input) {
            if(checker.check(s)) {
                result.add(s);
            }
        }
        return result;
    }

    class NameChecker implements Checker {
        @Override
        public boolean check(Student s) {
            return s.getName().startsWith("M");
        }
    }

    class OtherPropChecker implements Checker {
        @Override
        public boolean check(Student s) {
            return s.getOtherProp().endsWith("M");
        }
    }

    public List<Student> getNameStartingWithM(List<Student> input, String prefix) {
        List<Student> result = new ArrayList<>();
        for(Student s : input) {
            if(s.getName().startsWith(prefix)) {
                result.add(s);
            }
        }
        return result;
    }

    public List<Student> getOtherPropStartingWith(List<Student> input, String prefix) {
        List<Student> result = new ArrayList<>();
        for(Student s : input) {
            if(s.getOtherProp().startsWith(prefix)) {
                result.add(s);
            }
        }
        return result;
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
