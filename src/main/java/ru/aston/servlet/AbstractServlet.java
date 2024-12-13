package ru.aston.servlet;

import com.google.gson.Gson;
import ru.aston.exception.ServletInitializationException;
import ru.aston.exception.ValidationException;
import ru.aston.servlet.util.ExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public abstract class AbstractServlet extends HttpServlet {
    protected ExceptionHandler exceptionHandler;
    protected Gson gson;

    protected abstract void add(String requestBody);

    protected abstract void update(long id, String requestBody);

    protected abstract void findAll(HttpServletResponse resp) throws IOException;

    protected abstract void findById(long id, HttpServletResponse resp) throws IOException;

    protected abstract void delete(long id);

    @Override
    public void init() {
        final Object exceptionHandlerFromContext = getServletContext().getAttribute("exceptionHandler");
        final Object gsonFromContext = getServletContext().getAttribute("gson");

        try {
            exceptionHandler = (ExceptionHandler) exceptionHandlerFromContext;
            gson = (Gson) gsonFromContext;
        } catch (ClassCastException e) {
            throw new ServletInitializationException("Невозможно инициализировать сервлет: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            add(requestBody);
        } catch (Exception e) {
            exceptionHandler.handle(e, resp);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            long id = readPath(req.getPathInfo());
            if (id == -1) {
                throw new ValidationException("Отсутствует id");
            }
            update(id, req.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
        } catch (Exception e) {
            exceptionHandler.handle(e, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            long id = readPath(req.getPathInfo());
            if (id == -1) {
                findAll(resp);
            } else {
                findById(id, resp);
            }
        } catch (Exception e) {
            exceptionHandler.handle(e, resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            long id = readPath(req.getPathInfo());
            if (id == -1) {
                throw new ValidationException("Отсутствует id");
            }
            delete(id);
        } catch (Exception e) {
            exceptionHandler.handle(e, resp);
        }
    }

    private long readPath(String path) {
        if (path == null || path.length() <= 1) {
            return -1;
        }

        long id;
        try {
            id = Long.parseLong(path.split("/")[1]);
        } catch (NumberFormatException e) {
            throw new ValidationException("Некорректный id");
        }
        return id;
    }
}
