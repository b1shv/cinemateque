package ru.aston.repository.impl;

import ru.aston.model.Person;
import ru.aston.repository.DataSource;
import ru.aston.repository.PersonRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonRepositoryImpl implements PersonRepository {
    @Override
    public int addPerson(Person person) throws SQLException {
        String sql = "INSERT INTO persons (name, birthdate) VALUES (?, ?)";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, person.getName());
            statement.setTimestamp(2, Timestamp.valueOf(person.getBirthdate().atStartOfDay()));
            return statement.executeUpdate();
        }
    }

    @Override
    public int updatePerson(Person person) throws SQLException {
        String sql = "UPDATE persons SET name = ?, birthdate = ? WHERE id = ?";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, person.getName());
            statement.setTimestamp(2, Timestamp.valueOf(person.getBirthdate().atStartOfDay()));
            statement.setLong(3, person.getId());
            return statement.executeUpdate();
        }
    }

    @Override
    public Person findPersonById(long id) throws SQLException {
        String sql = "SELECT * FROM persons WHERE id = ?";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return makePerson(resultSet);
        }
    }

    @Override
    public List<Person> findAllPersons() throws SQLException {
        String sql = "SELECT * FROM persons";
        List<Person> persons = new ArrayList<>();

        try (Connection connection = DataSource.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                persons.add(makePerson(resultSet));
            }
        }
        return persons;
    }

    @Override
    public int deletePerson(long id) throws SQLException {
        String sql = "DELETE FROM persons WHERE id = ?";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            return statement.executeUpdate();
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
