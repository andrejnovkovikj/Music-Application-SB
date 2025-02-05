package com.example.fakefy.web;

import com.example.fakefy.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class SongController {
    private final AlbumService albumService;
    private final ArtistService artistService;
    private final SongService songService;
    private final PlaylistService playlistService;
    private final MusicUserService musicUserService;

    public SongController(AlbumService albumService, ArtistService artistService, SongService songService, PlaylistService playlistService, MusicUserService musicUserService) {
        this.albumService = albumService;
        this.artistService = artistService;
        this.songService = songService;
        this.playlistService = playlistService;
        this.musicUserService = musicUserService;
    }

    @GetMapping("/songs")
    public String getAllSongs(Model model) {
        model.addAttribute("songs",songService.listAll());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
            model.addAttribute("user", auth);
        }
        return "songs";
    }
    @PostMapping("/songs/{songId}/like")
    public String likeSong(@PathVariable("songId") Long songId, @RequestParam String userName, Model model) {
        System.out.println(songId + " like " + userName);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            return "redirect:/login";
        }

        musicUserService.likeSong(userName, songId);

        return "redirect:/albums";
    }
    @PostMapping("/songs/{songId}/unlike")
    public String unlikeAlbum(@PathVariable("songId") Long songId, @RequestParam String userName, Model model) {
        System.out.println(songId + " unlike " + userName);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            return "redirect:/login";
        }
        musicUserService.unlikeSong(userName, songId);
        return "redirect:/albums";
    }

}
