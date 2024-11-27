package ru.aston.repository.impl;

import ru.aston.exception.DataSourceException;
import ru.aston.model.Film;
import ru.aston.model.Genre;
import ru.aston.repository.DataSource;
import ru.aston.repository.GenreRepository;

import java.sql.*;
import java.util.*;

public class GenreRepositoryImpl implements GenreRepository {
    @Override
    public boolean genreExists(String genreName) {
        String sql = "SELECT * FROM genres WHERE name = ?";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, genreName);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new DataSourceException(e.getMessage());
        }
    }

    @Override
    public Set<Long> genresExists(Set<Long> genreIds) {
        if (genreIds.isEmpty()) {
            return genreIds;
        }
        String sql = queryWithMultipleParams("SELECT id FROM genres WHERE id IN ", genreIds.size());

        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            int i = 1;
            for (long genreId : genreIds) {
                statement.setLong(i, genreId);
                i++;
            }
            ResultSet resultSet = statement.executeQuery();
            return findMismatch(genreIds, resultSet);
        } catch (SQLException e) {
            throw new DataSourceException(e.getMessage());
        }
    }

    @Override
    public void addGenre(Genre genre) {
        String sql = "INSERT INTO genres (name, description) VALUES (?, ?)";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, genre.getName());
            statement.setString(2, genre.getDescription());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataSourceException(e.getMessage());
        }
    }

    @Override
    public void updateGenre(Genre genre) {
        String sql = "UPDATE genres SET name = ?, description = ? WHERE id = ?";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, genre.getName());
            statement.setString(2, genre.getDescription());
            statement.setLong(3, genre.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataSourceException(e.getMessage());
        }
    }

    @Override
    public Optional<Genre> findGenreById(long id) {
        String sql = "SELECT * FROM genres WHERE id = ?";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return (resultSet.next()) ? Optional.of(makeGenre(resultSet)) : Optional.empty();
        } catch (SQLException e) {
            throw new DataSourceException(e.getMessage());
        }
    }

    @Override
    public List<Genre> findAllGenres() {
        List<Genre> genres = new ArrayList<>();
        String sql = "SELECT * FROM genres";

        try (Connection connection = DataSource.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                genres.add(makeGenre(resultSet));
            }
            return genres;
        } catch (SQLException e) {
            throw new DataSourceException(e.getMessage());
        }
    }

    @Override
    public List<Genre> findGenresByFilmId(long filmId) {
        List<Genre> genres = new ArrayList<>();
        String sql = "SELECT g.* " +
                "FROM genres AS g " +
                "JOIN films_genres AS fg ON g.id = fg.genre_id " +
                "WHERE fg.film_id = ?";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, filmId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                genres.add(makeGenre(resultSet));
            }
            return genres;
        } catch (SQLException e) {
            throw new DataSourceException(e.getMessage());
        }
    }

    @Override
    public Map<Long, List<Genre>> findGenresByFilms(List<Film> films) {
        String sql = queryWithMultipleParams(
                "SELECT fg.film_id, g.id, g.name, g.description " +
                        "FROM films_genres AS fg " +
                        "JOIN genres AS g ON fg.genre_id = g.id " +
                        "WHERE fg.film_id IN ",
                films.size());

        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            for (int i = 1; i <= films.size(); i++) {
                statement.setLong(i, films.get(i - 1).getId());
            }

            Map<Long, List<Genre>> genres = new HashMap<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long filmId = resultSet.getLong("film_id");
                if (!genres.containsKey(filmId)) {
                    genres.put(filmId, new ArrayList<>());
                }
                genres.get(filmId).add(makeGenre(resultSet));
            }
            return genres;
        } catch (SQLException e) {
            throw new DataSourceException(e.getMessage());
        }
    }

    @Override
    public void deleteGenre(long id) {
        String sql = "DELETE FROM genres WHERE id = ?";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataSourceException(e.getMessage());
        }
    }

    private Genre makeGenre(ResultSet resultSet) throws SQLException {
        return new Genre.Builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .build();
    }

    private String queryWithMultipleParams(String sql, int paramsCount) {
        StringBuilder builder = new StringBuilder();
        String separator = ", ";

        builder.append(sql);
        builder.append("(");
        for (int i = 0; i < paramsCount; i++) {
            builder.append("?");
            builder.append(separator);
        }
        builder.delete(builder.length() - separator.length(), builder.length());
        builder.append(")");

        return builder.toString();
    }

    private Set<Long> findMismatch(Set<Long> ids, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            long currentId = resultSet.getLong("id");
            ids.remove(currentId);
        }
        return ids;
    }
}
