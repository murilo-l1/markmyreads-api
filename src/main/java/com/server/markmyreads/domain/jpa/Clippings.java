package com.server.markmyreads.domain.jpa;

import com.server.markmyreads.domain.enumeration.ClippingsLocale;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.time.LocalDateTime;

@Entity
@Table(name = Clippings.TABLE_NAME)
@Getter
@Setter
public class Clippings {

    protected static final String TABLE_NAME = "clippings";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "locale", nullable = false)
    private ClippingsLocale locale;

    @NotNull
    @Column(name = "uploaded_at", nullable = false)
    private LocalDateTime uploadedAt;

    @NotNull
    @Column(name = "size", nullable = false)
    private Long size;

}
