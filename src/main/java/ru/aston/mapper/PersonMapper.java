package ru.aston.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.aston.dto.PersonFullDto;
import ru.aston.dto.PersonRequest;
import ru.aston.dto.PersonShortDto;
import ru.aston.model.Person;

@Mapper
public interface PersonMapper {
    public PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    Person toPerson(PersonRequest personRequest);

    PersonShortDto toPersonShortDto(Person person);

    PersonFullDto toPersonFullDto(Person person);

    default Person toPerson(PersonRequest personRequest, long id) {
        Person person = toPerson(personRequest);
        person.setId(id);
        return person;
    }
}
