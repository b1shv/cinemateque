package ru.aston.repository;

import ru.aston.model.Film;

import java.sql.SQLException;
import java.util.List;

public interface FilmRepository {
    boolean filmExists(long id) throws SQLException;

    boolean filmExists(Film film) throws SQLException;

    long addFilm(Film film) throws SQLException;

    int updateFilm(Film film) throws SQLException;

    Film findFilmById(long id) throws SQLException;

    List<Film> findFilmsByGenre(long genreId) throws SQLException;

    List<Film> findFilmsByDirector(long directorId) throws SQLException;

    int deleteFilm(long id) throws SQLException;

    void addFilmGenres(Film film) throws SQLException;

    void deleteFilmGenres(long filmId) throws SQLException;
}
