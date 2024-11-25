package ru.aston.repository.impl;

import ru.aston.model.Film;
import ru.aston.model.Genre;
import ru.aston.model.Person;
import ru.aston.repository.DataSource;
import ru.aston.repository.FilmRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmRepositoryImpl implements FilmRepository {
    @Override
    public boolean filmExists(long id) throws SQLException {
        String sql = "SELECT * FROM films WHERE id = ?";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }
    }

    @Override
    public boolean filmExists(Film film) throws SQLException {
        String sql = "SELECT * FROM films WHERE name = ? AND release_date = ? AND director_id = ?";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, film.getName());
            statement.setTimestamp(2, Timestamp.valueOf(film.getReleaseDate().atStartOfDay()));
            statement.setLong(3, film.getDirector().getId());
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }
    }

    @Override
    public long addFilm(Film film) throws SQLException {
        String sql = "INSERT INTO films (name, release_date, director_id, description) VALUES (?, ?, ?, ?)";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, film.getName());
            statement.setTimestamp(2, Timestamp.valueOf(film.getReleaseDate().atStartOfDay()));
            statement.setLong(3, film.getDirector().getId());
            statement.setString(4, film.getDescription());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getLong(1);
        }
    }

    @Override
    public int updateFilm(Film film) throws SQLException {
        String sql = "UPDATE films SET name = ?, release_date = ?, director_id = ?, description = ? WHERE id = ?";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, film.getName());
            statement.setTimestamp(2, Timestamp.valueOf(film.getReleaseDate().atStartOfDay()));
            statement.setLong(3, film.getDirector().getId());
            statement.setString(4, film.getDescription());
            statement.setLong(5, film.getId());
            return statement.executeUpdate();
        }
    }

    @Override
    public Film findFilmById(long id) throws SQLException {
        String sql = "SELECT f.id, f.name, f.release_date, f.description, f.director_id, p.name AS director_name, " +
                "p.birthdate AS director_birthdate " +
                "FROM films as f " +
                "JOIN persons AS p ON f.director_id = p.id " +
                "WHERE f.id = ?";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return makeFilm(resultSet);
        }
    }

    @Override
    public List<Film> findFilmsByGenre(long genreId) throws SQLException {
        List<Film> films = new ArrayList<>();
        String sql = "SELECT f.id, f.name, f.release_date, f.description, f.director_id, p.name AS director_name, " +
                "p.birthdate AS director_birthdate " +
                "FROM films as f " +
                "JOIN persons AS p ON f.director_id = p.id " +
                "JOIN films_genres AS fg ON f.id = fg.film_id " +
                "WHERE fg.genre_id = ?";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, genreId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                films.add(makeFilm(resultSet));
            }
        }
        return films;
    }

    @Override
    public List<Film> findFilmsByDirector(long directorId) throws SQLException {
        List<Film> films = new ArrayList<>();
        String sql = "SELECT f.id, f.name, f.release_date, f.description, f.director_id, p.name AS director_name, " +
                "p.birthdate AS director_birthdate " +
                "FROM films as f " +
                "JOIN persons AS p ON f.director_id = p.id " +
                "WHERE p.id = ?";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, directorId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                films.add(makeFilm(resultSet));
            }
        }
        return films;
    }

    @Override
    public int deleteFilm(long id) throws SQLException {
        String sql = "DELETE FROM films WHERE id = ?";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            return statement.executeUpdate();
        }
    }

    @Override
    public void addFilmGenres(Film film) throws SQLException {
        String sql = "INSERT INTO films_genres (film_id, genre_id) VALUES (?, ?)";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            for (Genre genre : film.getGenres()) {
                statement.setLong(1, film.getId());
                statement.setLong(2, genre.getId());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    @Override
    public void deleteFilmGenres(long filmId) throws SQLException {
        String sql = "DELETE FROM films_genres WHERE film_id = ?";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, filmId);
            statement.executeUpdate();
        }
    }

    private Film makeFilm(ResultSet resultSet) throws SQLException {
        Person director = new Person.Builder()
                .id(resultSet.getLong("director_id"))
                .name(resultSet.getString("director_name"))
                .birthdate(resultSet.getTimestamp("director_birthdate").toLocalDateTime().toLocalDate())
                .build();

        return new Film.Builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .releaseDate(resultSet.getTimestamp("release_date").toLocalDateTime().toLocalDate())
                .director(director)
                .description(resultSet.getString("description"))
                .build();
    }
}
