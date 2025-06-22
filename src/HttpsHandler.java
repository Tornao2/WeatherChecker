import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpsHandler {
    private HttpClient client;
    private String baseSiteUrl = "https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&hourly=temperature_2m";

    public HttpsHandler(){
        client = HttpClient.newHttpClient();
    }

    public JSONObject sendRequestConnection(String additionalUrl) {
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI(baseSiteUrl + additionalUrl)).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return new JSONObject(response.body());
        } catch (Exception e) {
            return new JSONObject();
        }
    }
}
