package com.rajesh.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rajesh.modal.Book;

public interface BookRepo extends JpaRepository<Book, Long>{

}
