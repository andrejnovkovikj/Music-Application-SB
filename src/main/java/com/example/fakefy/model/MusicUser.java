package com.example.fakefy.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@JsonIgnoreProperties({"playlists"})
@Table(name = "music_user")
public class MusicUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    boolean isPremium;

    @ManyToMany
    @JoinTable(
            name = "liked_songs",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id")
    )
    private Set<Song> likedSongs = new HashSet<>();


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Playlist> playlists = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "liked_albums",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "album_id")
    )
    private Set<Album> likedAlbums = new HashSet<>();

    public MusicUser(){}

    public MusicUser(String username, String email, String password, UserRole role, boolean isPremium) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.isPremium = isPremium;
        this.likedSongs = new HashSet<>();
        this.likedAlbums = new HashSet<>();
        this.playlists = new ArrayList<>();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Song> getLikedSongs() {
        return likedSongs;
    }

    public void setLikedSongs(Set<Song> likedSongs) {
        this.likedSongs = likedSongs;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public Set<Album> getLikedAlbums() {
        return likedAlbums;
    }

    public void setLikedAlbums(Set<Album> likedAlbums) {
        this.likedAlbums = likedAlbums;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
    }

}
