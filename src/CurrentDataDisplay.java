import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

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
}
