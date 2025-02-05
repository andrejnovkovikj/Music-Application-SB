package com.example.fakefy.service;

import com.example.fakefy.model.Playlist;

import java.util.List;

public interface PlaylistService {
    Playlist findById(Long id);
    Playlist findByName(String name);
    List<Playlist> listAll();
    Playlist create(String name, String description,String imageUrl,String username);
    Playlist update(Long id, String name, String description,String imageUrl);
    Playlist delete(Long id);
    void addSongToPlaylist(Long playlistId, Long songId);
    void removeSongFromPlaylist(Long playlistId, Long songId);
    List<Playlist>getAllPlaylistsForUser(Long id);
}
