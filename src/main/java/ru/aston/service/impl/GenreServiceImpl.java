package ru.aston.service.impl;

import ru.aston.exception.ConsistencyException;
import ru.aston.exception.NotFoundException;
import ru.aston.model.Genre;
import ru.aston.repository.GenreRepository;
import ru.aston.service.GenreService;

import java.util.List;


public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public void addGenre(Genre genre) {
        if (genreRepository.genreExists(genre.getName())) {
            throw new ConsistencyException(CONSISTENCY_MESSAGE);
        }
        genreRepository.addGenre(genre);
    }

    @Override
    public void updateGenre(Genre genre) {
        genreRepository.findGenreById(genre.getId())
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_MESSAGE, genre.getId())));
        genreRepository.updateGenre(genre);
    }

    @Override
    public Genre findGenreById(long id) {
        return genreRepository.findGenreById(id)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_MESSAGE, id)));
    }

    @Override
    public List<Genre> findAllGenres() {
        return genreRepository.findAllGenres();
    }

    @Override
    public void deleteGenre(long id) {
        genreRepository.findGenreById(id)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_MESSAGE, id)));
        genreRepository.deleteGenre(id);
    }
}
