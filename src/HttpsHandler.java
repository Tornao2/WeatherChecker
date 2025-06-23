import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpsHandler {
    private final HttpClient client;

    public HttpsHandler(){
        client = HttpClient.newHttpClient();
    }
    public JSONObject sendRequestConnection(String additionalUrl) {
        try {
            String baseSiteUrl = "https://api.open-meteo.com/v1/forecast?";
            HttpRequest request = HttpRequest.newBuilder(new URI(baseSiteUrl + additionalUrl)).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject jsonResponse = new JSONObject(response.body());
            if (jsonResponse.has("error"))
                return new JSONObject();
            return jsonResponse;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
