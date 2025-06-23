import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class JavaFxBuilder {
    static public void createButton(Pane layoutManager, String title, EventHandler<ActionEvent> event) {
        Button sendButton = new Button(title);
        sendButton.setOnAction(event);
        layoutManager.getChildren().add(sendButton);
    }
    static public void createLabeledTextField(Pane layoutManager, String baseName){
        GridPane box = new GridPane();
        box.setId(baseName.replace(" ", "") + "Container");
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
    static public void createLabeledDataPicker(Pane layoutManager, String baseName) {
        GridPane box = new GridPane();
        box.setId(baseName.replace(" ", "") + "Container");
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
}
