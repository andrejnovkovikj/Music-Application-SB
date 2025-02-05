package com.example.fakefy.web.rest;

import com.example.fakefy.model.MusicUser;
import com.example.fakefy.model.Playlist;
import com.example.fakefy.model.dto.PlaylistDto;
import com.example.fakefy.model.dto.PlaylistUserDto;
import com.example.fakefy.service.MusicUserService;
import com.example.fakefy.service.PlaylistService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.List;


@RestController
@RequestMapping("/api/playlists")
@CrossOrigin(origins = {"https://music-application-sb-frontend.onrender.com","http://localhost:3000"})
public class PlaylistApiController {
    private final PlaylistService playlistService;
    private final MusicUserService musicUserService;

    public PlaylistApiController(PlaylistService playlistService, MusicUserService musicUserService) {
        this.playlistService = playlistService;
        this.musicUserService = musicUserService;
    }

    @GetMapping
    public ResponseEntity<List<Playlist>> getAllPlaylists() {
        List<Playlist> playlists = playlistService.listAll();
        return ResponseEntity.ok(playlists);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistUserDto> getPlaylistById(@PathVariable Long id) {
        Playlist playlist = playlistService.findById(id);
        MusicUser user = playlist.getUser();
        PlaylistUserDto dtoPlaylist = new PlaylistUserDto(
                playlist.getId(),
                playlist.getName(),
                playlist.getUser(),
                playlist.getDescription(),
                playlist.getImageUrl(),
                playlist.getSongs(),
                user.getUsername()


        );
        return playlist != null ? ResponseEntity.ok(dtoPlaylist) : ResponseEntity.notFound().build();
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Playlist> getPlaylistByName(@PathVariable String name) {
        Playlist playlist = playlistService.findByName(name);
        return playlist != null ? ResponseEntity.ok(playlist) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Playlist> addPlaylist(@RequestBody PlaylistDto playlist) {
        try {
            Playlist newPlaylist = playlistService.create(
                    playlist.getName(),
                    playlist.getDescription(),
                    playlist.getImageUrl(),
                    playlist.getUsername()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(newPlaylist);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Playlist> updatePlaylist(@PathVariable Long id, @RequestBody PlaylistDto playlist) {
        Playlist updatedPlaylist = playlistService.update(
                id,
                playlist.getName(),
                playlist.getDescription(),
                playlist.getImageUrl()
        );
        return ResponseEntity.ok(updatedPlaylist);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable Long id) {
        playlistService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{playlistId}/{songId}/add-song-to-playlist")
    public ResponseEntity<String> addSongToPlaylist(@PathVariable Long playlistId,@PathVariable Long songId) {

        this.playlistService.addSongToPlaylist(playlistId, songId);
        return ResponseEntity.ok("Song added to playlist .");
    }

    @DeleteMapping("/{playlistId}/{songId}/remove-song-from-playlist")
    public ResponseEntity<String> removeSongToPlaylist(@PathVariable Long playlistId,@PathVariable Long songId) {
        this.playlistService.removeSongFromPlaylist(playlistId, songId);
        return ResponseEntity.ok("Song removed from playlist .");
    }

}