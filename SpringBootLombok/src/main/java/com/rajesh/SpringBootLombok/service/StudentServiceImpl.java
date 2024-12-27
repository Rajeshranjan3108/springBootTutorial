package com.rajesh.SpringBootLombok.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rajesh.SpringBootLombok.modal.Student;
import com.rajesh.SpringBootLombok.repo.StudentRepo;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepo studentRepo;
	@Override
	public List<Student> findAllStudent() {
		Student stud=new Student();
		stud.setAge("23");
		System.out.println(stud.toString());
		List<Student> list=studentRepo.findAll();
		list.stream().filter(x->"Raju".equals(x.getStudName())).findAny().orElse(stud);
		return list;
	}
	@Override
	public Student findById(Long id) {
		// TODO Auto-generated method stub
		return studentRepo.findById(id).orElse(null);
	}
	@Override
	public void save(Student db) {
		// TODO Auto-generated method stub
		studentRepo.save(db);
	}

}
