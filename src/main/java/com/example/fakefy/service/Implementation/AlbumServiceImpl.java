package com.example.fakefy.service.Implementation;

import com.example.fakefy.model.Album;
import com.example.fakefy.model.Artist;
import com.example.fakefy.model.Genre;
import com.example.fakefy.repositories.AlbumRepository;
import com.example.fakefy.repositories.ArtistRepository;
import com.example.fakefy.repositories.PlaylistRepository;
import com.example.fakefy.repositories.SongRepository;
import com.example.fakefy.service.AlbumService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;

    public AlbumServiceImpl(AlbumRepository albumRepository, ArtistRepository artistRepository, PlaylistRepository playlistRepository, SongRepository songRepository) {
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
        this.playlistRepository = playlistRepository;
        this.songRepository = songRepository;
    }

    @Override
    public Album getAlbumById(Long id) {
        return this.albumRepository.findById(id).orElse(null);
    }

    @Override
    public Album getAlbumByTitle(String title) {
        return this.albumRepository.findByTitle(title);
    }

    @Override
    public List<Album> listAll() {
        return this.albumRepository.findAll();
    }

    @Override
    public Album createAlbum(String title, LocalDate dateCreated, Long artistId, Genre genre,String imageUrl) {
        Artist artist = this.artistRepository.findById(artistId).orElse(null);
        Album album = new Album(title,dateCreated,imageUrl,artist,genre);
        return this.albumRepository.save(album);
    }

    @Override
    public Album updateAlbum(Long id, String title, LocalDate dateCreated,Long artistId,Genre genre,String imageUrl) {
        Artist artist = this.artistRepository.findById(artistId).orElse(null);
        Album album = this.getAlbumById(id);
        album.setTitle(title);
        album.setDateCreated(dateCreated);
        album.setArtist(artist);
        album.setGenre(genre);
        album.setImageUrl(imageUrl);
        return this.albumRepository.save(album);
    }

    @Override
    public Album deleteAlbum(Long id) {
        Album album = this.getAlbumById(id);
        this.albumRepository.delete(album);
        return album;
    }

    @Override
    public List<Album> findAllByArtistId(Long artistId) {
        Artist artist = this.artistRepository.findById(artistId).orElse(null);
        return this.albumRepository.findAllByArtist(artist);
    }
}
