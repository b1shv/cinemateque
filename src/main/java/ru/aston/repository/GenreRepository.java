package ru.aston.repository;

import ru.aston.model.Film;
import ru.aston.model.Genre;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface GenreRepository {
    boolean genreExists(String genreName);

    Set<Long> genresExists(Set<Long> genreIds);

    void addGenre(Genre genre);

    void updateGenre(Genre genre);

    Optional<Genre> findGenreById(long id);

    List<Genre> findAllGenres();

    List<Genre> findGenresByFilmId(long filmId);

    Map<Long, List<Genre>> findGenresByFilms(List<Film> films);

    void deleteGenre(long id);
}
