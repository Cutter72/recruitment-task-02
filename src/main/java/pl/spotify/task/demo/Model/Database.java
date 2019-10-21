package pl.spotify.task.demo.Model;

import org.dizitart.no2.Nitrite;
import org.springframework.stereotype.Component;

@Component
public class Database {
    Nitrite db = Nitrite.builder()
            .compressed()
            .filePath("/tmp/test.db")
            .openOrCreate("user", "password");
}
