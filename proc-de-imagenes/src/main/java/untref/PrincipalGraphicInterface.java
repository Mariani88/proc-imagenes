package untref;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.List;

public class PrincipalGraphicInterface extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		AnchorPane principalPane = new PrincipalGraphicInterfaceController().initInterfaceElements();

		Scene scene = new Scene(principalPane, 800, 600);
		primaryStage.setScene(scene);



		primaryStage.setMaximized(true);
		primaryStage.show();
	}
}