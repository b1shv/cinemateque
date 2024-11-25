package ru.aston.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Film {
    private long id;
    private String name;
    private LocalDate releaseDate;
    private Person director;
    private List<Genre> genres;
    private String description;

    private Film(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.releaseDate = builder.releaseDate;
        this.director = builder.director;
        this.genres = builder.genres == null ? new ArrayList<>() : builder.genres;
        this.description = builder.description;
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

    public Person getDirector() {
        return director;
    }

    public void setDirector(Person director) {
        this.director = director;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
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
        Film film = (Film) o;
        return id == film.id && Objects.equals(name, film.name) && Objects.equals(releaseDate, film.releaseDate) && Objects.equals(director, film.director) && Objects.equals(genres, film.genres) && Objects.equals(description, film.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, releaseDate, director, genres, description);
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", releaseDate=" + releaseDate +
                ", director=" + director +
                ", genres=" + genres +
                ", description='" + description + '\'' +
                '}';
    }

    public static class Builder {
        private long id;
        private String name;
        private LocalDate releaseDate;
        private Person director;
        private List<Genre> genres;
        private String description;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder releaseDate(LocalDate releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public Builder director(Person director) {
            this.director = director;
            return this;
        }

        public Builder genres(List<Genre> genres) {
            this.genres = genres;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Film build() {
            return new Film(this);
        }
    }
}
