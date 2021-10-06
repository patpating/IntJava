package ttl.larku.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ttl.larku.dao.inmemory.InMemoryStudentDAO;
import ttl.larku.domain.Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;


@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT)
public class StudentServiceMockTest {

	@Mock
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
		getStudent = new Student("Joe", LocalDate.of(1995, 5, 14), Student.Status.FULL_TIME, ("838 939 0202"));
		getStudent.setId(goodId);
		newStudent = new Student("Sammy", LocalDate.of(1995, 5, 14), Student.Status.PART_TIME, "928 749 0303");
		newStudent.setId(goodId+1);
		
		students = new ArrayList<>();
		students.add(getStudent);
		students.add(newStudent);

	}
	

	@Test
	public void getStudentGood() throws Exception{
		//Set up Mocks
		Mockito.when(dao.get(goodId)).thenReturn(getStudent);

		//Call method and do JUnit assertions
		Student result = studentService.getStudent(goodId);
		assertEquals("Joe", result.getName());

		//Do Mockito Verifications.
		Mockito.verify(dao).get(goodId);
	}
	@Test
	public void testGetStudentWithBadId() {
		//Set up Mocks
		Mockito.when(dao.get(badId)).thenReturn(null);

		//Call method and do JUnit assertions
		Student student = studentService.getStudent(badId);
		assertNull(student );

		//Do Mockito Verifications.
		Mockito.verify(dao).get(badId);
	}

	@Test
	public void testCreateStudent() {
		//Set up Mocks
		Mockito.when(dao.create(newStudent)).thenReturn(newStudent);

		//Call method and do JUnit assertions
		Student student = studentService.createStudent(newStudent);

		//Do Mockito Verifications.
		Mockito.verify(dao).create(newStudent);
	}

	@Test
	public void deleteGoodStudent() {
		//Set up Mocks
		Mockito.when(dao.get(goodId)).thenReturn(getStudent);
		Mockito.when(dao.delete(getStudent)).thenReturn(true);

		//Call method and do JUnit assertions
		boolean done = studentService.deleteStudent(goodId);
		assertTrue(done);

		//Do Mockito Verifications.
		Mockito.verify(dao).get(goodId);
		Mockito.verify(dao).delete(getStudent);
	}

	@Test
	public void deleteStudentWithBadId() {
		//Set up Mocks
		Mockito.when(dao.get(badId)).thenReturn(null);

		//Call method and do JUnit assertions
		boolean done = studentService.deleteStudent(badId);
		assertFalse(done);

		//Do Mockito Verifications.
		Mockito.verify(dao).get(badId);
		Mockito.verify(dao, never()).delete(any());
	}

	@Test
	public void testUpdateStudent() {
		//Set up Mocks
		Mockito.when(dao.get(goodId)).thenReturn(getStudent);
		Mockito.when(dao.update(getStudent)).thenReturn(true);

		//Call method and do JUnit assertions
		Student student = studentService.getStudent(goodId);

		boolean done = studentService.updateStudent(student);
		assertTrue(done);

		//Do Mockito Verifications.
		Mockito.verify(dao).update(student);
	}

	@Test
	public void testGetAll() {
		//Set up Mocks
		Mockito.when(dao.getAll()).thenReturn(students);

		//Call method and do JUnit assertions
		List<Student> students = studentService.getAllStudents();

		//Do Mockito Verifications.
		Mockito.verify(dao).getAll();
	}
}
