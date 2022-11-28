package com.kpi.lab.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kpi.lab.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
	List<Book> findByAuthor(String authorName);

	List<Book> findByName(String name);

	@Query("""
			SELECT b
			 FROM Book b
			 JOIN b.keywords k ON k.value = :keywordValue
			""")
	List<Book> findByKeywordIn(String keywordValue);
}
