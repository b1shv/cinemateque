package ru.aston.repository;

import ru.aston.model.Film;

import java.sql.SQLException;
import java.util.List;

public interface FilmRepository {
    int addFilm(Film film) throws SQLException;

    int updateFilm(Film film) throws SQLException;

    Film findFilmById(long id) throws SQLException;

    List<Film> findAllFilms() throws SQLException;

    int deleteFilm(long id) throws SQLException;
}
