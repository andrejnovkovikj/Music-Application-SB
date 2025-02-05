package com.example.fakefy.repositories;

import com.example.fakefy.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist,Long> {
    Playlist findByName(String name);
}
