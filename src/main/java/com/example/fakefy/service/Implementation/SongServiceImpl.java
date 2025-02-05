package com.example.fakefy.service.Implementation;

import com.example.fakefy.model.Album;
import com.example.fakefy.model.Artist;
import com.example.fakefy.model.Song;
import com.example.fakefy.repositories.AlbumRepository;
import com.example.fakefy.repositories.ArtistRepository;
import com.example.fakefy.repositories.PlaylistRepository;
import com.example.fakefy.repositories.SongRepository;
import com.example.fakefy.service.SongService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongServiceImpl implements SongService {
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;

    public SongServiceImpl(AlbumRepository albumRepository, ArtistRepository artistRepository, PlaylistRepository playlistRepository, SongRepository songRepository) {
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
        this.playlistRepository = playlistRepository;
        this.songRepository = songRepository;
    }

    @Override
    public Song findById(Long id) {
        return this.songRepository.findById(id).orElse(null);
    }

    @Override
    public Song findByTitle(String title) {
        return this.songRepository.findByTitle(title);
    }

    @Override
    public List<Song> listAll() {
        return this.songRepository.findAll();
    }

    @Override
    public Song create(String title, String filePath, int lengthSeconds, Long albumId, Long artistId) {
        Album album = this.albumRepository.findById(albumId).orElse(null);
        Artist artist = this.artistRepository.findById(artistId).orElse(null);
        Song song = new Song(title,filePath,lengthSeconds,album,artist);
        return this.songRepository.save(song);
    }

    @Override
    public Song update(Long id, String title, String filePath, int lengthSeconds, Long albumId, Long artistId) {
        Song song = this.findById(id);
        song.setTitle(title);
        song.setFilePath(filePath);
        song.setLengthSeconds(lengthSeconds);
        song.setAlbum(this.albumRepository.findById(albumId).orElse(null));
        song.setArtist(this.artistRepository.findById(artistId).orElse(null));
        return this.songRepository.save(song);
    }

    @Override
    public Song delete(Long id) {
        Song song = this.findById(id);
        this.songRepository.delete(song);
        return song;
    }

    @Override
    public List<Song> findAllByAlbumId(Long albumId) {
        Album album = this.albumRepository.findById(albumId).orElse(null);
        return this.songRepository.findAllByAlbum(album);
    }
}
