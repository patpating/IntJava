package ttl.larku.dao;

import ttl.larku.domain.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

/**
 * @author whynot
 */
public class InMemoryStudentDao implements StudentDao {

    private Map<Integer, Student> students = new HashMap<>();

    @Override
    public Student insert(Student student) {
       students.put(student.getId(), student);
       return student;
    }

    @Override
    public boolean delete(int id) {
        return students.remove(id) != null;
    }

    //TODO - computeIfPresent
    @Override
    public boolean update(Student student) {
        Student oldStudent = students.get(student.getId());
        if(oldStudent != null) {
            students.put(student.getId(), student);
            return true;
        }
        return false;
    }

    @Override
    public Student get(int id) {
        return students.get(id);
    }

    @Override
    public List<Student> getAll() {
//        return students.values();
//        return (List<Student>)students.values();
        return new ArrayList<>(students.values());
    }
}
