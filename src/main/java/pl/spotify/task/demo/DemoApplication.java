package pl.spotify.task.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Paths;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        System.setProperty("resources", Paths.get("src/main/resources/").toAbsolutePath().toString());
        SpringApplication.run(DemoApplication.class, args);
    }

}
