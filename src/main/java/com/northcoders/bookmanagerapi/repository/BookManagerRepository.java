package com.northcoders.bookmanagerapi.repository;

import com.northcoders.bookmanagerapi.model.Book;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookManagerRepository extends CrudRepository<Book, Long> {

    
}
