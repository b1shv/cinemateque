package ru.aston.repository;

import ru.aston.model.Genre;

import java.sql.SQLException;
import java.util.List;

public interface GenreRepository {
    int addGenre(Genre genre) throws SQLException;

    int updateGenre(Genre genre) throws SQLException;

    List<Genre> findAllGenres() throws SQLException;

    int deleteGenre(long id) throws SQLException;
}
