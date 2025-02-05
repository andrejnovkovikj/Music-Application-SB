package com.example.fakefy.web.rest;

import com.example.fakefy.model.Album;
import com.example.fakefy.model.dto.AlbumDto;
import com.example.fakefy.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = {"https://music-application-sb-frontend.onrender.com","http://localhost:3000"})
public class AdminController {
    private final AlbumService albumService;
    private final ArtistService artistService;
    private final SongService songService;
    private final PlaylistService playlistService;
    private final MusicUserService musicUserService;

    public AdminController(AlbumService albumService, ArtistService artistService, SongService songService, PlaylistService playlistService, MusicUserService musicUserService) {
        this.albumService = albumService;
        this.artistService = artistService;
        this.songService = songService;
        this.playlistService = playlistService;
        this.musicUserService = musicUserService;
    }
    @GetMapping("/albums")
    public ResponseEntity<List<Album>> showAlbums(){
        List<Album> albums = albumService.listAll();
        return ResponseEntity.ok(albums);
    }

    @GetMapping("/albums/{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable Long id){
        Album album = this.albumService.getAlbumById(id);
        return album != null ? ResponseEntity.ok(album) : ResponseEntity.notFound().build();
    }
    @DeleteMapping("/albums/delete/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable Long id){
        this.albumService.deleteAlbum(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/albums/create")
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

    @PutMapping("/albums/update/{id}")
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


}
