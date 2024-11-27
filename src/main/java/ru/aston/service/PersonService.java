package ru.aston.service;

import ru.aston.model.Person;

import java.util.List;

public interface PersonService {
    public final static String NOT_FOUND_MESSAGE = "Человек с id = %d не найден";
    public final static String CONSISTENCY_MESSAGE = "Человек с таким именем и датой рождения уже есть в Синематеке";

    void addPerson(Person person);

    void updatePerson(Person person);

    List<Person> findAllPersons();

    Person findPersonById(long id);

    void deletePerson(long id);
}
