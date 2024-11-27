package ru.aston.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class FilmRequest {
    private String name;
    private LocalDate releaseDate;
    private long directorId;
    private List<Long> genresIds;
    private String description;

    public FilmRequest(String name, LocalDate releaseDate, long directorId, List<Long> genresIds, String description) {
        this.name = name;
        this.releaseDate = releaseDate;
        this.directorId = directorId;
        this.genresIds = genresIds;
        this.description = description;
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

    public long getDirectorId() {
        return directorId;
    }

    public void setDirectorId(long directorId) {
        this.directorId = directorId;
    }

    public List<Long> getGenresIds() {
        return genresIds;
    }

    public void setGenresIds(List<Long> genresIds) {
        this.genresIds = genresIds;
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
        FilmRequest that = (FilmRequest) o;
        return directorId == that.directorId
                && Objects.equals(name, that.name)
                && Objects.equals(releaseDate, that.releaseDate)
                && Objects.equals(genresIds, that.genresIds)
                && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, releaseDate, directorId, genresIds, description);
    }
}
