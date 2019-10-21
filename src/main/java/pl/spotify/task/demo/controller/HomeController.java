package pl.spotify.task.demo.controller;

import com.wrapper.spotify.model_objects.specification.Track;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.spotify.task.demo.service.SearchTracks;
import pl.spotify.task.demo.service.SpotifyAuthentication;

@Controller
public class HomeController {
    private SpotifyAuthentication spotifyAuthentication;

    public HomeController(SpotifyAuthentication spotifyAuthentication) {
        this.spotifyAuthentication = spotifyAuthentication;
    }

    public HomeController() {
    }

    @GetMapping("")
    @ResponseBody
    public String home() {
        return "<a href='/searchTracks'>searchTracks</a>";
    }

    @GetMapping("/searchTracks")
    public String welcome(Model model, @RequestParam(required = false) String query) {
        SearchTracks.setParams(SpotifyAuthentication.accessToken, query, 0, 30);
        Track[] tracks = SearchTracks.searchTracks_Sync();
        for (Track track : tracks) {
            System.out.println(track.getPreviewUrl());
        }
        model.addAttribute("searchedTracks", tracks);
        model.addAttribute("query", query);
        return "searchTracks";
    }
}
