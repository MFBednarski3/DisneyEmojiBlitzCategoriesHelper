package FX;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class MainScreen extends Application {

    /**
     * @author Mike Bednarski This is main screen program that will create the
     * display based on the FXML file
     * @param args
     */

    public static void execute(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("DisneyEBFX.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Disney Emoji Blitz Categories");
        primaryStage.show();
    }

}
