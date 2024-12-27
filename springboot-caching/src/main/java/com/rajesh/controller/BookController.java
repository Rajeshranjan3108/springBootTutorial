package com.rajesh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rajesh.modal.Book;
import com.rajesh.service.BookServiceInterface;

@RestController
@RequestMapping(value="/book")
public class BookController {

	@Autowired
	private BookServiceInterface bookServiceInterface;
	@GetMapping("/find/{id}")
	public ResponseEntity<String> getBookById(@PathVariable Long id){
		Book book=bookServiceInterface.getBookById(id);
		return new ResponseEntity(book,HttpStatus.OK);
	}
	
	@PostMapping("/save")
	public ResponseEntity<String> saveBook(@RequestBody Book book){
		Book saveBook=bookServiceInterface.saveBook(book);
		return new ResponseEntity(saveBook,HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<String> updateBook(@RequestBody Book book){
		Book saveBook=bookServiceInterface.updateBook(book);
		return new ResponseEntity(saveBook,HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteBookById(@PathVariable Long id){
		bookServiceInterface.deleteBookById(id);
		return new ResponseEntity("Book deleted with id : "+id,HttpStatus.OK);
	}
}
