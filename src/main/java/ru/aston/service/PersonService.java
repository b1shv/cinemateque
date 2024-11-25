package ru.aston.service;

import ru.aston.model.Person;

import java.sql.SQLException;
import java.util.List;

public interface PersonService {
    void addPerson(Person person) throws SQLException;

    void updatePerson(Person person) throws SQLException;

    List<Person> findAllPersons() throws SQLException;

    void deletePerson(long id) throws SQLException;
}
