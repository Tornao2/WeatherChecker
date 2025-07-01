import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;

/// Main class for the program
public class Main extends Application {
    ///Object of the custom https handling class
    private final HttpsHandler httphandler = new HttpsHandler();
    ///Starting function which setups all the ui and event handling
    ///Equivalent to main functions
    public void start(Stage stage)  {
        VBox verticalManager = new VBox(8);
        verticalManager.setPadding(new Insets(10, 5, 5, 5));
        verticalManager.setAlignment(Pos.TOP_CENTER);
        createFirstRow(verticalManager);
        createSecondRow(verticalManager);
        Button sendButton = new Button("Send the request");
        sendButton.setOnAction(_ -> {
            if (verticalManager.lookup("Separator") != null){
                verticalManager.getChildren().removeFirst();
                verticalManager.getChildren().removeFirst();
            }
            try {
                JSONObject json = httphandler.sendRequestConnection(composeUrl(verticalManager));
                if (json.isEmpty())
                    throw new RuntimeException("GET didn't succeed");
                else
                    handleJson(verticalManager, json);
            } catch (Exception e) {
                JavaFxBuilder.createHorSeperatorFirst(verticalManager);
                JavaFxBuilder.createCenteredTextFirst(verticalManager, "ErrorLabel", e.getMessage());
            }});
        verticalManager.getChildren().add(sendButton);
        createTabs(verticalManager);
        basicSetUp(stage, verticalManager);
    }
    ///Function that creates a tab view and tabs that decide whether you are looking at current or hourly data
    private void createTabs(Pane layoutManager){
        TabPane tabPane = new TabPane();
        tabPane.setId("TabPane");
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setTabMinWidth(290);
        Tab current = new Tab("Current weather", new VBox());
        Tab hourly = new Tab("Hourly weather", new VBox());
        tabPane.getTabs().addAll(current, hourly);
        layoutManager.getChildren().add(tabPane);
    }
    ///Function that handles json data received from GET request
    private void handleJson(Pane layoutManager, JSONObject receivedJson){
        handleCurrentData(layoutManager, receivedJson.getJSONObject("current"));
        handleHourlyData(layoutManager, receivedJson.getJSONObject("hourly"));
        ((TabPane) (layoutManager.lookup("#TabPane"))).getSelectionModel().select(1);
        ((TabPane) (layoutManager.lookup("#TabPane"))).getSelectionModel().select(0);
    }
    ///Function that handles json data received from GET request that is current
    private void handleCurrentData(Pane layoutManager, JSONObject currentData) {
        CurrentDataDisplay.createWeatherCodeSign(layoutManager, currentData.getInt("weather_code"));
        CurrentDataDisplay.addTemperatureText(layoutManager, currentData.getFloat("temperature_2m"));
        CurrentDataDisplay.addApparentTemperatureText(layoutManager, currentData.getFloat("apparent_temperature"));
        CurrentDataDisplay.addRelativeHumidity(layoutManager, currentData.getInt("relative_humidity_2m"));
        CurrentDataDisplay.addPrecipitation(layoutManager, currentData.getFloat("precipitation"));
        CurrentDataDisplay.addPressure(layoutManager, currentData.getFloat("surface_pressure"));
        CurrentDataDisplay.addWindSpeed(layoutManager, currentData.getFloat("wind_speed_10m"));
    }
    ///Function that handles json data received from GET request that is hourly
    private void handleHourlyData(Pane layoutManager, JSONObject hourlyData) {
        LongTermDataDisplay.createTabPane(layoutManager);
        JSONArray timeArray = hourlyData.getJSONArray("time");
        JSONArray weatherCode = hourlyData.getJSONArray("weather_code");
        JSONArray temperature = hourlyData.getJSONArray("temperature_2m");
        JSONArray apparentTemperature = hourlyData.getJSONArray("apparent_temperature");
        JSONArray relativeHumidity = hourlyData.getJSONArray("relative_humidity_2m");
        JSONArray precipitation = hourlyData.getJSONArray("precipitation");
        JSONArray pressure = hourlyData.getJSONArray("surface_pressure");
        JSONArray windSpeed = hourlyData.getJSONArray("wind_speed_10m");
    }
    ///Function to compose the url used to send the request based on the parameters program user chooses
    private String composeUrl(Pane layoutManager){
        Pair<Float, Float> coordinates = parseCoordinates(layoutManager);
        Pair<String, String> dates = parseDates(layoutManager);
        String coordinatesLink = "https://api.open-meteo.com/v1/forecast?latitude=" + coordinates.getKey() + "&longitude=" + coordinates.getValue() + "&";
        String allParameters = coordinatesLink + "hourly=temperature_2m,relative_humidity_2m,apparent_temperature,weather_code,surface_pressure,wind_speed_10m,precipitation&current=temperature_2m,relative_humidity_2m,apparent_temperature,precipitation,weather_code,surface_pressure,wind_speed_10m&timezone=auto";
        return allParameters + "&start_date=" + dates.getKey() + "&end_date=" + dates.getValue();
    }
    ///Function to parse the coordinates from the textfields and employ some checks to their correctness
    private Pair<Float, Float> parseCoordinates(Pane layoutManager) {
        String latitudeText = ((TextField) (layoutManager.lookup("#LatitudeField"))).getText();
        String longitudeText = ((TextField) (layoutManager.lookup("#LongitudeField"))).getText();
        if (latitudeText.isEmpty() || longitudeText.isEmpty()){
            throw new RuntimeException("Both latitude and longitude must have a valid argument");
        }
        float latitudeFloat = Math.round(Float.parseFloat(latitudeText) * 100000) / 100000.0f;
        float longitudeFloat = Math.round(Float.parseFloat(longitudeText) * 100000) / 100000.0f;
        if (longitudeFloat > 180 || longitudeFloat < -180) {
            throw new RuntimeException("Wrong longitude format");
        }
        if (latitudeFloat < -90 || latitudeFloat > 90){
            throw new RuntimeException("Wrong latitude format");
        }
        return new Pair<>(latitudeFloat, longitudeFloat);
    }
    ///Function to parse the start-end dates for the use in url composition which also employs checks to their correctness
    private Pair<String, String> parseDates (Pane layoutManager){
        LocalDate startDate = ((DatePicker) (layoutManager.lookup("#StartDateDate"))).getValue();
        LocalDate endDate = ((DatePicker) (layoutManager.lookup("#StopDateDate"))).getValue();
        if (startDate == null || endDate == null){
            throw new RuntimeException("Both end and start date should have a valid date");
        }
        if (startDate.isAfter(endDate) || startDate.isEqual(endDate)){
            throw new RuntimeException("End date must be further chronologically");
        }
        if (startDate.minusDays(16).isAfter(LocalDate.now()) || endDate.minusDays(16).isAfter(LocalDate.now())){
            throw new RuntimeException("Date must be less than 17 days ahead of the current date");
        }
        if (startDate.plusMonths(3).isBefore(LocalDate.now()) || endDate.plusMonths(3).isBefore(LocalDate.now())){
            throw new RuntimeException("Date must be less than 3 months before the current date");
        }
        return new Pair<>(startDate.toString(), endDate.toString());
    }
    ///Function to create textfields in the first virtual row of program UI which pertain to latitude and longitude
    private void createFirstRow(Pane layoutManager){
        GridPane firstRow = new GridPane();
        JavaFxBuilder.createLabeledTextField(firstRow, "Latitude");
        JavaFxBuilder.createLabeledTextField(firstRow, "Longitude");
        GridPane.setConstraints(firstRow.getChildren().getFirst(), 1, 0);
        GridPane.setConstraints(firstRow.getChildren().getLast(), 2, 0);
        firstRow.getColumnConstraints().add(new ColumnConstraints(0));
        firstRow.getColumnConstraints().add(new ColumnConstraints(320));
        layoutManager.getChildren().add(firstRow);
    }
    ///Function to create data pickers in the second virtual row of program UI which pertain to start and end dates
    private void createSecondRow(Pane layoutManager){
        GridPane secondRow = new GridPane();
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
        stage.setResizable(false);
        layoutManager.setStyle("-fx-background-color: #f5fcfc ;");
        stage.setScene(scene);
        stage.setTitle("Weather checker");
        stage.show();
    }
}