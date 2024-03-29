package pl.spotify.task.demo.service;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.requests.data.artists.GetArtistRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchArtistsRequest;
import org.apache.log4j.Logger;

import java.io.IOException;

public class SearchArtists {
    private static SpotifyApi spotifyApi = null;
    private static SearchArtistsRequest searchArtistsRequest = null;
    final static Logger log = Logger.getLogger(SearchTracks.class);

    public static Paging<Artist> searchArtists_Sync() {
        try {
            return searchArtistsRequest.execute();
        } catch (IOException | SpotifyWebApiException e) {
            log.error("NullPointerException while getting Paging<Artist>. Probably invalid page number and exceeded Spotify API limits.");
            return null;
        }

    }

    public static void setParams(String accessToken, String query, int page, int limit) {
        spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(accessToken)
                .build();
        searchArtistsRequest = spotifyApi.searchArtists(query)
                .limit(limit)
                .offset(page * limit)
                .build();
    }

    public static Artist getArtist_Sync(String id) {
        GetArtistRequest getArtistRequest = spotifyApi.getArtist(id)
                .build();
        try {
            Artist artist = getArtistRequest.execute();
            log.info("Artist with id = '" + id + "' loaded from Spotify API.");
            return artist;
        } catch (IOException | SpotifyWebApiException e) {
            log.error("NullPointerException while getting single Artist with id = " + id);
            return null;
        }
    }
}
