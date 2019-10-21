package pl.spotify.task.demo.service;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;

import java.io.IOException;

public class SearchTracks {
    private static SpotifyApi spotifyApi = null;
    private static SearchTracksRequest searchTracksRequest = null;

    public static Track[] searchTracks_Sync() {
        try {
            final Paging<Track> trackPaging = searchTracksRequest.execute();
            System.out.println("getNext: " + trackPaging.getNext());
            System.out.println("Total: " + trackPaging.getTotal());
            return trackPaging.getItems();
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }

    }

    public static void setParams(String accessToken, String query, int page, int limit) {
        spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(accessToken)
                .build();
        searchTracksRequest = spotifyApi.searchTracks(query)
//          .market(CountryCode.SE)
                .limit(limit)
                .offset(page * limit)
                .build();
    }
}
