package ru.aston.repository.impl;

import ru.aston.exception.DataSourceException;
import ru.aston.model.Person;
import ru.aston.repository.DataSource;
import ru.aston.repository.PersonRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonRepositoryImpl implements PersonRepository {
    @Override
    public boolean personExists(Person person) {
        String sql = "SELECT * FROM persons WHERE name = ? AND birthdate = ?";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, person.getName());
            statement.setTimestamp(2, Timestamp.valueOf(person.getBirthdate().atStartOfDay()));
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new DataSourceException(e.getMessage());
        }
    }

    @Override
    public void addPerson(Person person) {
        String sql = "INSERT INTO persons (name, birthdate) VALUES (?, ?)";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, person.getName());
            statement.setTimestamp(2, Timestamp.valueOf(person.getBirthdate().atStartOfDay()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataSourceException(e.getMessage());
        }
    }

    @Override
    public void updatePerson(Person person) {
        String sql = "UPDATE persons SET name = ?, birthdate = ? WHERE id = ?";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, person.getName());
            statement.setTimestamp(2, Timestamp.valueOf(person.getBirthdate().atStartOfDay()));
            statement.setLong(3, person.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataSourceException(e.getMessage());
        }
    }

    @Override
    public List<Person> findAllPersons() {
        String sql = "SELECT * FROM persons";
        List<Person> persons = new ArrayList<>();

        try (Connection connection = DataSource.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                persons.add(makePerson(resultSet));
            }
            return persons;
        } catch (SQLException e) {
            throw new DataSourceException(e.getMessage());
        }
    }

    @Override
    public Optional<Person> findPersonById(long id) {
        String sql = "SELECT * FROM persons WHERE id = ?";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() ? Optional.of(makePerson(resultSet)) : Optional.empty();
        } catch (SQLException e) {
            throw new DataSourceException(e.getMessage());
        }
    }

    @Override
    public void deletePerson(long id) {
        String sql = "DELETE FROM persons WHERE id = ?";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataSourceException(e.getMessage());
        }
    }

    private Person makePerson(ResultSet resultSet) throws SQLException {
        return new Person.Builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .birthdate(resultSet.getTimestamp("birthdate").toLocalDateTime().toLocalDate())
                .build();
    }
}
