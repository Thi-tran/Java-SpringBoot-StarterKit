package com.course.project.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.course.project.domain.Student;
import com.course.project.service.StudentService;

@RestController
public class StudentController {

	@Autowired
	public StudentService studentService;
	
	@GetMapping("/students")
	public List<Student> getStudents(){
		return studentService.getAllStudents();
	}
	
	@PostMapping("/student")
	public ResponseEntity<?> saveStudent(@Valid @RequestBody Student student) {
		Student newStudent = studentService.saveStudent(student);
		return new ResponseEntity<>(newStudent, HttpStatus.CREATED);
	}
	
	@PutMapping("/student/{studentId}")
	public ResponseEntity<?> updateStudent(@PathVariable Long studentId, @Valid @RequestBody Student requestStudent){
		Student updateStudent = studentService.updateStudent(studentId, requestStudent);
		return new ResponseEntity<>(updateStudent, HttpStatus.OK);
	}
	
	@DeleteMapping("/student/{studentId}")
	public ResponseEntity<?> deleteStudent(@PathVariable Long studentId){
		studentService.deleteStudent(studentId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
