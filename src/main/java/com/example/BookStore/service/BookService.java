package com.example.BookStore.service;

import com.example.BookStore.exception.BookNotFoundException;
import com.example.BookStore.exception.InvalidSearchFiltersException;
import com.example.BookStore.model.Book;
import com.example.BookStore.model.BookListing;
import com.example.BookStore.model.BookWithDetails;
import com.example.BookStore.model.SearchFilters;
import com.example.BookStore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private BookRepository bookRepository;
    private BookDescriptionClient bookClient;

    @Autowired
    public BookService(BookRepository bookRepository,
                       /*@Qualifier("bookDescriptionLoripsum")*/ BookDescriptionClient bookClient) {
        this.bookRepository = bookRepository;
        this.bookClient = bookClient;
    }

    public BookListing getListing() {
        List<Book> bookList = bookRepository.findAll();
        return new BookListing(bookList, bookList.size());
    }

    public void addBook(Book book) {
        bookRepository.save(book);
    }

    public Book getById(String bookId) {
        // TODO fix it
//        return bookRepository.findById(bookId)
//                .orElse(new Book("1", "Super", "Ksiazka"));
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));
    }

    public void deleteAll() {
        bookRepository.deleteAll();
    }

    public void deleteById(String bookId) {
        Optional<Book> bookToRemove = bookRepository.findById(bookId);
//        if (bookToRemove.isPresent()) {
//            bookRepository.delete(bookToRemove.get());
//        }
        bookToRemove.ifPresent(book -> bookRepository.delete(book));
    }

    public BookListing getFilteredListing(SearchFilters searchFilters) {
        List<Book> bookList;

        if (searchFilters.hasAuthor() && searchFilters.hasTitle()) {
            // wyjątek
            throw new InvalidSearchFiltersException();
        }
        if (searchFilters.hasAuthor()) {
            bookList = bookRepository.findByAuthorStartsWith(searchFilters.getAuthor());
        } else if (searchFilters.hasTitle()) {
            bookList = bookRepository.findByTitleStartsWith(searchFilters.getTitle());
        } else {
            bookList = bookRepository.findAll();
        }
        return new BookListing(bookList, bookList.size());
    }

    public BookWithDetails getBookWithDetails(String bookId) {
        Book book = getById(bookId);
        return new BookWithDetails(
                book.getTitle(),
                book.getAuthor(),
                bookClient.getDescription(bookId));
    }
}
