package ru.aston.repository;

import ru.aston.model.Film;
import ru.aston.model.Genre;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface GenreRepository {
    boolean genreExists(String genreName) throws SQLException;

    boolean genreExists(long id) throws SQLException;

    Set<Long> genresExists(Set<Long> genreIds) throws SQLException;

    int addGenre(Genre genre) throws SQLException;

    int updateGenre(Genre genre) throws SQLException;

    List<Genre> findAllGenres() throws SQLException;

    List<Genre> findGenresByFilmId(long filmId) throws SQLException;

    Map<Long, List<Genre>> findGenresByFilms(List<Film> films) throws SQLException;

    int deleteGenre(long id) throws SQLException;
}
