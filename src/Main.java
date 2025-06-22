import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONObject;

public class Main extends Application {
    HttpsHandler httphandler = new HttpsHandler();

    public void start(Stage stage)  {
        VBox verticalManager = new VBox();
        verticalManager.setAlignment(Pos.BASELINE_CENTER);
        createSendButton(verticalManager);
        basicSetUp(stage, verticalManager);
    }

    public void createSendButton(Pane layoutManager) {
        Button sendButton = new Button("Send Request");
        EventHandler<ActionEvent> event = _ -> {
            JSONObject json = httphandler.sendRequestConnection("");
            System.out.println(json);
        };
        sendButton.setOnAction(event);
        layoutManager.getChildren().add(sendButton);
    }

    public void basicSetUp(Stage stage, Pane layoutManager) {
        Scene scene = new Scene(layoutManager, 320, 240);
        stage.setScene(scene);
        stage.setTitle("Weather checker");
        stage.show();
    }
}