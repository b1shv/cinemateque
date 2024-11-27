package ru.aston.service;

import ru.aston.model.Film;

import java.util.List;

public interface FilmService {
    String CONSISTENCY_MESSAGE = "Такой фильм уже есть в Синематеке";
    String NOT_FOUND_MESSAGE = "Фильма с id = %d нет в Синематеке";

    void addFilm(Film film);

    void updateFilm(Film film);

    Film findFilmById(long id);

    List<Film> findAllFilms();

    void deleteFilm(long id);
}
