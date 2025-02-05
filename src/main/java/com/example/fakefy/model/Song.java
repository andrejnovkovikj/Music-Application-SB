package com.example.fakefy.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Data
@Entity
@Table(name = "song")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String filePath;

    private int lengthSeconds;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    public Song(){}

    public Song(String title, String filePath, int lengthSeconds) {
        this.title = title;
        this.filePath = filePath;
        this.lengthSeconds = lengthSeconds;
    }

    public Song( String title, String filePath, int lengthSeconds, Album album, Artist artist) {
        this.title = title;
        this.filePath = filePath;
        this.lengthSeconds = lengthSeconds;
        this.album = album;
        this.artist = artist;
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getLengthSeconds() {
        return lengthSeconds;
    }

    public void setLengthSeconds(int lengthSeconds) {
        this.lengthSeconds = lengthSeconds;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
