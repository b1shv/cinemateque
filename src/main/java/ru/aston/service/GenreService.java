package ru.aston.service;

import ru.aston.model.Genre;

import java.util.List;

public interface GenreService {
    public static final String CONSISTENCY_MESSAGE = "Такой жанр уже есть в Синематеке";
    public static final String NOT_FOUND_MESSAGE = "Не удалось найти жанр c id = %d";

    void addGenre(Genre genre);

    void updateGenre(Genre genre);

    Genre findGenreById(long id);

    List<Genre> findAllGenres();

    void deleteGenre(long id);
}
