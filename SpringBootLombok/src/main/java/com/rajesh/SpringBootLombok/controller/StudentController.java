package com.rajesh.SpringBootLombok.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rajesh.SpringBootLombok.modal.Student;
import com.rajesh.SpringBootLombok.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private StudentService studentService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/findAll")
	public ResponseEntity<String> getAllStudents(){
		List<Student> allStudent = studentService.findAllStudent();
		return new ResponseEntity(allStudent, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/update")
	public ResponseEntity<String> update(@RequestBody Student stud){
		Student db=studentService.findById(stud.getId());
		db.setAge(stud.getAge());
		db.setStudName(stud.getStudName());
		studentService.save(db);
		return new ResponseEntity(db,HttpStatus.OK);
	}
}
