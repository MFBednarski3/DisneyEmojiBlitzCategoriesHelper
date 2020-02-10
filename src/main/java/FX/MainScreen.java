package FX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class MainScreen extends Application {
	
	/**
	 * @author Mike Bednarski This is main screen program that will create the
	 *         display based on the FXML file
	 * @param args
	 */

	public static void execute(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("DisneyEBFX.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Disney Emoji Blitz Categories");
		primaryStage.show();
	}

}
