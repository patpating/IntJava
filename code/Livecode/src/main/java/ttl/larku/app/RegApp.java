package ttl.larku.app;

import ttl.larku.dao.TheFactory;
import ttl.larku.domain.Student;
import ttl.larku.service.StudentService;

import java.time.LocalDate;
import java.util.List;

/**
 * @author whynot
 */
public class RegApp {

//    StudentService service = new StudentService();
    StudentService service = TheFactory.studentService();
    public static void main(String[] args) {
        RegApp ra = new RegApp();
        ra.postAStudent();

        ra.getRequestForAllStudents();
    }

    public void postAStudent() {
        Student student1 = new Student(1, "Joe", LocalDate.of(1950, 10, 10), Student.Status.FullTime,
                "282 929 9292", "393 9393 0303");
        service.createStudent(student1);

        List<Student> all = service.getAllStudents();
        System.out.println("all size: " + all.size());
        for(Student student : all) {
            System.out.println(student);
        }
    }

    public List<Student> getRequestForAllStudents() {

        List<Student> all = service.getAllStudents();
        System.out.println("getAll size: " + all.size());
        for(Student student : all) {
            System.out.println(student);
        }

        return all;
    }

    public static void initStudentService(StudentService service) {

        Student student1 = new Student(1, "Joe", LocalDate.of(1950, 10, 10), Student.Status.FullTime,
                "282 929 9292", "393 9393 0303");
        service.createStudent(student1);

        student1 = new Student(2, "Sammy", LocalDate.of(1978, 10, 10), Student.Status.PartTime,
                "282 929 9292", "393 9393 0303");
        service.createStudent(student1);

        student1 = new Student(3, "Manoj", LocalDate.of(2020, 10, 10), Student.Status.Hibernating,
                "282 929 9292", "393 9393 0303");
        service.createStudent(student1);

        student1 = new Student(4, "Cynthia", LocalDate.of(1988, 10, 10), Student.Status.FullTime,
                "282 929 9292", "393 9393 0303");
        service.createStudent(student1);
    }
}
