package ttl.larku.dao;

import ttl.larku.domain.Student;

import java.util.List;

/**
 * @author whynot
 */
public interface StudentDao {
    Student insert(Student student);

    boolean delete(int id);

    boolean update(Student student);

    Student get(int id);

    List<Student> getAll();
}
