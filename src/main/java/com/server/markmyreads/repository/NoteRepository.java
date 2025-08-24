package com.server.markmyreads.repository;

import com.server.markmyreads.domain.jpa.Note;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByBookId(@NotNull final Long bookId);

}
