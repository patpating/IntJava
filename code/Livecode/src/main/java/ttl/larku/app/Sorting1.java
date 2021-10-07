package ttl.larku.app;

import ttl.larku.dao.TheFactory;
import ttl.larku.domain.Student;
import ttl.larku.service.StudentService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author whynot
 */
public class Sorting1 {

    //    StudentService service = new StudentService();
    public static void main(String[] args) {
        Sorting1 ra = new Sorting1();
        //ra.sorting1();
        ra.sorting2();

    }

    public static <A extends Comparable<A>> void sort(List<A> list) {
    }

    public void sorting1() {
        StudentService service = TheFactory.studentService();
        initStudentService(service);
        List<Student> students = service.getAllStudents();

        Collections.sort(students);
        for (Student s : students) {
            System.out.println(s);
        }
    }

    public static <T> void sort(List<T> list, Comparator<T> c) {
    }

    public void sorting2() {
        StudentService service = TheFactory.studentService();
        initStudentService(service);
        List<Student> students = service.getAllStudents();

        NameComparator nc = new NameComparator();
        Comparator<Student> nc2 = new Comparator<Student>() {
            @Override
            public int compare(Student student1, Student student2) {
                return student1.getName().compareTo(student2.getName());
            }
        };

        Comparator<Student> ncx = (Student student1, Student student2) -> {
                return student1.getName().compareTo(student2.getName());
            };

        //Get rid of function names etc.
        Comparator<Student> nc3 = (Student student1, Student student2) -> {
            return student1.getName().compareTo(student2.getName());
        };

        //Arguments not always needed
        Comparator<Student> nc4 = (student1, student2) -> {
            return student1.getName().compareTo(student2.getName());
        };

        //Single statement
        Comparator<Student> nc5 = (student1, student2) -> student1.getName().compareTo(student2.getName());

        Collections.sort(students, nc5);

        Collections.sort(students, (student1, student2) -> student1.getName().compareTo(student2.getName()));

        Collections.sort(students, (student1, student2) -> student1.getDob().compareTo(student2.getDob()));

        Consumer<Student> cc = new Consumer<Student>() {
            @Override
            public void accept(Student student) {
                System.out.println(student);
            }
        };

        students.forEach(this::lotsOfCode);

//        students.forEach(s -> specialPrint(s));

        OtherClass oc = new OtherClass();
        students.forEach(OtherClass::specialPrint);



        students.forEach(System.out::println);

//        for (Student s : students) {
//            System.out.println(s);
//        }

    }


    public void lotsOfCode(Student s) {
        //Lot's of code
        System.out.println(s);


























        //put into DB

    }

    interface MyInterface {
        public void doit(String s);
    }

    class NameComparator implements Comparator<Student> {
        @Override
        public int compare(Student student1, Student student2) {
            return student1.getName().compareTo(student2.getName());
        }
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
