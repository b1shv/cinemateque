package ru.aston.repository.impl;

import ru.aston.model.Genre;
import ru.aston.repository.DataSource;
import ru.aston.repository.GenreRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenreRepositoryImpl implements GenreRepository {
    @Override
    public int addGenre(Genre genre) throws SQLException {
        String sql = "INSERT INTO genres (name, description) VALUES (?, ?)";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, genre.getName());
            statement.setString(2, genre.getDescription());
            return statement.executeUpdate();
        }
    }

    @Override
    public int updateGenre(Genre genre) throws SQLException {
        String sql = "UPDATE genres SET name = ?, description = ? WHERE id = ?";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, genre.getName());
            statement.setString(2, genre.getDescription());
            statement.setLong(3, genre.getId());
            return statement.executeUpdate();
        }
    }

    @Override
    public List<Genre> findAllGenres() throws SQLException {
        List<Genre> genres = new ArrayList<>();
        String sql = "SELECT * FROM genres";

        try (Connection connection = DataSource.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                genres.add(makeGenre(resultSet));
            }
        }
        return genres;
    }

    @Override
    public int deleteGenre(long id) throws SQLException {
        String sql = "DELETE FROM genres WHERE id = ?";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            return statement.executeUpdate();
        }
    }

    private Genre makeGenre(ResultSet resultSet) throws SQLException {
        return new Genre.Builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .build();
    }
}
