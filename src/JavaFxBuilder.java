import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import org.json.JSONArray;

import java.time.chrono.Chronology;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**
 * Class implementing the builder archetype for objects connected to javafx
 */
public class JavaFxBuilder {
     /**
      * Static method to create javaFx textfield with a label predefined to be in one row using a gridpane
      * and setting id for the field for later searches by using the basename provided by user
      */
    static public void createLabeledTextField(Pane layoutManager, String baseName){
        GridPane box = new GridPane();
        box.setAlignment(Pos.CENTER);
        TextField field = new TextField();
        field.setId(baseName.replace(" ", "") + "Field");
        Label label = new Label(baseName + ": ");
        GridPane.setConstraints(label, 1, 0);
        GridPane.setConstraints(field, 2, 0);
        field.setMinWidth(100);
        box.getColumnConstraints().add(new ColumnConstraints(0));
        box.getColumnConstraints().add(new ColumnConstraints(70));
        box.getChildren().addAll(label, field);
        layoutManager.getChildren().add(box);
    }
     /**
      * Static function to create a labeled data picker object with a predefined label, both positioned in one row;
      * id set to the data picker itself is created by concating Date with the base name
      */
    static public void createLabeledDataPicker(Pane layoutManager, String baseName) {
        GridPane box = new GridPane();
        DatePicker picker = new DatePicker();
        picker.setChronology(Chronology.ofLocale(Locale.getDefault()));
        picker.setId(baseName.replace(" ", "")+"Date");
        Label label = new Label(baseName + ": ");
        layoutManager.getChildren().addAll();
        GridPane.setConstraints(label, 1, 0);
        GridPane.setConstraints(picker, 2, 0);
        box.getColumnConstraints().add(new ColumnConstraints(0));
        box.getColumnConstraints().add(new ColumnConstraints(70));
        box.getChildren().addAll(label, picker);
        layoutManager.getChildren().add(box);
    }
    /// Static function to create a horizontal seperator with predefined id that spans the entire screen at the first position in the layout manager
    static public void createHorSeperatorFirst(Pane layoutManager) {
        Separator separator = new Separator();
        separator.setId("Separator");
        separator.setMaxWidth(640);
        layoutManager.getChildren().addFirst(separator);
    }
    /// Static function to create a text entity with defined by user id at the first position in the layout manager
    static public void createCenteredTextFirst(Pane layoutManager, String idName, String statement){
        Label text = new Label(statement);
        text.setId(idName);
        layoutManager.getChildren().addFirst(text);
    }
    /// Static function to create the alert window to choose location
    static public int createAlert(JSONArray response){
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Choose correct location");
        VBox content = new VBox();
        content.setSpacing(10);
        ScrollPane actualContent = new ScrollPane();
        actualContent.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        String res = Objects.requireNonNull(JavaFxBuilder.class.getResource("resources/look.css")).toExternalForm();
        final int[] selectedIndex = {-1};
        for (int i = 0; i < response.length(); i++){
            if (!response.getJSONObject(i).getString("type").equals("city") &&
                    !response.getJSONObject(i).getString("type").equals("town") &&
                    !response.getJSONObject(i).getString("type").equals("village") &&
                    !response.getJSONObject(i).getString("type").equals("hamlet") &&
                    !response.getJSONObject(i).getString("class").equals("boundary")){
                continue;
            }
            VBox singleResult = new VBox(new Text(response.getJSONObject(i).getString("display_name")));
            final int index = i;
            singleResult.setOnMouseClicked(_ -> {
                for (Node node : content.getChildren()) {
                    node.setStyle("-fx-background-color: #d1e8ef;-fx-background-radius: 15;-fx-font-size: 16;");
                }
                singleResult.setStyle("-fx-background-color: #5bb38b;-fx-background-radius: 15;-fx-font-size: 16;");
                selectedIndex[0] = index;
            });
            singleResult.setPadding(new Insets(10));
            singleResult.setId("ResultingPlaces");
            singleResult.getStylesheets().add(res);
            content.getChildren().add(singleResult);
        }
        actualContent.setContent(content);
        alert.getDialogPane().setContent(actualContent);
        ButtonType okButton = new ButtonType("Choose the location", ButtonBar.ButtonData.LEFT);
        alert.getButtonTypes().add(okButton);
        alert.initModality(Modality.APPLICATION_MODAL);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()){
            return selectedIndex[0];
        } else {
            return -1;
        }
    }
}
