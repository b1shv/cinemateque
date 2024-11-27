package ru.aston.service.impl;

import ru.aston.exception.ConsistencyException;
import ru.aston.exception.NotFoundException;
import ru.aston.model.Person;
import ru.aston.repository.PersonRepository;
import ru.aston.service.PersonService;

import java.util.List;

public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void addPerson(Person person) {
        if (personRepository.personExists(person)) {
            throw new ConsistencyException(CONSISTENCY_MESSAGE);
        }
        personRepository.addPerson(person);
    }

    @Override
    public void updatePerson(Person person) {
        personRepository.findPersonById(person.getId())
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_MESSAGE, person.getId())));

        personRepository.updatePerson(person);
    }

    @Override
    public List<Person> findAllPersons() {
        return personRepository.findAllPersons();
    }

    @Override
    public Person findPersonById(long id) {
        return personRepository.findPersonById(id)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_MESSAGE, id)));
    }

    @Override
    public void deletePerson(long id) {
        personRepository.findPersonById(id)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_MESSAGE, id)));

        personRepository.deletePerson(id);
    }
}
