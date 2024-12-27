package com.rajesh.SpringBootLombok.service;

import java.util.List;

import com.rajesh.SpringBootLombok.modal.Student;

public interface StudentService {

	List<Student> findAllStudent();

	Student findById(Long id);

	void save(Student db);
}
