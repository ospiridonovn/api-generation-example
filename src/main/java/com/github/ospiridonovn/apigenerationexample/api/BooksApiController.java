package com.github.ospiridonovn.apigenerationexample.api;

import com.github.ospiridonovn.apigenerationexample.model.Book;
import com.github.ospiridonovn.apigenerationexample.service.BooksService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
public class BooksApiController implements BooksApi {
    private final BooksService booksService;

    public BooksApiController(BooksService booksService) {
        this.booksService = booksService;
    }

    @Override
    public ResponseEntity<Book> createNewBook(@Valid Book book) {
        return ResponseEntity.ok(booksService.create(book));
    }

    @Override
    public ResponseEntity<Void> deleteBook(@Min(1L) Long id) {
        booksService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(booksService.findAll());
    }

    @Override
    public ResponseEntity<Book> getOneBook(@Min(1L) Long id) {
        return ResponseEntity.ok(booksService.find(id));
    }

    @Override
    public ResponseEntity<Book> updateBook(@Min(1L) Long id, @Valid Book book) {
        return ResponseEntity.ok(booksService.update(id, book));
    }

}
