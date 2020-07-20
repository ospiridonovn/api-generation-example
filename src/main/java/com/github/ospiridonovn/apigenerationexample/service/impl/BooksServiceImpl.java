package com.github.ospiridonovn.apigenerationexample.service.impl;

import com.github.ospiridonovn.apigenerationexample.model.Book;
import com.github.ospiridonovn.apigenerationexample.service.BooksService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BooksServiceImpl implements BooksService {
    private final Map<Long, Book> storage = new HashMap<>();

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Book find(long id) {
        return Optional.ofNullable(storage.get(id)).orElseThrow(RuntimeException::new);
    }

    @Override
    public Book create(Book book) {
        Long newId = storage.keySet().stream().max(Long::compareTo).orElse(0L) + 1;
        book.setId(newId);
        storage.put(newId, book);
        return book;
    }

    @Override
    public Book update(long id, Book book) {
        if (!storage.containsKey(id)) throw new RuntimeException();
        return storage.put(id, book);
    }

    @Override
    public void delete(long id) {
        if (!storage.containsKey(id)) throw new RuntimeException();
        storage.remove(id);
    }
}
