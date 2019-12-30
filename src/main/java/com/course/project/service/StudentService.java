package com.course.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.course.project.domain.Student;
import com.course.project.domain.StudentRepository;
import com.course.project.exception.ResourceNotFoundException;

@Service
public class StudentService {
	
	private StudentRepository studentRepository;
	
	@Autowired
	public StudentService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}
	
	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}
	
	public Student saveStudent(Student student) {
		return studentRepository.save(student);
	}
	
	public Student updateStudent(Long studentId, Student requestStudent) {

		if (!studentRepository.existsById(studentId)) {
			throw new ResourceNotFoundException("Student not found with id " + studentId);
		}
		
		return studentRepository.findById(studentId)
				.map(student -> {
					student.setName(requestStudent.getName());
					student.setDescription(requestStudent.getDescription());
					studentRepository.save(student);
					
					return student;
				}).orElseThrow(() -> new ResourceNotFoundException("Studnet not found with id " + studentId));
	}
	
	public ResponseEntity<?> deleteStudent(Long studentId) {
		if (!studentRepository.existsById(studentId)) {
			throw new ResourceNotFoundException("Student not found with id " + studentId);
		}
		
		studentRepository.deleteById(studentId);
		return ResponseEntity.ok().build();
	}
	
}
