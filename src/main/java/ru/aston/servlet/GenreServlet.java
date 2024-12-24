package ru.aston.servlet;

import ru.aston.dto.GenreRequest;
import ru.aston.exception.ServletInitializationException;
import ru.aston.mapper.GenreMapper;
import ru.aston.model.Genre;
import ru.aston.service.GenreService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class GenreServlet extends AbstractServlet {
    private GenreService genreService;
    private GenreMapper genreMapper;

    @Override
    public void init() {
        super.init();

        final Object genreServiceFromContext = getServletContext().getAttribute("genreService");
        final Object genreMapperFromContext = getServletContext().getAttribute("genreMapper");

        try {
            genreService = (GenreService) genreServiceFromContext;
            genreMapper = (GenreMapper) genreMapperFromContext;
        } catch (ClassCastException e) {
            throw new ServletInitializationException("Невозможно инициализировать сервлет: " + e.getMessage());
        }
    }

    @Override
    protected void add(String requestBody) {
        genreService.addGenre(genreMapper.toGenre(gson.fromJson(requestBody, GenreRequest.class)));
    }

    @Override
    protected void update(long id, String requestBody) {
        Genre genre = genreMapper.toGenre(gson.fromJson(requestBody, GenreRequest.class), id);
        genreService.updateGenre(genre);
    }

    @Override
    protected void findAll(HttpServletResponse resp) throws IOException {
        try (PrintWriter writer = resp.getWriter()) {
            writer.write(gson.toJson(genreService.findAllGenres().stream()
                    .map(genreMapper::toGenreFullDto)
                    .toList()));
        }
    }

    @Override
    protected void findById(long id, HttpServletResponse resp) throws IOException {
        try (PrintWriter writer = resp.getWriter()) {
            writer.write(gson.toJson(genreMapper.toGenreFullDto(genreService.findGenreById(id))));
        }
    }

    @Override
    protected void delete(long id) {
        genreService.deleteGenre(id);
    }
}
