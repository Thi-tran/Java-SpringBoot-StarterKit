package com.course.project.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.course.project.domain.Student;
import com.course.project.domain.StudentRepository;
import com.course.project.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;

@RunWith(SpringRunner.class)
@WebMvcTest(value = StudentService.class)
class StudentServiceTest {
	
	@MockBean
	private StudentRepository studentRepository;
	
	@Autowired
	private StudentService studentService;
	
	Student mockStudent = new Student("Anh Thi", "Dep Trai");
	String exampleStudent = "{\"name\":\"Thi\",\"description\":\"Dep Trai\"}";
	
	@BeforeEach
	void setUp() {
		mockStudent.setId(new Long(1));
	}
	
	@Test 
	public void getAllStudents() throws Exception{		
		// Given
		List<Student> studentList = new ArrayList<Student>();
		studentList.add(mockStudent);

		// Mock Repository  
		Mockito.when(studentRepository.findAll()).thenReturn(studentList);
		
		// Test
		List<Student> allStudent = studentService.getAllStudents();
		assertEquals(allStudent, studentList);
	}
	
	@Test
	public void saveStudent() throws Exception{
		Mockito.when(studentRepository.save(Mockito.any(Student.class))).thenReturn(mockStudent);
		
		Student createStudent = studentService.saveStudent(mockStudent);
		assertEquals(createStudent, mockStudent);
	}
	
	@Test
	public void updateNonExistedStudentThrowException() {
		Mockito.when(studentRepository.existsById(Mockito.anyLong())).thenReturn(false);
		
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> studentService.updateStudent(new Long(1), mockStudent));
		
		assertEquals("Student not found with id 1", exception.getMessage());
	}
	
	@Test 
	public void updateStudent() {
		Student requestStudent = new Student("Anh Thi2", "Rat dep trai");

		// Mocking Repository
		Optional<Student> optionStudent = Optional.of(mockStudent);
	
		Mockito.when(studentRepository.existsById(Mockito.anyLong())).thenReturn(true);
		Mockito.when(studentRepository.findById(Mockito.anyLong())).thenReturn(optionStudent);
		Mockito.when(studentRepository.save(Mockito.any(Student.class))).thenReturn(requestStudent);

		// Tests
		Student updateStudent = studentService.updateStudent(new Long(1), requestStudent);
		
		assertEquals(updateStudent.getDescription(), requestStudent.getDescription());
		assertEquals(updateStudent.getName(), requestStudent.getName());	
	}
	
	@Test
	public void deleteNonExistedStudentThrowException() {
		Mockito.when(studentRepository.existsById(Mockito.anyLong())).thenReturn(false);
		
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> studentService.deleteStudent(new Long(1)));
		
		assertEquals("Student not found with id 1", exception.getMessage());
	}
	
	@Test
	public void deleteStudent() {
		Mockito.when(studentRepository.existsById(Mockito.anyLong())).thenReturn(true);
		Mockito.doNothing().when(studentRepository).deleteById(Mockito.anyLong());
		
		ResponseEntity<?> response = studentService.deleteStudent(mockStudent.getId());
		
		assertEquals(response.getStatusCodeValue(), HttpStatus.OK.value());
	}
	
}
