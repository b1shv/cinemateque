package ru.aston.servlet.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ExceptionHandler {
    void handle(Exception e, HttpServletResponse resp) throws IOException;
}
