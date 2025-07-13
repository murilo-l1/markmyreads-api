package com.server.markmyreads.repository;

import com.server.markmyreads.domain.jpa.Clippings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClippingsRepository extends JpaRepository<Clippings, Long> {
}
