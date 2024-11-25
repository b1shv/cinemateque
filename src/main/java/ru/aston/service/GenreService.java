package ru.aston.service;

import ru.aston.model.Genre;

import java.sql.SQLException;
import java.util.List;

public interface GenreService {
    void addGenre(Genre genre) throws SQLException;

    void updateGenre(Genre genre) throws SQLException;

    List<Genre> findAllGenres() throws SQLException;

    void deleteGenre(long id) throws SQLException;
}
