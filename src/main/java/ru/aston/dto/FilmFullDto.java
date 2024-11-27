package ru.aston.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class FilmFullDto {
    private long id;
    private String name;
    private LocalDate releaseDate;
    private PersonShortDto director;
    private List<GenreShortDto> genres;
    private String description;

    public FilmFullDto(long id, String name, LocalDate releaseDate, PersonShortDto director,
                       List<GenreShortDto> genres, String description) {
        this.id = id;
        this.name = name;
        this.releaseDate = releaseDate;
        this.director = director;
        this.genres = genres;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public PersonShortDto getDirector() {
        return director;
    }

    public void setDirector(PersonShortDto director) {
        this.director = director;
    }

    public List<GenreShortDto> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreShortDto> genres) {
        this.genres = genres;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmFullDto that = (FilmFullDto) o;
        return id == that.id
                && Objects.equals(name, that.name)
                && Objects.equals(releaseDate, that.releaseDate)
                && Objects.equals(director, that.director)
                && Objects.equals(genres, that.genres)
                && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, releaseDate, director, genres, description);
    }
}
