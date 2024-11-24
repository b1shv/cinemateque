package ru.aston.service;

import ru.aston.model.Genre;

import java.util.List;

public interface GenreService {
    void addGenre(Genre genre);

    void updateGenre(Genre genre);

    List<Genre> findAllGenres();

    void deleteGenre(long id);
}
