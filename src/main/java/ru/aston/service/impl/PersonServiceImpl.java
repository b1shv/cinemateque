package ru.aston.service.impl;

import ru.aston.exception.ConsistencyException;
import ru.aston.exception.NotFoundException;
import ru.aston.model.Person;
import ru.aston.repository.PersonRepository;
import ru.aston.service.PersonService;

import java.sql.SQLException;
import java.util.List;

public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void addPerson(Person person) throws SQLException {
        if (personRepository.personExists(person)) {
            throw new ConsistencyException("Человек с таким именем и датой рождения уже есть в Синематеке");
        }
        personRepository.addPerson(person);
    }

    @Override
    public void updatePerson(Person person) throws SQLException {
        if (!personRepository.personExists(person.getId())) {
            throw new NotFoundException(String.format("Человек с id = %d не найден", person.getId()));
        }
        personRepository.updatePerson(person);
    }

    @Override
    public List<Person> findAllPersons() throws SQLException {
        return personRepository.findAllPersons();
    }

    @Override
    public void deletePerson(long id) throws SQLException {
        if (!personRepository.personExists(id)) {
            throw new NotFoundException(String.format("Человек с id = %d не найден", id));
        }
        personRepository.deletePerson(id);
    }
}
