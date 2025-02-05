package com.example.fakefy.web.rest;

import com.example.fakefy.model.Song;
import com.example.fakefy.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/songs")
@CrossOrigin(origins = {"https://music-application-sb-frontend.onrender.com","http://localhost:3000"})
public class SongApiController {
  private final AlbumService albumService;
    private final ArtistService artistService;
    private final SongService songService;
    private final PlaylistService playlistService;
    private final MusicUserService musicUserService;

    public SongApiController(AlbumService albumService, ArtistService artistService, SongService songService, PlaylistService playlistService, MusicUserService musicUserService) {
        this.albumService = albumService;
        this.artistService = artistService;
        this.songService = songService;
        this.playlistService = playlistService;
        this.musicUserService = musicUserService;
    }

    @GetMapping
    public ResponseEntity<List<Song>> getAllSongs(){
        List<Song> songs = songService.listAll();
        return ResponseEntity.ok(songs);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable Long id){
        Song song = songService.findById(id);
        return song != null ? ResponseEntity.ok(song) : ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long id){
        songService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping
    public ResponseEntity<Song> addSong(@RequestBody Song song){
        Song newSong = songService.create(
                song.getTitle(),
                song.getFilePath(),
                song.getLengthSeconds(),
                song.getAlbum().getId(),
                song.getArtist().getId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(newSong);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Song> updateSong(@PathVariable Long id,@RequestBody Song song){
        Song updatedSong = songService.update(
                id,
                song.getTitle(),
                song.getFilePath(),
                song.getLengthSeconds(),
                song.getAlbum().getId(),
                song.getArtist().getId()
        );
        return ResponseEntity.ok(updatedSong);
    }

    @PostMapping("/{songId}/like")
    public ResponseEntity<String> likeSong(@PathVariable Long songId) {
        // Get the username from the SecurityContextHolder (assuming JWT authentication)
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if (username == null || username.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated. Please log in.");
        }

        // Like the song
        musicUserService.likeSong(username, songId);
        return ResponseEntity.ok("Song liked successfully.");
    }

    @PostMapping("/{songId}/unlike")
    public ResponseEntity<String> unlikeSong(@PathVariable Long songId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if (username == null || username.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated. Please log in.");
        }

        // Unlike the song
        musicUserService.unlikeSong(username, songId);
        return ResponseEntity.ok("Song unliked successfully.");
    }


}