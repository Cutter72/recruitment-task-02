package pl.spotify.task.demo.Model;

import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.Track;
import org.apache.log4j.Logger;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class Database {
    final static Logger log = Logger.getLogger(Database.class);

    public Database() {


    }

    Nitrite db = Nitrite.builder()
            .compressed()
            .filePath(Paths.get("src/main/resources/database.db").toAbsolutePath().toString())
            .openOrCreate("user", "password");
    private ObjectRepository<TrackDto> trackObjectRepository = db.getRepository(TrackDto.class);
    private ObjectRepository<ArtistDto> artistObjectRepository = db.getRepository(ArtistDto.class);


    public void addTrack(Track track) {
        TrackDto trackDto = new TrackDto(track.getId(), track);
        trackObjectRepository.insert(trackDto);
        log.info("Track with id: '" + track.getId() + "' added to database");
    }

    public void removeTrack(String id) {
        trackObjectRepository.remove(ObjectFilters.eq("id", id));
        log.info("Track with id: '" + id + "' removed from database");
    }

    public List<Track> getAllTracks() {
        List<Track> tracks = new ArrayList<>();
        trackObjectRepository.find().project(TrackDto.class).toList().forEach(item -> tracks.add(item.getTrack()));
        log.info("All favourite tracks loaded from database");
        return tracks;
    }

    public void addArtist(Artist artist) {
        ArtistDto artistDto = new ArtistDto(artist.getId(), artist);
        artistObjectRepository.insert(artistDto);
        log.info("Artist with id: '" + artist.getId() + "' added to database");
    }

    public void removeArtist(String id) {
        artistObjectRepository.remove(ObjectFilters.eq("id", id));
        log.info("Artist with id: '" + id + "' removed from database");
    }

    public List<Artist> getAllArtists() {
        List<Artist> artists = new ArrayList<>();
        artistObjectRepository.find().project(ArtistDto.class).toList().forEach(item -> artists.add(item.getArtist()));
        log.info("All favourite artists loaded from database");
        return artists;
    }
}
