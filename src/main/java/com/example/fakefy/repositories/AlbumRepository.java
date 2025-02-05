package com.example.fakefy.repositories;

import com.example.fakefy.model.Album;
import com.example.fakefy.model.Artist;
import com.example.fakefy.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository  extends JpaRepository<Album, Long> {
    Album findByTitle(String title);
    List<Album> findAllByArtist(Artist artist);

}
