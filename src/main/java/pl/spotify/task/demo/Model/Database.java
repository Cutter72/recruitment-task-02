package pl.spotify.task.demo.Model;

import org.dizitart.no2.Document;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.NitriteCollection;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

import static org.dizitart.no2.Document.createDocument;
import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

@Component
public class Database {
    Nitrite db = Nitrite.builder()
            .compressed()
            .filePath(Paths.get("src/main/resources/database.db").toAbsolutePath().toString())
            .openOrCreate("user", "password");
    NitriteCollection collection = db.getCollection("test");
    Document doc = createDocument("firstName", "John")
            .put("lastName", "Doe")
            .put("birthDay", new Date())
            .put("data", new byte[]{1, 2, 3})
            .put("fruits", new ArrayList<String>() {{
                add("apple");
                add("orange");
            }})
            .put("note", "a quick brown fox jump over the lazy dog");

    public void create() {
        collection.insert(doc);

    }

    public void modify() {

        collection.update(eq("firstName", "John"), createDocument("lastName", "Wick"));
    }
}
