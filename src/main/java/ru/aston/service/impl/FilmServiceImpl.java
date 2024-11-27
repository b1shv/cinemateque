package ru.aston.service.impl;

import ru.aston.exception.ConsistencyException;
import ru.aston.exception.NotFoundException;
import ru.aston.model.Film;
import ru.aston.model.Genre;
import ru.aston.repository.FilmRepository;
import ru.aston.repository.GenreRepository;
import ru.aston.repository.PersonRepository;
import ru.aston.service.FilmService;
import ru.aston.service.PersonService;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;
    private final GenreRepository genreRepository;
    private final PersonRepository personRepository;

    public FilmServiceImpl(FilmRepository filmRepository,
                           GenreRepository genreRepository,
                           PersonRepository personRepository) {
        this.filmRepository = filmRepository;
        this.genreRepository = genreRepository;
        this.personRepository = personRepository;
    }

    @Override
    public void addFilm(Film film) {
        if (filmRepository.filmExists(film)) {
            throw new ConsistencyException(CONSISTENCY_MESSAGE);
        }
        personRepository.findPersonById(film.getDirector().getId())
                .orElseThrow(() -> new NotFoundException(String.format(PersonService.NOT_FOUND_MESSAGE, film.getDirector().getId())));

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
    public void updateFilm(Film film) {
        filmRepository.findFilmById(film.getId())
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_MESSAGE, film.getId())));
        personRepository.findPersonById(film.getDirector().getId())
                .orElseThrow(() -> new NotFoundException(String.format(PersonService.NOT_FOUND_MESSAGE, film.getDirector().getId())));

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
    public Film findFilmById(long id) {
        Film film = filmRepository.findFilmById(id)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_MESSAGE, id)));
        film.setGenres(genreRepository.findGenresByFilmId(id));
        return film;
    }

    @Override
    public List<Film> findAllFilms() {
        return addGenres(filmRepository.findAllFilms());
    }

    @Override
    public void deleteFilm(long id) {
        filmRepository.findFilmById(id)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_MESSAGE, id)));
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

    private List<Film> addGenres(List<Film> films) {
        Map<Long, List<Genre>> genres = genreRepository.findGenresByFilms(films);

        for (Film film : films) {
            film.setGenres(genres.get(film.getId()));
        }
        return films;
    }
}
