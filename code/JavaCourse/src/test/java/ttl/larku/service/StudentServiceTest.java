package ttl.larku.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ttl.larku.domain.Student;
import ttl.larku.domain.Student.Status;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class StudentServiceTest {

	private String name1 = "Bloke";
	private String name2 = "Blokess";
	private LocalDate dob1 = LocalDate.of(1990, 2, 14);
	private LocalDate dob2 = LocalDate.of(1975, 8, 24);
	private String newName = "Karl Jung";
	private String phoneNumber1 = "290 298 4790";
	private String phoneNumber2 = "3838 939 93939";
	
	private StudentService studentService;
	
	@BeforeEach
	public void setup() {
		studentService = new StudentService();
	}
	
	@Test
	public void testCreateStudent() {
		Student newStudent = studentService.createStudent(name1, dob1, Status.FULL_TIME, phoneNumber1);
		
		Student result = studentService.getStudent(newStudent.getId());
		
		assertTrue(result.getName().contains(name1));
		assertEquals(1, studentService.getAllStudents().size());
	}
	
	@Test
	public void testDeleteStudent() {
		Student student1 = studentService.createStudent(name1, dob1, Status.FULL_TIME, phoneNumber1);
		Student student2 = new Student(name1, dob1, Status.FULL_TIME, phoneNumber1);
		student2 = studentService.createStudent(student2);
		
		assertEquals(2, studentService.getAllStudents().size());
		
		boolean done = studentService.deleteStudent(student1.getId());
		assertTrue(done);
		
		assertEquals(1, studentService.getAllStudents().size());
		assert(studentService.getAllStudents().get(0).getName().contains(name1));
	}

	@Test
	public void testDeleteNonExistentStudent() {
		Student student1 = studentService.createStudent(name1, dob1, Status.FULL_TIME, phoneNumber1);
		Student student2 = new Student(name1, dob1, Status.FULL_TIME, phoneNumber1);
		student2 = studentService.createStudent(student2);
		
		assertEquals(2, studentService.getAllStudents().size());
		
		//Non existent Id
		boolean done = studentService.deleteStudent(9999);
		assertFalse(done);
		
		assertEquals(2, studentService.getAllStudents().size());
	}
	
	@Test
	public void testUpdateStudent() {
		Student student1 = studentService.createStudent(name1, dob1, Status.FULL_TIME, phoneNumber1);
		
		assertEquals(1, studentService.getAllStudents().size());

		student1.setName(name2);
		boolean done = studentService.updateStudent(student1);
		assertTrue(done);
		
		assertEquals(1, studentService.getAllStudents().size());
		assertEquals(name2, studentService.getAllStudents().get(0).getName());
	}
}
