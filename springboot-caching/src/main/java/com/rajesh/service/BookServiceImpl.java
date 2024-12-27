package com.rajesh.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.rajesh.modal.Book;
import com.rajesh.repo.BookRepo;

@Service
public class BookServiceImpl implements BookServiceInterface{

	private static final Logger logger=LoggerFactory.getLogger(BookServiceImpl.class);
	@Autowired
	private BookRepo bookRepo;
	
	@Override
	@Cacheable(cacheNames = "books", key = "#id")
	public Book getBookById(Long id) {
		logger.info("calling from database >>>");
		Optional<Book> book=bookRepo.findById(id);
		return book.isPresent()?book.get():null;
	}

	@Override
	public Book saveBook(Book book) {
		Book saved=bookRepo.save(book);
		return saved;
	}

	@Override
	@CachePut(cacheNames = "books", key = "#book.id")
	public Book updateBook(Book book) {
		Book dbBook=bookRepo.findById(book.getId()).orElse(null);
		if(dbBook !=null) {
			dbBook.setBookName(book.getBookName());
			return bookRepo.save(dbBook);
		}
		return null;
	}

	@Override
	@CacheEvict(cacheNames = "books", key = "#id")
	public void deleteBookById(Long id) {
		bookRepo.deleteById(id);
	}

	
}
