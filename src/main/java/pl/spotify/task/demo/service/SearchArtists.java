package pl.spotify.task.demo.service;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.requests.data.search.simplified.SearchArtistsRequest;

import java.io.IOException;

public class SearchArtists {
    private static SpotifyApi spotifyApi = null;
    private static SearchArtistsRequest searchArtistsRequest = null;

    public static Paging<Artist> searchArtists_Sync() {
        try {
            final Paging<Artist> artistPaging = searchArtistsRequest.execute();
            return artistPaging;
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }

    }

    public static void setParams(String accessToken, String query, int page, int limit) {
        spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(accessToken)
                .build();
        searchArtistsRequest = spotifyApi.searchArtists(query)
//          .market(CountryCode.SE)
                .limit(limit)
                .offset(page * limit)
                .build();
    }
}
