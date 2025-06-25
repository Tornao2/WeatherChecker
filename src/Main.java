import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.json.JSONObject;

import java.time.LocalDate;
/// Main class for the program
public class Main extends Application {
    ///Object of the custom https handling class
    private final HttpsHandler httphandler = new HttpsHandler();
    ///Starting function which setups all the ui and event handling
    ///Equivalent to main functions
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
                System.err.println("Couldn't create a link" + e);
            }
        });
        basicSetUp(stage, verticalManager);
    }
    ///Function to compose the url used to send the request based on the parameters program user chooses
    private String composeUrl(Pane layoutManager){
        Pair<Float, Float> coordinates = parseCoordinates(layoutManager);
        Pair<String, String> dates = parseDates(layoutManager);
        String coordinatesLink = "https://api.open-meteo.com/v1/forecast?latitude=" + coordinates.getKey() + "&longitude=" + coordinates.getValue() + "&hourly=temperature_2m";
        String dateLink = coordinatesLink + "&start_date=" + dates.getKey() + "&end_date=" + dates.getValue();
        return dateLink;
    }
    ///Function to parse the coordinates from the textfields and employ some checks to their correctness
    private Pair<Float, Float> parseCoordinates(Pane layoutManager) {
        String latitudeText = ((TextField) (layoutManager.lookup("#FirstRow").lookup("#LatitudeContainer").lookup("#LatitudeField"))).getText();
        String longitudeText = ((TextField) (layoutManager.lookup("#FirstRow").lookup("#LongitudeContainer").lookup("#LongitudeField"))).getText();
        float latitudeFloat;
        float longitudeFloat;
        latitudeFloat = Math.round(Float.parseFloat(latitudeText) * 100000) / 100000.0f;
        longitudeFloat = Math.round(Float.parseFloat(longitudeText) * 100000) / 100000.0f;
        if (longitudeFloat > 180 || longitudeFloat < -180) {
            System.err.println("Wrong longitude format");
            throw new RuntimeException();
        }
        if (latitudeFloat < -90 || latitudeFloat > 90){
            System.err.println("Wrong latitude format");
            throw new RuntimeException();
        }
        return new Pair<>(latitudeFloat, longitudeFloat);
    }
    ///Function to parse the start-end dates for the use in url composition which also employs checks to their correctness
    private Pair<String, String> parseDates (Pane layoutManager){
        LocalDate startDate = ((DatePicker) (layoutManager.lookup("#SecondRow").lookup("#StartDateContainer")
                .lookup("#StartDateDate"))).getValue();
        LocalDate endDate = ((DatePicker) (layoutManager.lookup("#SecondRow").lookup("#StopDateContainer")
                .lookup("#StopDateDate"))).getValue();
        if (startDate.isAfter(endDate) || startDate.isEqual(endDate)){
            System.err.println("End date must be further chronologically");
            throw new RuntimeException();
        }
        if (startDate.minusDays(16).isAfter(LocalDate.now()) || endDate.minusDays(16).isAfter(LocalDate.now())){
            System.err.println("Date must be less than 17 days ahead of the current date");
            throw new RuntimeException();
        }
        if (startDate.plusMonths(3).isBefore(LocalDate.now()) || endDate.plusMonths(3).isBefore(LocalDate.now())){
            System.err.println("Date must be less than 3 months before the current date");
            throw new RuntimeException();
        }
        return new Pair<>(startDate.toString(), endDate.toString());
    }
    ///Function to create textfields in the first virtual row of program UI which pertain to latitude and longitude
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
    ///Function to create data pickers in the second  virtual row of program UI which pertain to start and end dates
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
    ///Function which sets up the scene object
    private void basicSetUp(Stage stage, Pane layoutManager) {
        Scene scene = new Scene(layoutManager, 640, 480);
        stage.setScene(scene);
        stage.setTitle("Weather checker");
        stage.show();
    }
}