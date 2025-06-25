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
     * Basic constructor which set ups the client object for future use
     */
    public HttpsHandler(){
        client = HttpClient.newHttpClient();
    }
    /// Function which sends the request to the specified url by appending additional parameters to the base address
    /// It returns a jsonobject with the response or an empty json if there was en error
    public JSONObject sendRequestConnection(String url) {
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
}
