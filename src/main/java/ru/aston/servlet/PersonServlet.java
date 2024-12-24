package ru.aston.servlet;

import ru.aston.dto.PersonFullDto;
import ru.aston.dto.PersonRequest;
import ru.aston.exception.ServletInitializationException;
import ru.aston.mapper.PersonMapper;
import ru.aston.service.PersonService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class PersonServlet extends AbstractServlet {
    private PersonService personService;
    private PersonMapper personMapper;

    @Override
    public void init() {
        super.init();

        final Object personServiceFromContext = getServletContext().getAttribute("personService");
        final Object personMapperFromContext = getServletContext().getAttribute("personMapper");

        try {
            personService = (PersonService) personServiceFromContext;
            personMapper = (PersonMapper) personMapperFromContext;
        } catch (ClassCastException e) {
            throw new ServletInitializationException("Невозможно инициализировать сервлет: " + e.getMessage());
        }
    }

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
                    .map(personMapper::toPersonFullDto)
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
