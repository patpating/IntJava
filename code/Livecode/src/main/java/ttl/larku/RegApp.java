package ttl.larku;

import ttl.larku.dao.StudentDao;
import ttl.larku.domain.Student;
import ttl.larku.service.StudentService;

import java.time.LocalDate;
import java.util.List;

/**
 * @author whynot
 */
public class RegApp {

    StudentService service = new StudentService();
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
}
