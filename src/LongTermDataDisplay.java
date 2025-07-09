import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.json.JSONArray;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.format.FormatStyle.LONG;
import static java.time.format.FormatStyle.MEDIUM;

///Class for handling long term data
public class LongTermDataDisplay {
    ///Static function creating a tab pane for choosing which data to view
    static public void createTabPane(Pane layoutManager){
        if (layoutManager.lookup("#LongTabPane") == null) {
            VBox layout = (VBox) ((StackPane) ((TabPane) (layoutManager.lookup("#tabPaneOverall"))).getTabs().getLast().getContent()).getChildren().getFirst();
            TabPane tabPane = new TabPane();
            tabPane.setTabMinWidth(80);
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
    /// Static function for creating a xy chart of temperatures
    static public void createTemperatureChart(Pane layoutManager, JSONArray timeArray, JSONArray temperature){
        VBox tempCont = (VBox) ((TabPane) (layoutManager.lookup("#LongTabPane"))).getTabs().getFirst().getContent();
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time");
        yAxis.setLabel("Temperature");
        XYChart.Series data = new XYChart.Series();
        for (int i = 0; i < timeArray.length(); i++) {
            String x = LocalDateTime.parse(timeArray.getString(i))
                    .format(DateTimeFormatter.ofLocalizedDateTime(LONG, MEDIUM));
            Number y = temperature.getFloat(i);
            data.getData().add(new XYChart.Data<>(x, y));
        }
        if (layoutManager.lookup("#TempChart") == null) {
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setFitToHeight(true);
            scrollPane.setFitToWidth(false);
            LineChart<String, Number> chart = new LineChart<>(xAxis, yAxis);
            chart.setCreateSymbols(false);
            chart.setLegendVisible(false);
            chart.setMinWidth(timeArray.length() * 21);
            chart.getData().add(data);
            chart.setId("TempChart");
            scrollPane.setContent(chart);
            tempCont.getChildren().add(scrollPane);
        } else {
            LineChart<String, Number> chart = (LineChart<String, Number>) layoutManager.lookup("#TempChart");
            chart.getData().removeFirst();
            chart.getData().add(data);
        }
    }
}
