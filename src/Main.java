import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.json.JSONObject;

public class Main extends Application {
    private HttpsHandler httphandler = new HttpsHandler();

    public void start(Stage stage)  {
        VBox verticalManager = new VBox(4);
        verticalManager.setPadding(new Insets(6));
        verticalManager.setAlignment(Pos.BASELINE_CENTER);
        createFirstRow(verticalManager);
        createSecondRow(verticalManager);
        JavaFxBuilder.createButton(verticalManager, "Send a request", _ -> {
            try {
                JSONObject json = httphandler.sendRequestConnection(composeUrl(verticalManager));
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

    private String composeUrl(Pane layoutManager){
        Pair<Float, Float> coordinates = parseCoordinates(layoutManager);
        String returnLink = "latitude=" + coordinates.getKey() + "&longitude=" + coordinates.getValue() + "&hourly=temperature_2m";
        return returnLink;
    }
    private Pair<Float, Float> parseCoordinates(Pane layoutManager) {
        String latitudeText = ((TextField) (layoutManager.lookup("#FirstRow").lookup("#LatitudeContainer").lookup("#LatitudeField"))).getText();
        String longitudeText = ((TextField) (layoutManager.lookup("#FirstRow").lookup("#LongitudeContainer").lookup("#LongitudeField"))).getText();
        float latitudeFloat;
        float longitudeFloat;
        latitudeFloat = Math.round(Float.parseFloat(latitudeText) * 100000) / 100000.0f;
        longitudeFloat = Math.round(Float.parseFloat(longitudeText) * 100000) / 100000.0f;
        if (longitudeFloat > 180 || longitudeFloat < -180 || latitudeFloat < -90 || latitudeFloat > 90)
            throw new RuntimeException();
        return new Pair<>(latitudeFloat, longitudeFloat);
    }
    private void createFirstRow(Pane layoutManager){
        GridPane firstRow = new GridPane();
        firstRow.setId("FirstRow");
        JavaFxBuilder.createLabeledTextField(firstRow, "Latitude");
        JavaFxBuilder.createLabeledTextField(firstRow, "Longitude");
        GridPane.setConstraints(firstRow.getChildren().getFirst(), 1, 0);
        GridPane.setConstraints(firstRow.getChildren().getLast(), 2, 0);
        firstRow.getColumnConstraints().add(new ColumnConstraints(0));
        firstRow.getColumnConstraints().add(new ColumnConstraints(320));
        layoutManager.getChildren().add(firstRow);
    }
    private void createSecondRow(Pane layoutManager){
        GridPane secondRow = new GridPane();
        secondRow.setId("SecondRow");
        JavaFxBuilder.createLabeledDataPicker(secondRow, "Start Date");
        JavaFxBuilder.createLabeledDataPicker(secondRow, "Stop Date");
        GridPane.setConstraints(secondRow.getChildren().getFirst(), 1, 0);
        GridPane.setConstraints(secondRow.getChildren().getLast(), 2, 0);
        secondRow.getColumnConstraints().add(new ColumnConstraints(0));
        secondRow.getColumnConstraints().add(new ColumnConstraints(320));
        layoutManager.getChildren().add(secondRow);
    }
    private void basicSetUp(Stage stage, Pane layoutManager) {
        Scene scene = new Scene(layoutManager, 640, 480);
        stage.setScene(scene);
        stage.setTitle("Weather checker");
        stage.show();
    }
}