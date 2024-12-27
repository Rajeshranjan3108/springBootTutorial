package com.rajesh.service;

import com.rajesh.modal.Book;


public interface BookServiceInterface {

	Book getBookById(Long id);

	Book saveBook(Book book) ;
	
	Book updateBook(Book book);

	void deleteBookById(Long id);

}
