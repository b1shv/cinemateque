package ru.aston.service;

import ru.aston.model.Person;

import java.util.List;

public interface PersonService {
    void addPerson(Person person);

    void updatePerson(Person person);

    List<Person> findAllPersons();

    void deletePerson(long id);
}
