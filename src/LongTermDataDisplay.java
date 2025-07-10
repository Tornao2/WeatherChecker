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

import static java.time.format.FormatStyle.*;

///Class for handling long term data
public class LongTermDataDisplay {
    /// Static function for creating a scroll view which shows time, temperature and weather codes at given hour
    static public void createScrollingView(Pane layoutManager, JSONArray timeArray, JSONArray temperature, JSONArray weatherCodes){
        VBox layout = (VBox) ((StackPane) ((TabPane) (layoutManager.lookup("#tabPaneOverall"))).getTabs().getLast().getContent()).getChildren().getFirst();
        HBox content = fillOutContent(timeArray, temperature, weatherCodes);
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
    static private HBox fillOutContent(JSONArray timeArray, JSONArray temperature, JSONArray weatherCodes) {
        HBox content = new HBox();
        content.setId("ScrollContent");
        content.setSpacing(10);
        content.setAlignment(Pos.TOP_LEFT);
        for(int i = 0; i < timeArray.length(); i++){
            VBox singlePane = new VBox();
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
        return content;
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
