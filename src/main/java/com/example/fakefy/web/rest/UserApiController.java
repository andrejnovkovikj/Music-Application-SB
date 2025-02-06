package com.example.fakefy.web.rest;

import com.example.fakefy.model.*;
import com.example.fakefy.service.MusicUserService;
import com.example.fakefy.service.PlaylistService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = {"https://music-application-sb-frontend.onrender.com","http://localhost:3000"})
public class UserApiController {

    private final MusicUserService musicUserService;
    private final PlaylistService  playlistService;

    public UserApiController(MusicUserService musicUserService, PlaylistService playlistService) {
        this.musicUserService = musicUserService;
        this.playlistService = playlistService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody MusicUser user) {
        MusicUser createdUser = musicUserService.create(user.getUsername(), user.getEmail(), user.getPassword(), user.getRole(), user.isPremium());

        if (createdUser != null) {
            return ResponseEntity.ok("User created successfully");
        } else {
            return ResponseEntity.status(500).body("Failed to create user");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MusicUser> getUserById(@PathVariable Long id) {
        MusicUser user = musicUserService.findById(id);

        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<MusicUser>> getAllUsers() {
        List<MusicUser> users = musicUserService.listAll();

        if (users != null && !users.isEmpty()) {
            return ResponseEntity.ok(users);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MusicUser> updateUser(@PathVariable Long id,
                                                @RequestBody MusicUser user) {
        try {
            MusicUser updatedUser = musicUserService.update(id, user.getUsername(), user.getEmail(), user.getPassword(), user.getRole(), user.isPremium());
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            MusicUser deletedUser = musicUserService.delete(id);
            return ResponseEntity.ok("User with id " + id + " deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("User not found");
        }

    }

    @GetMapping("/authenticated")
    @ResponseBody
    public ResponseEntity<Boolean> checkAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated() && !(auth.getPrincipal() instanceof String && "anonymousUser".equals(auth.getPrincipal()))) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
    }
    @GetMapping("/current-user")
    public ResponseEntity<MusicUser> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            String username = authentication.getName();
            MusicUser user = musicUserService.findByUsername(username);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @GetMapping("/current-user-role/{username}")
    public ResponseEntity<Boolean> isAdmin(@PathVariable String username) {
        MusicUser user = musicUserService.findByUsername(username);
        if (user != null) {
            boolean isAdmin = "ADMIN".equals(user.getRole().toString());
            return ResponseEntity.ok(isAdmin);
        } else {
            return ResponseEntity.ok(false);
        }
    }

    @GetMapping("/{username}/liked-albums")
    public ResponseEntity<Set<Album>> getLikedAlbumsByUsername(@PathVariable String username) {
        Set<Album> likedAlbums = musicUserService.getLikedAlbumsByUsername(username);

        if (likedAlbums != null && !likedAlbums.isEmpty()) {
            return ResponseEntity.ok(likedAlbums);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }
    @GetMapping("/{username}/liked-songs")
    public ResponseEntity<Set<Song>> getLikedSongsByUsername(@PathVariable String username) {
        Set<Song> likedSongs = musicUserService.getLikedSongsByUsername(username);

        if (likedSongs != null && !likedSongs.isEmpty()) {
            return ResponseEntity.ok(likedSongs);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }
    @GetMapping("/{username}/playlists")
    public ResponseEntity<List<Playlist>> getUserPlaylists(@PathVariable String username) {
        MusicUser user = this.musicUserService.findByUsername(username);
        List<Playlist> playlists = playlistService.getAllPlaylistsForUser(user.getId());
        return ResponseEntity.ok(playlists);
    }


}