package com.example.BookStore;

import com.example.BookStore.model.Book;
import com.example.BookStore.repository.BookRepository;
import com.example.BookStore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
public class BookStoreApplication implements CommandLineRunner {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private BookService bookService;

	public static void main(String[] args) {
		SpringApplication.run(BookStoreApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		System.out.println("Hello Spring");

		Book book1 = new Book("Spring 1", "Autor 2");
		Book book2 = new Book("Java", "Autor 1");
		Book book3 = new Book("Super Spring", "Autor 3");
		Book book4 = new Book("Spring in Action", "Autor 2");

		bookRepository.save(book1);
		bookRepository.save(book2);
		bookRepository.save(book3);
		bookRepository.save(book4);

		System.out.println(bookRepository.searchSpringBooks(2));
//		System.out.println(bookService.getListing());
	}
}
