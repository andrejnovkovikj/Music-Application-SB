package com.example.fakefy.repositories;

import com.example.fakefy.model.Album;
import com.example.fakefy.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    Song findByTitle(String title);
    List<Song> findAllByAlbum(Album album);
}
