package ru.aston.repository;

import ru.aston.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {
    boolean personExists(Person person);

    void addPerson(Person person);

    void updatePerson(Person person);

    List<Person> findAllPersons();

    Optional<Person> findPersonById(long id);

    void deletePerson(long id);
}
