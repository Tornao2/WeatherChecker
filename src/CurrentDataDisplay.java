import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

///Class for making UI for current weather data
public class CurrentDataDisplay {
    ///Static function importing an image and using it for weather code display
    static public void createWeatherCodeSign(Pane layoutManager, int weatherCode){
        VBox layout = (VBox) ((TabPane) (layoutManager.lookup("#TabPane"))).getTabs().getFirst().getContent();
        ImageView weatherImage;
        if(layout.lookup("#WeatherCodeDisplay") == null) {
            weatherImage = new ImageView();
            weatherImage.setId("WeatherCodeDisplay");
            layout.getChildren().add(weatherImage);
        } else {
            weatherImage = (ImageView) layout.lookup("#WeatherCodeDisplay");
        }
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
    ///Static function to create a text which shows current temperature
    static public void addTemperatureText(Pane layoutManager, float temperature){
        VBox layout = (VBox) ((TabPane) (layoutManager.lookup("#TabPane"))).getTabs().getFirst().getContent();
        if(layout.lookup("#CurrentTemperatureText") == null) {
            Text temperatureText = new Text("Current Temperature: " + temperature + "°C");
            temperatureText.setId("CurrentTemperatureText");
            layout.getChildren().addLast(temperatureText);
        } else {
            ((Text) (layout.lookup("#CurrentTemperatureText"))).setText("Current Temperature: " + temperature + "°C");
        }
    }
    ///Static function to create a text which shows current apparent temperature
    static public void addApparentTemperatureText(Pane layoutManager, float temperature){
        VBox layout = (VBox) ((TabPane) (layoutManager.lookup("#TabPane"))).getTabs().getFirst().getContent();
        if(layout.lookup("#CurrentTemperatureApparent") == null) {
            Text temperatureText = new Text("Apparent Temperature: " + temperature + "°C");
            temperatureText.setId("CurrentTemperatureApparent");
            layout.getChildren().addLast(temperatureText);
        } else {
            ((Text) (layout.lookup("#CurrentTemperatureApparent"))).setText("Apparent Temperature: " + temperature + "°C");
        }
    }
    ///Static function to create a text which shows current humidity in percentages
    static public void addRelativeHumidity(Pane layoutManager, int humidity){
        VBox layout = (VBox) ((TabPane) (layoutManager.lookup("#TabPane"))).getTabs().getFirst().getContent();
        if(layout.lookup("#CurrentHumidity") == null) {
            Text humidityText = new Text("Humidity: " + humidity + "%");
            humidityText.setId("CurrentHumidity");
            layout.getChildren().addLast(humidityText);
        } else {
            ((Text) (layout.lookup("#CurrentHumidity"))).setText("Humidity: " + humidity + "%");
        }
    }
    ///Static function to create a text which shows current precipitation
    static public void addPrecipitation(Pane layoutManager, float precipitation){
        VBox layout = (VBox) ((TabPane) (layoutManager.lookup("#TabPane"))).getTabs().getFirst().getContent();
        if(layout.lookup("#CurrentPrecipitation") == null) {
            Text precipitationText = new Text("Precipitation: " + precipitation + "mm");
            precipitationText.setId("CurrentPrecipitation");
            layout.getChildren().addLast(precipitationText);
        } else {
            ((Text) (layout.lookup("#CurrentPrecipitation"))).setText("Precipitation: " + precipitation + "mm");
        }
    }
    ///Static function to create a text which shows current surface pressure
    static public void addPressure(Pane layoutManager, float pressure){
        VBox layout = (VBox) ((TabPane) (layoutManager.lookup("#TabPane"))).getTabs().getFirst().getContent();
        if(layout.lookup("#CurrentPressure") == null) {
            Text pressureText = new Text("Pressure: " + pressure + "hPa");
            pressureText.setId("CurrentPressure");
            layout.getChildren().addLast(pressureText);
        } else {
            ((Text) (layout.lookup("#CurrentPressure"))).setText("Pressure: " + pressure + "hPa");
        }
    }
    ///Static function to create a text which shows current wind speed
    static public void addWindSpeed(Pane layoutManager, float windSpeed){
        VBox layout = (VBox) ((TabPane) (layoutManager.lookup("#TabPane"))).getTabs().getFirst().getContent();
        if(layout.lookup("#CurrentWindSpeed") == null) {
            Text windText = new Text("Wind speed: " + windSpeed + "km/h");
            windText.setId("CurrentWindSpeed");
            layout.getChildren().addLast(windText);
        } else {
            ((Text) (layout.lookup("#CurrentWindSpeed"))).setText("Wind speed: " + windSpeed + "km/h");
        }
    }
}
