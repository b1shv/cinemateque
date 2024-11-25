package ru.aston.repository;

import ru.aston.model.Person;

import java.sql.SQLException;
import java.util.List;

public interface PersonRepository {
    boolean personExists(long id) throws SQLException;

    boolean personExists(Person person) throws SQLException;

    int addPerson(Person person) throws SQLException;

    int updatePerson(Person person) throws SQLException;

    List<Person> findAllPersons() throws SQLException;

    int deletePerson(long id) throws SQLException;
}
