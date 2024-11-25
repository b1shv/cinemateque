package ru.aston.service;

import ru.aston.model.Film;

import java.sql.SQLException;
import java.util.List;

public interface FilmService {
    void addFilm(Film film) throws SQLException;

    void updateFilm(Film film) throws SQLException;

    Film findFilmById(long id) throws SQLException;

    List<Film> findFilmsByGenre(long genreId) throws SQLException;

    List<Film> findFilmsByDirector(long directorId) throws SQLException;

    void deleteFilm(long id) throws SQLException;
}
