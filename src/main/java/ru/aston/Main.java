package ru.aston;

import ru.aston.repository.impl.FilmRepositoryImpl;
import ru.aston.repository.impl.GenreRepositoryImpl;
import ru.aston.repository.impl.PersonRepositoryImpl;
import ru.aston.service.FilmService;
import ru.aston.service.impl.FilmServiceImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        FilmService filmService = new FilmServiceImpl(new FilmRepositoryImpl(), new GenreRepositoryImpl(), new PersonRepositoryImpl());
        System.out.println(filmService.findFilmsByDirector(1L));

    }


}