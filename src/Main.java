import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONObject;

public class Main extends Application {
    HttpsHandler httphandler = new HttpsHandler();

    public void start(Stage stage)  {
        VBox verticalManager = new VBox(6);
        verticalManager.setPadding(new Insets(10));
        verticalManager.setAlignment(Pos.BASELINE_CENTER);
        createCoordinateFields(verticalManager);
        JavaFxBuilder.createButton(verticalManager, "Send a request", _ -> {
            JSONObject json = httphandler.sendRequestConnection("latitude=52.52&longitude=13.41&hourly=temperature_2m");
            if (json.isEmpty())
                System.out.println("GET didn't succeed");
            else
                System.out.println(json);
        });
        basicSetUp(stage, verticalManager);
    }

    public void createCoordinateFields(Pane layoutManager){
        JavaFxBuilder.createLabeledTextField(layoutManager, "Latitude");
        JavaFxBuilder.createLabeledTextField(layoutManager, "Longitude");
    }

    public void basicSetUp(Stage stage, Pane layoutManager) {
        Scene scene = new Scene(layoutManager, 640, 480);
        stage.setScene(scene);
        stage.setTitle("Weather checker");
        stage.show();
    }
}