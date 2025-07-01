import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

///Class for handling long term data
public class LongTermDataDisplay {
    ///Static function creating a tab pane for choosing which data to view
    static public void createTabPane(Pane layoutManager){
        if (layoutManager.lookup("#LongTabPane") == null) {
            VBox layout = (VBox) ((TabPane) (layoutManager.lookup("#TabPane"))).getTabs().getLast().getContent();
            TabPane tabPane = new TabPane();
            tabPane.setTabMinWidth(82);
            tabPane.setId("LongTabPane");
            tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
            Tab temp = new Tab("Temperature", new VBox());
            Tab AppTemp = new Tab("Apparent temperature", new VBox());
            Tab humidity = new Tab("Humidity", new VBox());
            Tab precipitation = new Tab("Precipitation", new VBox());
            Tab windSpeed = new Tab("Wind speed", new VBox());
            Tab pressure = new Tab("Pressure", new VBox());
            tabPane.getTabs().addAll(temp, AppTemp, humidity, precipitation, windSpeed, pressure);
            layout.getChildren().add(tabPane);
        }
    }
}
