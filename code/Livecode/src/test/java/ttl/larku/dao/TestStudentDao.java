package ttl.larku.dao;

import org.junit.jupiter.api.Test;
import ttl.larku.domain.Student;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author whynot
 */
public class TestStudentDao {

    @Test
    public void testGetAll() {
        InMemoryStudentDao dao = new InMemoryStudentDao();
        Student student1 = new Student(1, "Joe", LocalDate.of(1950, 10, 10), Student.Status.FullTime,
                "282 929 9292", "393 9393 0303");
        dao.insert(student1);

        List<Student> all = dao.getAll();
        assertEquals(1, all.size());
    }

    @Test
    public void testUpdate_Student_With_With_Good_Id() {
        InMemoryStudentDao dao = new InMemoryStudentDao();
        Student student1 = new Student(1, "Joe", LocalDate.of(1950, 10, 10), Student.Status.FullTime,
                "282 929 9292", "393 9393 0303");
        dao.insert(student1);

        student1.setName("Charlie");
        boolean result = dao.update(student1);
        assertTrue(result);
    }

    @Test
    public void testUpdate_Student_With_With_Bad_Id() {
        InMemoryStudentDao dao = new InMemoryStudentDao();
        Student student1 = new Student(1, "Joe", LocalDate.of(1950, 10, 10), Student.Status.FullTime,
                "282 929 9292", "393 9393 0303");
        dao.insert(student1);

        student1.setName("Charlie");
        student1.setId(9999);
        boolean result = dao.update(student1);
        assertFalse(result);
    }
}
