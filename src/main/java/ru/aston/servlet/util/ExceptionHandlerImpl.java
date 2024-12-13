package ru.aston.servlet.util;

import com.google.gson.Gson;
import ru.aston.exception.ConsistencyException;
import ru.aston.exception.NotFoundException;
import ru.aston.exception.ValidationException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ExceptionHandlerImpl implements ExceptionHandler {
    private final static int NOT_FOUND = 404;
    private final static int BAD_REQUEST = 400;
    private final static int INTERNAL_SERVER_ERROR = 500;

    private final Gson gson;

    public ExceptionHandlerImpl(Gson gson) {
        this.gson = gson;
    }

    @Override
    public void handle(Exception e, HttpServletResponse resp) throws IOException {
        if (e instanceof NotFoundException) {
            resp.setStatus(NOT_FOUND);
        } else if (e instanceof ValidationException || e instanceof ConsistencyException) {
            resp.setStatus(BAD_REQUEST);
        } else {
            resp.setStatus(INTERNAL_SERVER_ERROR);
        }

        try (PrintWriter writer = resp.getWriter()) {
            writer.write(gson.toJson(e.getMessage()));
        }
    }
}
