package pl.spotify.task.demo.Model;

import com.wrapper.spotify.model_objects.specification.Track;
import org.dizitart.no2.objects.Id;

public class TrackDto {
    @Id
    private final String id;
    private final Track track;

    public TrackDto(String id, Track track) {
        this.id = id;
        this.track = track;
    }

    public String getId() {
        return id;
    }

    public Track getTrack() {
        return track;
    }

}
