package ru.aston.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.aston.mapper.FilmMapper;
import ru.aston.mapper.GenreMapper;
import ru.aston.mapper.PersonMapper;
import ru.aston.repository.FilmRepository;
import ru.aston.repository.GenreRepository;
import ru.aston.repository.PersonRepository;
import ru.aston.repository.impl.FilmRepositoryImpl;
import ru.aston.repository.impl.GenreRepositoryImpl;
import ru.aston.repository.impl.PersonRepositoryImpl;
import ru.aston.service.FilmService;
import ru.aston.service.GenreService;
import ru.aston.service.PersonService;
import ru.aston.service.impl.FilmServiceImpl;
import ru.aston.service.impl.GenreServiceImpl;
import ru.aston.service.impl.PersonServiceImpl;
import ru.aston.servlet.util.LocalDateAdapter;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.time.LocalDate;

@WebListener
public class Listener implements ServletContextListener {
    private FilmService filmService;
    private GenreService genreService;
    private PersonService personService;
    private FilmMapper filmMapper;
    private PersonMapper personMapper;
    private GenreMapper genreMapper;
    private Gson gson;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        final ServletContext servletContext = servletContextEvent.getServletContext();

        FilmRepository filmRepository = new FilmRepositoryImpl();
        GenreRepository genreRepository = new GenreRepositoryImpl();
        PersonRepository personRepository = new PersonRepositoryImpl();

        filmService = new FilmServiceImpl(filmRepository, genreRepository, personRepository);
        genreService = new GenreServiceImpl(genreRepository);
        personService = new PersonServiceImpl(personRepository);
        filmMapper = FilmMapper.INSTANCE;
        personMapper = PersonMapper.INSTANCE;
        genreMapper = GenreMapper.INSTANCE;
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
                .create();

        servletContext.setAttribute("filmService", filmService);
        servletContext.setAttribute("genreService", genreService);
        servletContext.setAttribute("personService", personService);
        servletContext.setAttribute("filmMapper", filmMapper);
        servletContext.setAttribute("personMapper", personMapper);
        servletContext.setAttribute("genreMapper", genreMapper);
        servletContext.setAttribute("gson", gson);
    }
}
