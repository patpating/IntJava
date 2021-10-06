package ttl.larku.service;

import ttl.larku.dao.StudentDao;
import ttl.larku.domain.Student;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @author whynot
 */
public class StudentService {

    private StudentDao studentDao = new StudentDao();

    public Student createStudent(Student student) {
        if(student.getDob().until(LocalDate.now(), ChronoUnit.YEARS) < 18) {
            throw new RuntimeException("Students must be 18 or older");
        }
       return studentDao.insert(student);
    }

    public Student getStudent(int id) {
        return studentDao.get(id);
    }

    public List<Student> getAllStudents() {
        return studentDao.getAll();
    }

    public boolean updateStudent(Student student) {
        Student oldStudent = studentDao.get(student.getId());
        if(oldStudent != null) {
            return studentDao.update(student);
        }
        return false;
    }
}
