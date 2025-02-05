package com.example.fakefy.service.Implementation;

import com.example.fakefy.model.Album;
import com.example.fakefy.model.MusicUser;
import com.example.fakefy.model.Song;
import com.example.fakefy.model.UserRole;
import com.example.fakefy.repositories.AlbumRepository;
import com.example.fakefy.repositories.MusicUserRepository;
import com.example.fakefy.repositories.SongRepository;
import com.example.fakefy.service.MusicUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MusicUserServiceImpl implements MusicUserService {
    private final MusicUserRepository musicUserRepository;
    private final SongRepository songRepository;
    private final AlbumRepository albumRepository;

    public MusicUserServiceImpl(MusicUserRepository musicUserRepository, SongRepository songRepository, AlbumRepository albumRepository) {
        this.musicUserRepository = musicUserRepository;
        this.songRepository = songRepository;
        this.albumRepository = albumRepository;
    }
    @Override
    public MusicUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof MusicUser) {
            return (MusicUser) authentication.getPrincipal();
        }
        return null;
    }
    @Override
    public MusicUser findByUsername(String username) {
        return this.musicUserRepository.findByUsername(username);
    }

    @Override
    public MusicUser findById(Long id) {
        return this.musicUserRepository.findById(id).orElseThrow();
    }

    @Override
    public List<MusicUser> listAll() {
        return musicUserRepository.findAll();
    }

    @Override
    public MusicUser create(String username, String email, String password, UserRole role, boolean isPremium) {
        MusicUser musicUser = new MusicUser(username, email, password, role, isPremium);
        return this.musicUserRepository.save(musicUser);
    }

    @Override
    public MusicUser update(Long id, String username, String email, String password, UserRole role, boolean isPremium) {
        MusicUser user = this.musicUserRepository.findById(id).orElseThrow();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        user.setPremium(isPremium);
        return this.musicUserRepository.save(user);
    }

    @Override
    public MusicUser delete(Long id) {
        MusicUser user = this.musicUserRepository.findById(id).orElseThrow();

        this.musicUserRepository.delete(user);
    return user;
    }
    @Override
    public void likeSong(String username, Long songId) {
        MusicUser user = musicUserRepository.findByUsername(username);
        Song song = songRepository.findById(songId).orElseThrow();
        user.getLikedSongs().add(song);
        musicUserRepository.save(user);
        songRepository.save(song);
    }
    @Override
    public void unlikeSong(String username, Long songId) {
        MusicUser user = musicUserRepository.findByUsername(username);
        Song song = songRepository.findById(songId).orElseThrow();
        user.getLikedSongs().remove(song);
        songRepository.save(song);
        musicUserRepository.save(user);
    }
    @Override
    public void likeAlbum(String username, Long albumId) {
        MusicUser user = musicUserRepository.findByUsername(username);
        Album album = albumRepository.findById(albumId).orElseThrow();
        user.getLikedAlbums().add(album);
        albumRepository.save(album);
        musicUserRepository.save(user);
    }
    @Override
    public void unlikeAlbum(String username, Long albumId) {
        MusicUser user = musicUserRepository.findByUsername(username);
        Album album = albumRepository.findById(albumId).orElseThrow();
        user.getLikedAlbums().remove(album);
        albumRepository.save(album);
        musicUserRepository.save(user);
    }

    @Override
    public Set<Album> getLikedAlbumsByUsername(String username) {
        Set<Album> albums = new HashSet<>();
        MusicUser user = this.musicUserRepository.findUserByUsername(username).orElseThrow();
        albums.addAll(user.getLikedAlbums());
        return albums;
    }

    @Override
    public Set<Song> getLikedSongsByUsername(String username) {
        Set<Song> songs = new HashSet<>();
        MusicUser user = this.musicUserRepository.findUserByUsername(username).orElseThrow();
        songs.addAll(user.getLikedSongs());
        return songs;
    }


    @Override
    public Set<Album> getLikedAlbumsByUserId(Long id) {
        Set<Album> albums = new HashSet<>();
        MusicUser user = this.musicUserRepository.findById(id).orElseThrow();
        albums.addAll(user.getLikedAlbums());
        return albums;
    }
}
