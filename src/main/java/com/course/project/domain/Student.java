package com.course.project.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "students")
public class Student {

	@Id
	@GeneratedValue(generator = "question_generator")
	@SequenceGenerator(
		name = "question_generator",
		sequenceName = "question_sequence",
		initialValue = 1000
	)
	private Long id;

	@NotBlank
	@Size(min = 3, max = 100)
	private String name;
	
	@Column(columnDefinition = "text")
	private String description;

	public Student() {
		
	}
	
	public Student(@NotBlank @Size(min = 3, max = 100) String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
