package ru.aston.service;

import ru.aston.model.Film;

import java.util.List;

public interface FilmService {
    void addFilm(Film film);

    void updateFilm(Film film);

    Film findFilmById(long id);

    List<Film> findFilmsByGenre(long genreId);

    List<Film> findFilmsByDirector(long directorId);

    void deleteFilm(long id);
}
