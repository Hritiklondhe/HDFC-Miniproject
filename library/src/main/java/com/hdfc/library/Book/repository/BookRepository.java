package com.hdfc.library.Book.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hdfc.library.Book.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE b.title =?1")
    public List<Book> findByTitle(@Param("title") String title);

    @Query("SELECT b FROM Book b WHERE b.author =?1")
    public List<Book> findByAuthor(@Param("author") String author);

    @Query("SELECT b FROM Book b where b.subject =?1")
    public List<Book> findBySubject(@Param("subject") String subject);

}
