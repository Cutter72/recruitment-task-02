package pl.spotify.task.demo.controller;

import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Track;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.spotify.task.demo.Model.Database;
import pl.spotify.task.demo.service.SearchArtists;
import pl.spotify.task.demo.service.SearchTracks;
import pl.spotify.task.demo.service.SpotifyAuthentication;

@Controller
public class HomeController {
    final static Logger log = Logger.getLogger(HomeController.class);
    private Database database;
    public HomeController(Database database) {
        this.database = database;
    }

    @GetMapping("")
    @ResponseBody
    public String home() {
        log.info("Homepage entered");
        return "<a href='/searchTracks'>searchTracks</a><br><br><a href='/searchArtists'>searchArtists</a>";
    }

    @GetMapping("/db")
    public String database() {
        log.info("database entered");
        database.create();
        return "";
    }

    @GetMapping("/saveToFavourites/{trackId}")
    @ResponseBody
    public String getTrack(@PathVariable String trackId) {
        Track track = SearchTracks.getTrack_Sync(trackId);
        return "Track with id = " + trackId + " loaded. Track title: " + track.getName()+", Artist: "+track.getArtists()[0].getName();
    }

    @GetMapping("/searchTracks")
    public String searchTracks(Model model, @RequestParam(required = false) String query, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit) {
        if (query == null || query == "") {
            log.info("Search for tracks page entered.");
            model.addAttribute("totalPages", 0);
            return "searchTracks";
        }
        if (page < 1) {
            page = 1;
        }
        if (limit < 1 || limit > 30) {
            limit = 10;
        }
        log.info("Search for tracks with phrase: '" + query + "'.");
        SearchTracks.setParams(SpotifyAuthentication.accessToken, query, page - 1, limit);
        Paging<Track> trackPaging = SearchTracks.searchTracks_Sync();
        int totalItems = 0;
        if (trackPaging != null) {
            totalItems = trackPaging.getTotal();
        }
        log.info("Found " + totalItems + " tracks.");
        if (totalItems < 1) {
            model.addAttribute("nothingFound", "No tracks found.");
            return "searchTracks";
        }
        Track[] tracks;
        try {
            tracks = trackPaging.getItems();
        } catch (NullPointerException ex) {
            log.error("NullPointerException while getting tracks. Redirect to: '/searchTracks'.");
            return "redirect:/searchTracks";
        }
        int totalPages = getTotalPages(limit, totalItems);
        model.addAttribute("searchedTracks", tracks);
        model.addAttribute("query", query);
        model.addAttribute("limit", limit);
        model.addAttribute("page", page);
        model.addAttribute("totalPages", totalPages);
        log.info("Enter tracks result page: " + page);
        return "searchTracks";
    }

    @GetMapping("/searchArtists")
    public String searchArtists(Model model, @RequestParam(required = false) String query, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit) {
        if (query == null || query == "") {
            log.info("Search for artists page entered.");
            model.addAttribute("totalPages", 0);
            return "searchArtists";
        }
        if (page < 1) {
            page = 1;
        }
        if (limit < 1 || limit > 30) {
            limit = 10;
        }
        log.info("Search for artists with phrase: '" + query + "'.");
        SearchArtists.setParams(SpotifyAuthentication.accessToken, query, page - 1, limit);
        Paging<Artist> artistPaging = SearchArtists.searchArtists_Sync();
        int totalItems = 0;
        if (artistPaging != null) {
            totalItems = artistPaging.getTotal();
        }
        log.info("Found " + totalItems + " artists.");
        if (totalItems < 1) {
            model.addAttribute("nothingFound", "No artists found.");
            return "searchArtists";
        }
        Artist[] artists;
        try {
            artists = artistPaging.getItems();
        } catch (NullPointerException ex) {
            log.error("NullPointerException while getting artists. Redirect to: '/searchArtists'.");
            return "redirect:/searchArtists";
        }
        int totalPages = getTotalPages(limit, totalItems);
        model.addAttribute("searchedArtists", artists);
        model.addAttribute("query", query);
        model.addAttribute("limit", limit);
        model.addAttribute("page", page);
        model.addAttribute("totalPages", totalPages);
        log.info("Enter artists result page: " + page);
        return "searchArtists";
    }

    private int getTotalPages(int limit, int totalItems) {
        int totalPages = totalItems / limit;
        if (totalItems >= 10000) {
            return totalPages / 10;
        }
        if (totalItems % limit != 0) {
            return ++totalPages;
        }
        return totalPages;
    }
}
