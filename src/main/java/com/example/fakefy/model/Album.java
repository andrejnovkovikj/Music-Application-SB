package com.example.fakefy.model;

import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

@AllArgsConstructor
@Data
@Entity
@Table(name = "album")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDate dateCreated;

    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @Enumerated(EnumType.STRING)
    private Genre genre;


    public Album(String title, LocalDate dateCreated, String imageUrl, Artist artist, Genre genre) {
        this.title = title;
        this.dateCreated = dateCreated;
        this.imageUrl = imageUrl;
        this.artist = artist;
        this.genre = genre;
    }

    public Album(){}


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

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
