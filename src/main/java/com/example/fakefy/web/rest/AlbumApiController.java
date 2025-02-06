package com.example.fakefy.web.rest;

import com.example.fakefy.model.Album;
import com.example.fakefy.model.Artist;
import com.example.fakefy.model.Song;
import com.example.fakefy.model.dto.AlbumDto;
import com.example.fakefy.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/albums")
@CrossOrigin(origins = {"https://music-application-sb-frontend.onrender.com","http://localhost:3000"})
public class AlbumApiController {
    private final AlbumService albumService;
    private final ArtistService artistService;
    private final SongService songService;
    private final PlaylistService playlistService;
    private final MusicUserService musicUserService;

    public AlbumApiController(AlbumService albumService, ArtistService artistService, SongService songService, PlaylistService playlistService, MusicUserService musicUserService) {
        this.albumService = albumService;
        this.artistService = artistService;
        this.songService = songService;
        this.playlistService = playlistService;
        this.musicUserService = musicUserService;
    }

    @GetMapping
    public ResponseEntity<List<Album>> showAlbums(){
        List<Album> albums = albumService.listAll();
        return ResponseEntity.ok(albums);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable Long id){
        Album album = this.albumService.getAlbumById(id);
        return album != null ? ResponseEntity.ok(album) : ResponseEntity.notFound().build();
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable Long id){
        this.albumService.deleteAlbum(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/create")
    public ResponseEntity<Album> createAlbum(@RequestBody AlbumDto album){
        Album newAlbum = albumService.createAlbum(
                album.getTitle(),
                album.getDateCreated(),
                album.getArtistId(),
                album.getGenre(),
                album.getImageUrl()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(newAlbum);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Album> updateAlbum(@PathVariable Long id, @RequestBody Album album){
        Album updatedAlbum = albumService.updateAlbum(
                id,
                album.getTitle(),
                album.getDateCreated(),
                album.getArtist().getId(),
                album.getGenre(),
                album.getImageUrl()
        );
        return ResponseEntity.ok(updatedAlbum);
    }
    @PostMapping("/{albumId}/like")
    public ResponseEntity<String> likeAlbum(@PathVariable Long albumId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if (username == null || username.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated. Please log in.");
        }

        musicUserService.likeAlbum(username, albumId);
        return ResponseEntity.ok("Album liked successfully.");
    }

    @PostMapping("/{albumId}/unlike")
    public ResponseEntity<String> unlikeAlbum(@PathVariable Long albumId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if (username == null || username.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated. Please log in.");
        }

        musicUserService.unlikeAlbum(username, albumId);
        return ResponseEntity.ok("Album unliked successfully.");
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<List<Song>> getSongsFromAlbum(@PathVariable Long id){
        Album album = this.albumService.getAlbumById(id);
        List<Song> songsAlbum = this.songService.findAllByAlbumId(id);
        return ResponseEntity.ok(songsAlbum);
    }


}
