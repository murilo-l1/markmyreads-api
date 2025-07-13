package com.server.markmyreads.repository;

import com.server.markmyreads.domain.jpa.Book;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByTitleAndAuthor(@NonNull @NotBlank final String title, @NonNull @NotBlank final String author);

}
