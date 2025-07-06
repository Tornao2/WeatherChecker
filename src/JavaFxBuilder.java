import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Objects;

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
        separator.getStylesheets().add((JavaFxBuilder.class.getResource("resources/look.css")).toExternalForm());
        layoutManager.getChildren().addFirst(separator);
    }
    /// Static function to create a text entity with defined by user id at the first position in the layout manager
    static public void createCenteredTextFirst(Pane layoutManager, String idName, String statement){
        Label text = new Label(statement);
        text.setId(idName);
        text.getStylesheets().add((JavaFxBuilder.class.getResource("resources/look.css")).toExternalForm());
        layoutManager.getChildren().addFirst(text);
    }
}
