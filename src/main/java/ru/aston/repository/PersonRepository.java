package ru.aston.repository;

import ru.aston.model.Person;

import java.sql.SQLException;
import java.util.List;

public interface PersonRepository {
    int addPerson(Person person) throws SQLException;

    int updatePerson(Person person) throws SQLException;

    Person findPersonById(long id) throws SQLException;

    List<Person> findAllPersons() throws SQLException;

    int deletePerson(long id) throws SQLException;
}
