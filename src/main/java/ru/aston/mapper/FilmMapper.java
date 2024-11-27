package ru.aston.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.aston.dto.FilmFullDto;
import ru.aston.dto.FilmRequest;
import ru.aston.dto.FilmShortDto;
import ru.aston.model.Film;
import ru.aston.model.Genre;
import ru.aston.model.Person;

@Mapper
public interface FilmMapper {
    public FilmMapper INSTANCE = Mappers.getMapper(FilmMapper.class);

    FilmFullDto toFilmFullDto(Film film);

    FilmShortDto toFilmShortDto(Film film);

    default Film toFilm(FilmRequest filmRequest) {
        return new Film.Builder()
                .name(filmRequest.getName())
                .releaseDate(filmRequest.getReleaseDate())
                .genres(filmRequest.getGenresIds().stream().map(id -> new Genre.Builder().id(id).build()).toList())
                .director(new Person.Builder().id(filmRequest.getDirectorId()).build())
                .description(filmRequest.getDescription())
                .build();
    }

    default Film toFilm(FilmRequest filmRequest, long id) {
        Film film = toFilm(filmRequest);
        film.setId(id);
        return film;
    }
}
