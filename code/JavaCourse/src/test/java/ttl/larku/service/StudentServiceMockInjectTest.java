package ttl.larku.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ttl.larku.dao.inmemory.InMemoryStudentDAO;
import ttl.larku.domain.Student;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.internal.verification.VerificationModeFactory.atMost;


@ExtendWith(MockitoExtension.class)
public class StudentServiceMockInjectTest {

	@Spy
	private InMemoryStudentDAO dao;

	@InjectMocks
	private StudentService studentService;

	private Student getStudent;
	private Student newStudent;
	private int goodId = 1;
	private int badId = 1000;
	
	private List<Student> students;

	@BeforeEach
	public void setup() {
		getStudent = new Student("Joe", LocalDate.of(1995, 5, 14), Student.Status.FULL_TIME, "838 939 0202");
		getStudent.setId(goodId);
		newStudent = new Student("Sammy", LocalDate.of(1995, 5, 14), Student.Status.PART_TIME, "928 749 0303");
		newStudent.setId(goodId+1);

		((InMemoryStudentDAO) studentService.getStudentDAO()).createStore();
		studentService.createStudent(getStudent);
		studentService.createStudent(newStudent);

	}
	
	
	@Test
	public void getStudentGood() throws Exception{
		int idToTest = 1;
		Student result = studentService.getStudent(idToTest);
		assertTrue(result.getName().contains("Joe"));
		Mockito.verify(dao, Mockito.atLeast(1)).get(idToTest);
	}

	@Test
	public void testGetStudentWithBadId() {
		int idToTest = 14;
		Student student = studentService.getStudent(idToTest);
		
		assertNull(student);
		Mockito.verify(dao).get(idToTest);
	}

	@Test
	public void testCreateStudent() {
		Student student = studentService.createStudent("Sammy", LocalDate.of(1994, 5, 22), Student.Status.HIBERNATING, "982 749 0033");

		assertNotEquals(0, student.getId());
		Mockito.verify(dao).create(student);
	}

	@Test
	public void testCreateStudentWithStudent() {
		Student student = studentService.createStudent(newStudent);
		
		assertNotEquals(0, student.getId());
		Mockito.verify(dao, atMost(2)).create(student);
	}

	@Test
	public void deleteGoodStudent() {
		studentService.deleteStudent(goodId);
		
		Mockito.verify(dao).get(goodId);
		Mockito.verify(dao).delete(getStudent);
	}

	@Test
	public void deleteStudentWithBadId() {
		studentService.deleteStudent(badId);
		
		Mockito.verify(dao).get(badId);
	}

	@Test
	public void testUpdateStudent() {
		Student student = studentService.getStudent(goodId);
		studentService.updateStudent(student);
		
		Mockito.verify(dao).update(student);
	}

	@Test
	public void testGetAll() {
		List<Student> students = studentService.getAllStudents();
		
		assertEquals(2, students.size());
		Mockito.verify(dao).getAll();
	}
}
