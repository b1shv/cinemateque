package ru.aston.servlet;

import ru.aston.dto.GenreRequest;
import ru.aston.model.Genre;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class GenreServlet extends AbstractServlet {
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
                    .map(genreMapper::toShortDto)
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
