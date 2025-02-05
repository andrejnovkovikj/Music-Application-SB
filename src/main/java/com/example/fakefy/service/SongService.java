package com.example.fakefy.service;

import com.example.fakefy.model.Album;
import com.example.fakefy.model.Artist;
import com.example.fakefy.model.Song;
import jakarta.persistence.*;

import java.util.List;

public interface SongService {

    Song findById(Long id);
    Song findByTitle(String title);
    List<Song> listAll();
    Song create(String title, String filePath, int lengthSeconds,Long albumId,Long artistId);
    Song update(Long id, String title, String filePath, int lengthSeconds,Long albumId,Long artistId);
    Song delete(Long id);

    List<Song> findAllByAlbumId(Long albumId);

}