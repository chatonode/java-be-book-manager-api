package com.northcoders.bookmanagerapi.repository;

import com.northcoders.bookmanagerapi.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookManagerRepository extends CrudRepository<Book, Long> {
    
}
