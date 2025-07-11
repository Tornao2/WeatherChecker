import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.json.JSONArray;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static java.time.format.FormatStyle.*;

///Class for handling long term data
public class LongTermDataDisplay {
    /// Static function for creating a view of indepth data after clicking at a certain node in the scroll view
    static public void createMenuForChosenNode(Pane layoutManager){
        VBox layout = (VBox) ((StackPane) ((TabPane) (layoutManager.lookup("#tabPaneOverall"))).getTabs().getLast().getContent()).getChildren().getFirst();
        VBox packingRows;
        if (layout.lookup("#PackingRows") != null) {
            ((VBox) (layout.getChildren().getFirst())).getChildren().clear();
            packingRows = (VBox) layout.getChildren().getFirst();
        } else {
            packingRows = new VBox();
            packingRows.setAlignment(Pos.CENTER);
            packingRows.setSpacing(14);
            packingRows.setMinHeight(130);
            VBox.setMargin(packingRows, new Insets(15));
            layout.getChildren().add(packingRows);
            packingRows.setId("PackingRows");
        }
        fillOutIndepth(packingRows);
    }
    /// Static function to fill out the view of the indepth data
    static private void fillOutIndepth(Pane parent){
        HBox firstRow = new HBox();
        Text apparentTemperature = new Text("Apparent Temperature: None");
        apparentTemperature.setId("ApparentTempLong");
        Text humidity = new Text("Humidity: None");
        humidity.setId("HumidityLong");
        StackPane left1 = new StackPane(apparentTemperature);
        StackPane right1 = new StackPane(humidity);
        left1.prefWidthProperty().bind(firstRow.widthProperty().multiply(1.0/2));
        right1.prefWidthProperty().bind(firstRow.widthProperty().multiply(1.0/2));
        firstRow.getChildren().addAll(left1, right1);
        HBox secondRow = new HBox();
        Text precipitation = new Text("Precipitation: None");
        precipitation.setId("PrecipitationLong");
        Text pressure = new Text("Pressure: None");
        pressure.setId("PressureLong");
        StackPane left2 = new StackPane(precipitation);
        StackPane right2 = new StackPane(pressure);
        left2.prefWidthProperty().bind(secondRow.widthProperty().multiply(1.0/2));
        right2.prefWidthProperty().bind(secondRow.widthProperty().multiply(1.0/2));
        secondRow.getChildren().addAll(left2, right2);
        HBox lastRow = new HBox();
        Text windSpeed = new Text("Wind speed: None");
        windSpeed.setId("WindSpeedLong");
        lastRow.setAlignment(Pos.CENTER);
        lastRow.getChildren().add(windSpeed);
        parent.getChildren().addAll(firstRow, secondRow, lastRow);
    }
    /// Static function for creating a scroll view which shows time, temperature and weather codes at given hour
    static public void createScrollingView(Pane layoutManager, JSONArray timeArray, JSONArray temperature, JSONArray weatherCodes
                                           ,JSONArray apparentTemperature, JSONArray humidity, JSONArray precipitation,
                                           JSONArray pressure, JSONArray windSpeed){
        VBox layout = (VBox) ((StackPane) ((TabPane) (layoutManager.lookup("#tabPaneOverall"))).getTabs().getLast().getContent()).getChildren().getFirst();
        HBox content = fillOutContent(layoutManager, timeArray, temperature, weatherCodes, apparentTemperature, humidity,
                precipitation, pressure, windSpeed);
        ScrollPane scrollView;
        if (layout.lookup("#ScrollView") != null) {
            scrollView = (ScrollPane) layout.lookup("#ScrollView");
            ((HBox) scrollView.getContent()).getChildren().clear();
        } else {
            scrollView = new ScrollPane();
            VBox.setMargin(scrollView, new Insets(5, 0, 0, 0));
            scrollView.setMinHeight(150);
            scrollView.setFitToHeight(true);
            scrollView.setId("ScrollView");
            layout.getChildren().add(scrollView);
        }
        scrollView.setHvalue(0);
        scrollView.setContent(content);
    }
    /// Fill out the content of scrollView
    static private HBox fillOutContent(Pane layoutManager, JSONArray timeArray, JSONArray temperature, JSONArray weatherCodes,
                                       JSONArray apparentTemperature, JSONArray humidity, JSONArray precipitation,
                                       JSONArray pressure, JSONArray windSpeed) {
        HBox content = new HBox();
        content.setId("ScrollContent");
        content.setAlignment(Pos.TOP_LEFT);
        ArrayList<VBox> vboxes = new ArrayList<>();
        for(int i = 0; i < timeArray.length(); i++){
            VBox singlePane = new VBox();
            singlePane.setStyle("-fx-background-color: #d1e8ef; -fx-background-radius: 6px;");
            vboxes.add(singlePane);
            HBox.setMargin(singlePane, new Insets(5));
            singlePane.setFillWidth(true);
            singlePane.setAlignment(Pos.TOP_CENTER);
            singlePane.setId("ScrollViewPane");
            ImageView weatherImage = new ImageView();
            LongTermDataDisplay.setImage(weatherCodes.getInt(i), weatherImage);
            weatherImage.setPreserveRatio(true);
            weatherImage.setFitHeight(50);
            Text tempText = new Text();
            tempText.setFont(new Font(22));
            tempText.setText(temperature.getFloat(i) + "°C");
            Text hourText = new Text();
            hourText.setFont(new Font(17));
            String[] temp = ((LocalDateTime.parse(timeArray.getString(i))).
                    format(DateTimeFormatter.ofLocalizedDateTime(MEDIUM, SHORT))).split(",");
            hourText.setText(temp[0] + "\n" + temp[2].strip());
            hourText.setTextAlignment(TextAlignment.CENTER);
            singlePane.getChildren().addAll(weatherImage, tempText, hourText);
            content.getChildren().add(singlePane);
        }
        setEvents(layoutManager, vboxes, apparentTemperature, humidity, precipitation, pressure, windSpeed);
        return content;
    }
    /// Function to set up onMouseClicked event for vboxes inside the scrolling view
    static private void setEvents(Pane layoutManager, ArrayList<VBox> vboxes, JSONArray apparentTemperature,
                                  JSONArray humidity, JSONArray precipitation, JSONArray pressure, JSONArray windSpeed){
        for (int i = 0; i < vboxes.size(); i++) {
            int index = i;
            VBox vb = vboxes.get(i);
            vb.setOnMouseClicked(_ -> {
                ((Text) layoutManager.lookup("#ApparentTempLong")).setText("Apparent Temperature: "
                        + apparentTemperature.getFloat(index) + "°C");
                ((Text) layoutManager.lookup("#HumidityLong")).setText("Humidity: "
                        + humidity.getInt(index) + "%");
                ((Text) layoutManager.lookup("#PrecipitationLong")).setText("Precipitation: "
                        + precipitation.getFloat(index) + "mm");
                ((Text) layoutManager.lookup("#PressureLong")).setText("Pressure: "
                        + pressure.getFloat(index) + "hPa");
                ((Text) layoutManager.lookup("#WindSpeedLong")).setText("Wind speed: "
                        + windSpeed.getFloat(index) + "km/s");
                vboxes.forEach(v -> v.setStyle("-fx-background-color: #d1e8ef; -fx-background-radius: 6px;"));
                vb.setStyle("-fx-background-color: #5bb38b; -fx-background-radius: 6px;");
            });
        }
    }
    ///Set WeatherCode image
    static private void setImage(int weatherCode, ImageView weatherImage){
        switch(weatherCode){
            case 0, 1:
                weatherImage.setImage(new Image("resources/Sunny.png"));
                break;
            case 2, 3:
                weatherImage.setImage(new Image("resources/Cloudy.png"));
                break;
            case 45, 48:
                weatherImage.setImage(new Image("resources/Foggy.png"));
                break;
            case 51, 53, 55, 56, 57:
                weatherImage.setImage(new Image("resources/LightRain.png"));
                break;
            case 61, 63, 65, 66, 67, 80, 81, 82:
                weatherImage.setImage(new Image("resources/HeavyRain.png"));
                break;
            case 71, 73, 75, 77, 85, 86:
                weatherImage.setImage(new Image("resources/Snowy.png"));
                break;
            case 95, 96, 977:
                weatherImage.setImage(new Image("resources/Thunder.png"));
                break;
        }
    }
}
