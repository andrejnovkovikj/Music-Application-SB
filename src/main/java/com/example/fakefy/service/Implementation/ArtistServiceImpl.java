package com.example.fakefy.service.Implementation;

import com.example.fakefy.model.Artist;
import com.example.fakefy.repositories.AlbumRepository;
import com.example.fakefy.repositories.ArtistRepository;
import com.example.fakefy.repositories.PlaylistRepository;
import com.example.fakefy.repositories.SongRepository;
import com.example.fakefy.service.ArtistService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistServiceImpl implements ArtistService {
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;

    public ArtistServiceImpl(AlbumRepository albumRepository, ArtistRepository artistRepository, PlaylistRepository playlistRepository, SongRepository songRepository) {
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
        this.playlistRepository = playlistRepository;
        this.songRepository = songRepository;
    }

    @Override
    public Artist findById(Long id) {
        return this.artistRepository.findById(id).orElse(null);
    }

    @Override
    public Artist findByName(String name) {
        return this.artistRepository.findByName(name);
    }

    @Override
    public List<Artist> findAll() {
        return this.artistRepository.findAll();
    }

    @Override
    public Artist create(String name, String bio,String imageUrl) {
        Artist artist = new Artist(name,bio,imageUrl);
        return this.artistRepository.save(artist);
    }

    @Override
    public Artist update(Long id, String name, String bio,String imageUrl) {
        Artist artist = this.findById(id);
        artist.setName(name);
        artist.setBio(bio);
        artist.setImageUrl(imageUrl);
        return this.artistRepository.save(artist);
    }

    @Override
    public Artist delete(Long id) {
        Artist artist = this.findById(id);
        this.artistRepository.delete(artist);
        return artist;
    }
}
