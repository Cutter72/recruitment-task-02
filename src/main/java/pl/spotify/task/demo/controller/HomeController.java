package pl.spotify.task.demo.controller;

import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.Paging;
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
    public String searchTracks(Model model, @RequestParam(required = false) String query, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit) {
        if (query == null || query == "") {
            log.info("Search for tracks with null query input.");
            model.addAttribute("totalPages", 0);
            return "searchTracks";
        }
        if (page < 1) {
            page = 1;
        }if (limit < 1 || limit > 30) {
            limit =10;
        }
        log.info("Search for tracks with '" + query + "' query input.");
        SearchTracks.setParams(SpotifyAuthentication.accessToken, query, page - 1, limit);
        Paging<Track> trackPaging = SearchTracks.searchTracks_Sync();
        Track[] tracks;
        try {
            tracks = trackPaging.getItems();
        } catch (NullPointerException ex) {
            return "redirect:/searchTracks";
        }
        int totalItems = trackPaging.getTotal();
        int totalPages = 0;
        if (totalItems > 10000) {
            totalPages = 10000/limit;
        } else {
            totalPages = totalItems / limit;
        }
        if (page > totalPages && totalPages != 0) {
            return "redirect:/searchTracks";
        }
        model.addAttribute("searchedTracks", tracks);
        model.addAttribute("query", query);
        model.addAttribute("limit", limit);
        model.addAttribute("page", page);
        model.addAttribute("totalPages", totalPages);
        return "searchTracks";
    }

    @GetMapping("/searchArtists")
    public String searchArtists(Model model, @RequestParam(required = false) String query, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit) {
        if (query == null || query == "") {
            log.info("Search for artists with null query input.");
            model.addAttribute("totalPages", 0);
            return "searchArtists";
        }
        if (page < 1) {
            page = 1;
        }if (limit < 1 || limit > 30) {
            limit =10;
        }
        log.info("Search for artists with '" + query + "' query input.");
        SearchArtists.setParams(SpotifyAuthentication.accessToken, query, page - 1, limit);
        Paging<Artist> trackPaging = SearchArtists.searchArtists_Sync();
        Artist[] tracks;
        try {
            tracks = trackPaging.getItems();
        } catch (NullPointerException ex) {
            model.addAttribute("nothingFound", "Nothing found.");
            return "searchArtists";
        }
        int totalItems = trackPaging.getTotal();
        int totalPages = 0;
        if (totalItems > 10000) {
            totalPages = 10000/limit;
        } else {
            totalPages = totalItems / limit;
        }
        if (page > totalPages && totalPages != 0) {
            return "redirect:/searchArtists";
        }
        model.addAttribute("searchedArtists", tracks);
        model.addAttribute("query", query);
        model.addAttribute("limit", limit);
        model.addAttribute("page", page);
        model.addAttribute("totalPages", totalPages);
        return "searchArtists";
    }
}
