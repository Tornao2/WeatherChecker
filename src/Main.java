import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.json.JSONObject;

public class Main extends Application {
    private HttpsHandler httphandler = new HttpsHandler();

    public void start(Stage stage)  {
        VBox verticalManager = new VBox(6);
        verticalManager.setPadding(new Insets(10));
        verticalManager.setAlignment(Pos.BASELINE_CENTER);
        createCoordinateFields(verticalManager);
        JavaFxBuilder.createButton(verticalManager, "Send a request", _ -> {
            try {
                Pair<Float, Float> coordinates = parseCoordinates(verticalManager);
                JSONObject json = httphandler.sendRequestConnection("latitude="
                        + coordinates.getKey() + "&longitude=" + coordinates.getValue() + "&hourly=temperature_2m");
                if (json.isEmpty())
                    System.err.println("GET didn't succeed");
                else
                    System.out.println(json);
            } catch (Exception e) {
                System.err.println("Couldn't parse coordinates");
            }
        });
        basicSetUp(stage, verticalManager);
    }

    private Pair<Float, Float> parseCoordinates(Pane layoutManager) {
        String latitudeText = ((TextField) (layoutManager.lookup("#LatitudeContainer").lookup("#LatitudeField"))).getText();
        String longitudeText = ((TextField) (layoutManager.lookup("#LongitudeContainer").lookup("#LongitudeField"))).getText();
        float latitudeFloat;
        float longitudeFloat;
        latitudeFloat = Math.round(Float.parseFloat(latitudeText) * 100000) / 100000.0f;
        longitudeFloat = Math.round(Float.parseFloat(longitudeText) * 100000) / 100000.0f;
        if (longitudeFloat > 180 || longitudeFloat < -180 || latitudeFloat < -90 || latitudeFloat > 90)
            throw new RuntimeException();
        return new Pair<>(latitudeFloat, longitudeFloat);
    }
    private void createCoordinateFields(Pane layoutManager){
        JavaFxBuilder.createLabeledTextField(layoutManager, "Latitude");
        JavaFxBuilder.createLabeledTextField(layoutManager, "Longitude");
    }
    private void basicSetUp(Stage stage, Pane layoutManager) {
        Scene scene = new Scene(layoutManager, 640, 480);
        stage.setScene(scene);
        stage.setTitle("Weather checker");
        stage.show();
    }
}