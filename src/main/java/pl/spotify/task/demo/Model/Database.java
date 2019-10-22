package pl.spotify.task.demo.Model;

import com.wrapper.spotify.model_objects.specification.Track;
import org.apache.log4j.Logger;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.util.List;

@Component
public class Database {
    final static Logger log = Logger.getLogger(Database.class);
    Nitrite db = Nitrite.builder()
            .compressed()
            .filePath(Paths.get("src/main/resources/database.db").toAbsolutePath().toString())
            .openOrCreate("user", "password");
    ObjectRepository<Track> trackObjectRepository= db.getRepository(Track.class);


    public void addTrack(Track track) {
        trackObjectRepository.insert(track);
        log.info("Track added to database");
    }

    public void removeTrack(Track track) {
        trackObjectRepository.remove(track);
        log.info("Track removed from database");
    }

    public List<Track> getAllTracks() {
    return trackObjectRepository.find().project(Track.class).toList();
    }
}
