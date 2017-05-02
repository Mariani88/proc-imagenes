package untref.controllers.nodeutils;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class ParametersWindowsFactory {

	public void create(List<Node> parametersNodes, EventHandler<ActionEvent> actionByAccept) {
		Stage stage = new Stage();
		VBox pane = new VBox();
		pane.setMaxWidth(200);
		pane.setMaxHeight(200);
		Button button = new Button("accept");
		button.setOnAction(event -> {
			actionByAccept.handle(event);
			stage.close();
		});
		pane.getChildren().addAll(parametersNodes);
		pane.getChildren().add(button);
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();
	}
}