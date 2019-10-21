package pl.spotify.task.demo.controller;

import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.Track;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.spotify.task.demo.service.SearchArtists;
import pl.spotify.task.demo.service.SearchTracks;
import pl.spotify.task.demo.service.SpotifyAuthentication;

@Controller
public class HomeController {
    private SpotifyAuthentication spotifyAuthentication;
    final static Logger log = Logger.getLogger(HomeController.class);

    public HomeController(SpotifyAuthentication spotifyAuthentication) {
        this.spotifyAuthentication = spotifyAuthentication;
    }

    public HomeController() {
    }

    @GetMapping("")
    @ResponseBody
    public String home() {
        return "<a href='/searchTracks'>searchTracks</a><br><a href='/searchArtists'>searchArtists</a>";
    }

    @GetMapping("/searchTracks")
    public String searchTracks(Model model, @RequestParam(required = false) String query) {
        if (query == null) {
            log.info("Search for tracks with null query input.");
            return "searchTracks";
        }
        log.info("Search for tracks with '" + query + "' qury input.");
        SearchTracks.setParams(SpotifyAuthentication.accessToken, query, 0, 30);
        Track[] tracks = SearchTracks.searchTracks_Sync();
        model.addAttribute("searchedTracks", tracks);
        model.addAttribute("query", query);
        return "searchTracks";
    }

    @GetMapping("/searchArtists")
    public String searchArtists(Model model, @RequestParam(required = false) String query) {
        if (query == null) {
            log.info("Search for artists with null query input.");
            return "searchArtists";
        }
        log.info("Search for artists with '" + query + "' qury input.");
        SearchArtists.setParams(SpotifyAuthentication.accessToken, query, 0, 30);
        Artist[] artists = SearchArtists.searchArtists_Sync();
        model.addAttribute("searchedArtists", artists);
        model.addAttribute("query", query);
        return "searchArtists";
    }
}
