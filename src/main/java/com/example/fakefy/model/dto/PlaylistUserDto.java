package com.example.fakefy.model.dto;

import com.example.fakefy.model.MusicUser;
import com.example.fakefy.model.Song;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import java.util.ArrayList;
import java.util.List;

public class PlaylistUserDto {
    private Long id;

    private String name;

    private MusicUser user;

    private String description;

    private String imageUrl;

    private List<Song> songs = new ArrayList<>();
    private String ime;

    public PlaylistUserDto(Long id, String name, MusicUser user, String description, String imageUrl, List<Song> songs, String ime) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.description = description;
        this.imageUrl = imageUrl;
        this.songs = songs;
        this.ime = ime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MusicUser getUser() {
        return user;
    }

    public void setUser(MusicUser user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }
}
