package com.example.fakefy.service;

import com.example.fakefy.model.Album;
import com.example.fakefy.model.Artist;
import com.example.fakefy.model.Genre;
import com.example.fakefy.model.Song;

import java.time.LocalDate;
import java.util.List;

public interface AlbumService {
    Album getAlbumById(Long id);
    Album getAlbumByTitle(String title);
    List<Album> listAll();
    Album createAlbum(String title, LocalDate dateCreated, Long artistId, Genre genre,String imageUrl);
    Album updateAlbum(Long id, String title, LocalDate dateCreated, Long artistId,Genre genre,String imageUrl);
    Album deleteAlbum(Long id);
    List<Album> findAllByArtistId(Long artistId);
}