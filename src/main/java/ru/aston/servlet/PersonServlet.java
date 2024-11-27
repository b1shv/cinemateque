package ru.aston.servlet;

import ru.aston.dto.PersonFullDto;
import ru.aston.dto.PersonRequest;
import ru.aston.model.Person;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class PersonServlet extends AbstractServlet {
    @Override
    protected void add(String requestBody) {
        personService.addPerson(personMapper.toPerson(gson.fromJson(requestBody, PersonRequest.class)));
    }

    @Override
    protected void update(long id, String requestBody) {
        personService.updatePerson(personMapper.toPerson(gson.fromJson(requestBody, PersonRequest.class), id));
    }

    @Override
    protected void findAll(HttpServletResponse resp) throws IOException {
        try (PrintWriter writer = resp.getWriter()) {
            writer.write(gson.toJson(personService.findAllPersons().stream()
                    .map(personMapper::toPersonShortDto)
                    .toList()));
        }
    }

    @Override
    protected void findById(long id, HttpServletResponse resp) throws IOException {
        try (PrintWriter writer = resp.getWriter()) {
            writer.write(gson.toJson(personMapper.toPersonFullDto(personService.findPersonById(id)), PersonFullDto.class));
        }
    }

    @Override
    protected void delete(long id) {
        personService.deletePerson(id);
    }
}
