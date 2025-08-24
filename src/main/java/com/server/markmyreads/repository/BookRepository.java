package com.server.markmyreads.repository;

import com.server.markmyreads.domain.jpa.Book;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByTitleAndAuthor(@NonNull @NotBlank final String title, @NonNull @NotBlank final String author);

    @Query(value = "SELECT * FROM book b " +
            "JOIN clippings_book cb on cb.book_id = b.id " +
            "WHERE cb.clippings_id = :clippingsId",
            nativeQuery = true)
    List<Book> findByClippingsId(@NonNull @Param("clippingsId") final Long clippingsId);
}
