package ru.aston.service.impl;

import ru.aston.exception.ConsistencyException;
import ru.aston.exception.NotFoundException;
import ru.aston.model.Genre;
import ru.aston.repository.GenreRepository;
import ru.aston.service.GenreService;

import java.sql.SQLException;
import java.util.List;


public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public void addGenre(Genre genre) throws SQLException {
        if (genreRepository.genreExists(genre.getName())) {
            throw new ConsistencyException("Такой жанр уже есть в Синематеке");
        }
        genreRepository.addGenre(genre);

    }

    @Override
    public void updateGenre(Genre genre) throws SQLException {
        if (!genreRepository.genreExists(genre.getId())) {
            throw new NotFoundException(String.format("Не удалось найти жанр c id = %d", genre.getId()));
        }
        genreRepository.updateGenre(genre);
    }

    @Override
    public List<Genre> findAllGenres() throws SQLException {
        return genreRepository.findAllGenres();
    }

    @Override
    public void deleteGenre(long id) throws SQLException {
        if (!genreRepository.genreExists(id)) {
            throw new NotFoundException(String.format("Не удалось найти жанр c id = %d", id));
        }
        genreRepository.deleteGenre(id);
    }
}
