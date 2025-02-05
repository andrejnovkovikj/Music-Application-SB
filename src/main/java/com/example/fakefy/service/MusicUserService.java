package com.example.fakefy.service;

import com.example.fakefy.model.Album;
import com.example.fakefy.model.MusicUser;
import com.example.fakefy.model.Song;
import com.example.fakefy.model.UserRole;

import java.util.List;
import java.util.Set;

public interface MusicUserService {
    MusicUser findByUsername(String username);
    MusicUser findById(Long id);
    List<MusicUser> listAll();
    MusicUser create(String username, String email, String password, UserRole role, boolean isPremium);
    MusicUser update(Long id,String username, String email, String password, UserRole role, boolean isPremium);
    MusicUser delete(Long id);
    void likeSong(String username, Long songId);
    void likeAlbum(String username, Long albumId);
    void unlikeSong(String username, Long songId);
    void unlikeAlbum(String username, Long albumId);
    MusicUser getCurrentUser();
    Set<Album> getLikedAlbumsByUsername(String username);
    Set<Song> getLikedSongsByUsername(String username);
    Set<Album> getLikedAlbumsByUserId(Long id);
}
