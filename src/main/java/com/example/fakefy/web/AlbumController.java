package com.example.fakefy.web;


import com.example.fakefy.model.Album;
import com.example.fakefy.model.MusicUser;
import com.example.fakefy.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping
public class AlbumController {
    private final AlbumService albumService;
    private final ArtistService artistService;
    private final SongService songService;
    private final PlaylistService playlistService;
    private final MusicUserService musicUserService;

    public AlbumController(AlbumService albumService, ArtistService artistService, SongService songService, PlaylistService playlistService, MusicUserService musicUserService) {
        this.albumService = albumService;
        this.artistService = artistService;
        this.songService = songService;
        this.playlistService = playlistService;
        this.musicUserService = musicUserService;
    }

    @GetMapping("/albums")
    public String getAlbums(Model model) {
        model.addAttribute("albums", albumService.listAll());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
            model.addAttribute("user", auth);
        }
        return "albums";
    }
    @GetMapping("/album/{id}")
    public String getAlbum(Model model, @PathVariable Long id) {
        Album album = albumService.getAlbumById(id);
        model.addAttribute("album", album);
        return "albumDetails";
    }

    @PostMapping("/albums/{albumId}/like")
    public String likeAlbum(@PathVariable("albumId") Long albumId, @RequestParam String userName, Model model) {
        System.out.println(albumId + " like " + userName);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            return "redirect:/login";
        }

        musicUserService.likeAlbum(userName, albumId);

        return "redirect:/albums";
    }
    @PostMapping("/albums/{albumId}/unlike")
    public String unlikeAlbum(@PathVariable("albumId") Long albumId, @RequestParam String userName, Model model) {
        System.out.println(albumId + " unlike " + userName);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            return "redirect:/login";
        }
        musicUserService.unlikeAlbum(userName, albumId);
        return "redirect:/albums";
    }


}
