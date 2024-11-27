package ru.aston.servlet;

import ru.aston.dto.FilmRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class FilmServlet extends AbstractServlet {
    @Override
    protected void add(String requestBody) {
        filmService.addFilm(filmMapper.toFilm(gson.fromJson(requestBody, FilmRequest.class)));
    }

    @Override
    protected void update(long id, String requestBody) {
        filmService.updateFilm(filmMapper.toFilm(gson.fromJson(requestBody, FilmRequest.class), id));
    }

    @Override
    protected void findAll(HttpServletResponse resp) throws IOException {
        try (PrintWriter writer = resp.getWriter()) {
            writer.write(gson.toJson(filmService.findAllFilms().stream().map(filmMapper::toFilmShortDto).toList()));
        }
    }

    @Override
    protected void findById(long id, HttpServletResponse resp) throws IOException {
        try (PrintWriter writer = resp.getWriter()) {
            writer.write(gson.toJson(filmMapper.toFilmFullDto(filmService.findFilmById(id))));
        }
    }

    @Override
    protected void delete(long id) {
        filmService.deleteFilm(id);
    }
}
