package com.example.fakefy.service;

import com.example.fakefy.model.Album;
import com.example.fakefy.model.Artist;
import com.example.fakefy.model.Song;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.List;

public interface ArtistService {
    Artist findById(Long id);
    Artist findByName(String name);
    List<Artist> findAll();
    Artist create(String name, String bio,String imageUrl);
    Artist update(Long id, String name, String bio,String imageUrl);
    Artist delete(Long id);
}