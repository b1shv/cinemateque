package ru.aston;

import ru.aston.repository.impl.GenreRepositoryImpl;
import ru.aston.service.GenreService;
import ru.aston.service.impl.GenreServiceImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        GenreService service = new GenreServiceImpl(new GenreRepositoryImpl());
        System.out.println(service.findGenreById(77));
    }


}