import javax.net.ssl.HttpsURLConnection;
import java.io.DataOutputStream;
import java.net.URI;
import java.net.URL;

public class HttpsHandler {
    private HttpsURLConnection connection;
    private String baseSiteUrl = "https://api.open-meteo.com/v1/";

    public boolean sendRequestConnection(String additionalUrl) {
        String properUrl = baseSiteUrl.concat(additionalUrl);
        try {
            URI uri = new URI(properUrl);
            URL siteUrl = uri.toURL();
            connection = (HttpsURLConnection) siteUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());

        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
