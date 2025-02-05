package com.example.fakefy.web;

import com.example.fakefy.model.Artist;
import com.example.fakefy.service.AlbumService;
import com.example.fakefy.service.ArtistService;
import com.example.fakefy.service.PlaylistService;
import com.example.fakefy.service.SongService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
public class ArtistController {
    private final AlbumService albumService;
    private final ArtistService artistService;
    private final SongService songService;
    private final PlaylistService playlistService;

    public ArtistController(AlbumService albumService, ArtistService artistService, SongService songService, PlaylistService playlistService) {
        this.albumService = albumService;
        this.artistService = artistService;
        this.songService = songService;
        this.playlistService = playlistService;
    }
    @GetMapping("/artists")
    public String artists(Model model){
        List<Artist> artists = artistService.findAll();
        model.addAttribute("artists", artists);
        return "artists.html";
    }
}
