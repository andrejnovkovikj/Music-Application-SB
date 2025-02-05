package com.example.fakefy.model.dto;

import com.example.fakefy.model.Artist;
import com.example.fakefy.model.Genre;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

public class AlbumDto {

    private Long id;

    private String title;

    private LocalDate dateCreated;
    private String imageUrl;

    private Long artistId;

    private Genre genre;

    public AlbumDto() {}

    public AlbumDto(String title, LocalDate dateCreated, String imageUrl, Long artistId, Genre genre) {
        this.title = title;
        this.dateCreated = dateCreated;
        this.imageUrl = imageUrl;
        this.artistId = artistId;
        this.genre = genre;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
