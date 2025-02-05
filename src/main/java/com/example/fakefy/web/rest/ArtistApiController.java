package com.example.fakefy.web.rest;

import com.example.fakefy.model.Album;
import com.example.fakefy.model.Artist;
import com.example.fakefy.model.Song;
import com.example.fakefy.service.AlbumService;
import com.example.fakefy.service.ArtistService;
import com.example.fakefy.service.PlaylistService;
import com.example.fakefy.service.SongService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artists")
@CrossOrigin(origins = {"https://music-application-sb-frontend.onrender.com","http://localhost:3000"})
public class ArtistApiController {
    private final AlbumService albumService;
    private final ArtistService artistService;
    private final SongService songService;
    private final PlaylistService playlistService;

    public ArtistApiController(AlbumService albumService, ArtistService artistService, SongService songService, PlaylistService playlistService) {
        this.albumService = albumService;
        this.artistService = artistService;
        this.songService = songService;
        this.playlistService = playlistService;
    }
    @GetMapping
    public ResponseEntity<List<Artist>> getAllArtists(){
        List<Artist> artists = artistService.findAll();
        return ResponseEntity.ok(artists);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Artist> getArtistById(@PathVariable Long id){
        Artist artist = artistService.findById(id);
        return artist != null ? ResponseEntity.ok(artist) : ResponseEntity.notFound().build();
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable Long id){
        this.artistService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping
    public ResponseEntity<Artist> addArtist(@RequestBody Artist artist){
        Artist newArtist = artistService.create(
                artist.getName(),
                artist.getBio(),
                artist.getImageUrl()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(newArtist);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Artist>updateArtist(@PathVariable Long id, @RequestBody Artist artist){
        Artist updatedArtist = artistService.update(
                id,
                artist.getName(),
                artist.getBio(),
                artist.getImageUrl()
        );
        return ResponseEntity.ok(updatedArtist);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<List<Album>> getAlbumsFromArtist(@PathVariable Long id){
        Artist artist = this.artistService.findById(id);
        List<Album> albumsArtist = this.albumService.findAllByArtistId(id);
        return ResponseEntity.ok(albumsArtist);
    }


}
