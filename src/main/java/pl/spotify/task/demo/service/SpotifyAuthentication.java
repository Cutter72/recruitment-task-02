package pl.spotify.task.demo.service;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SpotifyAuthentication {
    private static final String clientId = "b4448cb55b494d17851c5af086aab2b8";
    private static final String clientSecret = "4b15a5d9cd4f4155aa71eb4a9dbf5f2c";
    public static String accessToken = null;

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .build();
    public static ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
            .build();

    public SpotifyAuthentication() {
        clientCredentials_Sync();
    }

    public static void clientCredentials_Sync() {
        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
            accessToken = clientCredentials.getAccessToken();
            spotifyApi.setAccessToken(accessToken);
            System.out.println("AccessToken: "+accessToken);
            System.out.println("Expires in: " + clientCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
