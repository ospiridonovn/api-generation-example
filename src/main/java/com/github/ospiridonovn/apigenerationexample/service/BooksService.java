package com.github.ospiridonovn.apigenerationexample.service;

import com.github.ospiridonovn.apigenerationexample.model.Book;

import java.util.List;

public interface BooksService {
    List<Book> findAll();

    Book find(long id);

    Book create(Book book);

    Book update(long id, Book book);

    void delete(long id);
}
