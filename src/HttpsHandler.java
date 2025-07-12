import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
/**
 * Class to handle all https requests to the open-meteo weather api
 */
public class HttpsHandler {
    /**
     * Object from which to send the requests
     */
    private final HttpClient client;
    /**
     * Basic constructor which set-ups the client object for future use
     */
    public HttpsHandler(){
        client = HttpClient.newHttpClient();
    }
    /// Function which sends a request using open-meteo api to get weather data
    public JSONObject sendRequestConnectionWeather(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI(url)).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject jsonResponse = new JSONObject(response.body());
            if (jsonResponse.has("error"))
                return new JSONObject();
            return jsonResponse;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /// Function which sends a request using geocode maps api to get geolocation data
    public JSONArray sendRequestConnectionGeocoding(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI(url)).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONArray jsonResponse = new JSONArray(response.body());
            if (jsonResponse.isEmpty())
                return new JSONArray();
            return jsonResponse;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
