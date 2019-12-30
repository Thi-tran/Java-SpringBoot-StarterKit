package com.course.project.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.course.project.domain.Student;
import com.course.project.service.StudentService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = StudentController.class)
public class StudentControllerTest {

	@Autowired 
	private MockMvc mockMvc;
	
	@MockBean
	private StudentService studentService;
	
	Student mockStudent = new Student("Anh Thi", "Dep Trai");
	
	String exampleStudent = "{\"name\":\"Thi\",\"description\":\"Dep Trai\"}";
	
	@Test
	public void getAllStudents() throws Exception {
		// Given
		List<Student> studentList = new ArrayList<Student>();
		studentList.add(mockStudent);
		
		// Mock Repository
		Mockito.when(studentService.getAllStudents()).thenReturn(studentList);
		
		// Mock GET request
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/students").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		// Test
		String expected = "[{name: 'Anh Thi', description: 'Dep Trai'}]";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
		
	}
	
	@Test
	public void createStudent() throws Exception{
		Mockito.when(studentService.saveStudent(Mockito.any(Student.class))).thenReturn(mockStudent);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
											.post("/student")
											.accept(MediaType.APPLICATION_JSON).content(exampleStudent)
											.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		// Test
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
	}
	
	@Test
	public void updateStudent() throws Exception{
		Mockito.when(studentService.updateStudent(Mockito.anyLong(), Mockito.any(Student.class))).thenReturn(mockStudent);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
											.put("/student/" + 1000)
											.accept(MediaType.APPLICATION_JSON).content(exampleStudent)
											.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();

		// Test
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test 
	public void deleteStudent() throws Exception{
		Mockito.when(studentService.deleteStudent(Mockito.anyLong())).thenReturn(ResponseEntity.noContent().build());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
											.delete("/student/" + 1000)
											.accept(MediaType.APPLICATION_JSON).content(exampleStudent)
											.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();

		// Test
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
}
