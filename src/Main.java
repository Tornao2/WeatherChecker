import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    public void start(Stage stage)  {
        Scene programScene = basicSetUp(stage);
    }

    public Scene basicSetUp(Stage stage) {
        BorderPane layoutManager = new BorderPane();
        Scene scene = new Scene(layoutManager, 320, 240);
        stage.setScene(scene);
        stage.setTitle("Weather checker");
        stage.show();
        return scene;
    }
}