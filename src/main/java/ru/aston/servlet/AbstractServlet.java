package ru.aston.servlet;

import com.google.gson.Gson;
import ru.aston.exception.ServletInitializationException;
import ru.aston.exception.ValidationException;
import ru.aston.mapper.FilmMapper;
import ru.aston.mapper.GenreMapper;
import ru.aston.mapper.PersonMapper;
import ru.aston.service.FilmService;
import ru.aston.service.GenreService;
import ru.aston.service.PersonService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public abstract class AbstractServlet extends HttpServlet {
    protected static final String CONTENT_TYPE = "application/json";

    protected FilmService filmService;
    protected PersonService personService;
    protected GenreService genreService;
    protected FilmMapper filmMapper;
    protected PersonMapper personMapper;
    protected GenreMapper genreMapper;
    protected Gson gson;

    @Override
    public void init() {
        final Object filmServiceFromContext = getServletContext().getAttribute("filmService");
        final Object personServiceFromContext = getServletContext().getAttribute("personService");
        final Object genreServiceFromContext = getServletContext().getAttribute("genreService");
        final Object filmMapperFromContext = getServletContext().getAttribute("filmMapper");
        final Object personMapperFromContext = getServletContext().getAttribute("personMapper");
        final Object genreMapperFromContext = getServletContext().getAttribute("genreMapper");
        final Object gsonFromContext = getServletContext().getAttribute("gson");

        try {
            filmService = (FilmService) filmServiceFromContext;
            personService = (PersonService) personServiceFromContext;
            genreService = (GenreService) genreServiceFromContext;
            filmMapper = (FilmMapper) filmMapperFromContext;
            personMapper = (PersonMapper) personMapperFromContext;
            genreMapper = (GenreMapper) genreMapperFromContext;
            gson = (Gson) gsonFromContext;
        } catch (ClassCastException e) {
            throw new ServletInitializationException("Невозможно инициализировать сервлет: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        add(requestBody);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        if (path == null) {
            throw new ValidationException("Отсутствует id");
        }
        long id;
        try {
            id = Long.parseLong(path.split("/")[1]);
        } catch (NumberFormatException e) {
            throw new ValidationException("Некорректный id");
        }

        update(id, req.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(CONTENT_TYPE);
        String path = req.getPathInfo();
        if (path == null || path.length() == 1) {
            findAll(resp);
        } else {
            findById(Long.parseLong(path.split("/")[1]), resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getPathInfo().split("/")[1]);
        delete(id);
    }

    protected abstract void add(String requestBody);

    protected abstract void update(long id, String requestBody);

    protected abstract void findAll(HttpServletResponse resp) throws IOException;

    protected abstract void findById(long id, HttpServletResponse resp) throws IOException;

    protected abstract void delete(long id);
}
