package pl.spotify.task.demo.Model;

import com.wrapper.spotify.model_objects.specification.Artist;
import org.dizitart.no2.objects.Id;

public class ArtistDto {
    @Id
    private final String id;
    private final Artist artist;

    public ArtistDto(String id, Artist artist) {
        this.id = id;
        this.artist = artist;
    }

    public String getId() {
        return id;
    }

    public Artist getArtist() {
        return artist;
    }

}
