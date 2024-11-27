package ru.aston.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.aston.dto.GenreFullDto;
import ru.aston.dto.GenreRequest;
import ru.aston.dto.GenreShortDto;
import ru.aston.model.Genre;

@Mapper
public interface GenreMapper {
    public GenreMapper INSTANCE = Mappers.getMapper(GenreMapper.class);

    GenreFullDto toGenreFullDto(Genre genre);

    GenreShortDto toShortDto(Genre genre);

    Genre toGenre(GenreRequest genreRequest);

    default Genre toGenre(GenreRequest genreRequest, long id) {
        Genre genre = toGenre(genreRequest);
        genre.setId(id);
        return genre;
    }
}
