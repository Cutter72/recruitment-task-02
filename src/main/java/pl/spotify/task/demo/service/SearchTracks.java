package pl.spotify.task.demo.service;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;
import com.wrapper.spotify.requests.data.tracks.GetTrackRequest;
import org.apache.log4j.Logger;

import java.io.IOException;

public class SearchTracks {
    private static SpotifyApi spotifyApi = null;
    private static SearchTracksRequest searchTracksRequest = null;
    final static Logger log = Logger.getLogger(SearchTracks.class);

    public static Paging<Track> searchTracks_Sync() {
        try {
            return searchTracksRequest.execute();
        } catch (IOException | SpotifyWebApiException e) {
            log.error("NullPointerException while getting Paging<Track>. Probably invalid page number and exceeded Spotify API limits.");
            return null;
        }

    }

    public static void setParams(String accessToken, String query, int page, int limit) {
        spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(accessToken)
                .build();
        searchTracksRequest = spotifyApi.searchTracks(query)
                .limit(limit)
                .offset(page * limit)
                .build();
    }

    public static Track getTrack_Sync(String id) {
        GetTrackRequest getTrackRequest = spotifyApi.getTrack(id)
                .build();
        try {
            Track track = getTrackRequest.execute();
            log.info("Track with id = " + id + " loaded. Track title: " + track.getName()+", Artist: "+track.getArtists()[0].getName());
            return track;
        } catch (IOException | SpotifyWebApiException e) {
            log.error("NullPointerException while getting single Track with id = " + id);
            return null;
        }
    }
}
