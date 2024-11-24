package ru.aston;

import ru.aston.repository.GenreRepository;
import ru.aston.repository.impl.GenreRepositoryImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        GenreRepository repository = new GenreRepositoryImpl();
        System.out.println(repository.findAllGenres());
    }


}