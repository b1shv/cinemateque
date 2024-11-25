package ru.aston.service.impl;

import ru.aston.exception.ConsistencyException;
import ru.aston.exception.NotFoundException;
import ru.aston.model.Film;
import ru.aston.model.Genre;
import ru.aston.repository.FilmRepository;
import ru.aston.repository.GenreRepository;
import ru.aston.repository.PersonRepository;
import ru.aston.service.FilmService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;
    private final GenreRepository genreRepository;
    private final PersonRepository personRepository;

    public FilmServiceImpl(FilmRepository filmRepository, GenreRepository genreRepository, PersonRepository personRepository) {
        this.filmRepository = filmRepository;
        this.genreRepository = genreRepository;
        this.personRepository = personRepository;
    }

    @Override
    public void addFilm(Film film) throws SQLException {
        if (filmRepository.filmExists(film)) {
            throw new ConsistencyException("Такой фильм уже есть в Синематеке");
        }
        if (!personRepository.personExists(film.getDirector().getId())) {
            throw new NotFoundException(String.format("Режиссёра с id = %d нет в Синематеке", film.getDirector().getId()));
        }
        Set<Long> missingGenres = genreRepository.genresExists(
                film.getGenres().stream()
                        .map(Genre::getId)
                        .collect(Collectors.toSet()));
        if (!missingGenres.isEmpty()) {
            throw new NotFoundException(genresNotFoundExceptionMessage(missingGenres));
        }

        film.setId(filmRepository.addFilm(film));
        filmRepository.addFilmGenres(film);
    }

    @Override
    public void updateFilm(Film film) throws SQLException {
        if (!filmRepository.filmExists(film.getId())) {
            throw new NotFoundException(String.format("Фильма с id = %d нет в Синематеке", film.getId()));
        }
        if (!personRepository.personExists(film.getDirector().getId())) {
            throw new NotFoundException(String.format("Режиссёра с id = %d нет в Синематеке", film.getDirector().getId()));
        }
        Set<Long> missingGenres = genreRepository.genresExists(
                film.getGenres().stream()
                        .map(Genre::getId)
                        .collect(Collectors.toSet()));
        if (!missingGenres.isEmpty()) {
            throw new NotFoundException(genresNotFoundExceptionMessage(missingGenres));
        }

        filmRepository.updateFilm(film);
        filmRepository.deleteFilmGenres(film.getId());
        filmRepository.addFilmGenres(film);
    }

    @Override
    public Film findFilmById(long id) throws SQLException {
        if (!filmRepository.filmExists(id)) {
            throw new NotFoundException(String.format("Фильма с id = %d нет в Синематеке", id));
        }
        Film film = filmRepository.findFilmById(id);
        film.setGenres(genreRepository.findGenresByFilmId(id));
        return film;
    }

    @Override
    public List<Film> findFilmsByGenre(long genreId) throws SQLException {
        if (!genreRepository.genreExists(genreId)) {
            throw new NotFoundException(String.format("Жанра с id = %d нет в Синематеке", genreId));
        }
        List<Film> films = filmRepository.findFilmsByGenre(genreId);
        addGenres(films);
        return films;
    }

    @Override
    public List<Film> findFilmsByDirector(long directorId) throws SQLException {
        if (!personRepository.personExists(directorId)) {
            throw new NotFoundException(String.format("Режиссёра с id = %d нет в Синематеке", directorId));
        }
        List<Film> films = filmRepository.findFilmsByDirector(directorId);
        addGenres(films);
        return films;
    }

    @Override
    public void deleteFilm(long id) throws SQLException {
        if (!filmRepository.filmExists(id)) {
            throw new NotFoundException(String.format("Фильма с id = %d нет в Синематеке", id));
        }
        filmRepository.deleteFilm(id);
    }

    private String genresNotFoundExceptionMessage(Set<Long> genreIds) {
        StringBuilder builder = new StringBuilder();
        String separator = ", ";
        builder.append("Некоторых жанров нет в Синематеке: ");

        for (long genreId : genreIds) {
            builder.append("id = ");
            builder.append(genreId);
            builder.append(separator);
        }
        builder.delete(builder.length() - separator.length(), builder.length());
        return builder.toString();
    }

    private void addGenres(List<Film> films) throws SQLException {
        Map<Long, List<Genre>> genres = genreRepository.findGenresByFilms(films);

        for (Film film : films) {
            film.setGenres(genres.get(film.getId()));
        }
    }
}
