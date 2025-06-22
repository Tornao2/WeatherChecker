import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class JavaFxBuilder {
    static public void createButton(Pane layoutManager, String title, EventHandler<ActionEvent> event) {
        Button sendButton = new Button(title);
        sendButton.setOnAction(event);
        layoutManager.getChildren().add(sendButton);
    }
    static public void createLabeledTextField(Pane layoutManager, String baseName){
        HBox box = new HBox(6);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setId(baseName + "Container");
        TextField field = new TextField();
        field.setId(baseName + "Field");
        Label label = new Label(baseName + ": ");
        box.getChildren().addAll(label, field);
        layoutManager.getChildren().add(box);
    }
}
