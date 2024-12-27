package com.rajesh.SpringBootLombok.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rajesh.SpringBootLombok.modal.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long>{

}
