package ru.aston.repository;

import ru.aston.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmRepository {
    boolean filmExists(Film film);

    long addFilm(Film film);

    void updateFilm(Film film);

    Optional<Film> findFilmById(long id);

    List<Film> findAllFilms();

    void deleteFilm(long id);

    void addFilmGenres(Film film);

    void deleteFilmGenres(long filmId);
}
