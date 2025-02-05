package com.example.fakefy.repositories;

import com.example.fakefy.model.MusicUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MusicUserRepository extends JpaRepository<MusicUser, Long> {
    MusicUser findByUsername(String name);
    Optional<MusicUser> findUserByUsername(String name);
}
