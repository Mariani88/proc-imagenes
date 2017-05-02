package untref.eventhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import untref.controllers.nodeutils.ImageSetter;
import untref.service.ImageEditionService;

public class PowerLawGammaEventHandler implements EventHandler<ActionEvent> {

	private ImageEditionService imageEditionService;
	private ImageView imageView;
	private ImageView imageResultView;

	public PowerLawGammaEventHandler(ImageEditionService imageEditionService, ImageView imageView, ImageView imageResultView) {
		this.imageEditionService = imageEditionService;
		this.imageView = imageView;
		this.imageResultView = imageResultView;
	}

	@Override
	public void handle(ActionEvent event) {
		Stage stage = new Stage();
		VBox pane = new VBox();
		pane.setMaxWidth(200);
		pane.setMaxHeight(200);
		Label gammaLabel = new Label("gamma");
		TextField gammaValue = new TextField();
		gammaValue.setLayoutY(gammaLabel.getLayoutY() + gammaLabel.getHeight());
		Button button = new Button("accept");
		button.setLayoutY(gammaValue.getLayoutY() + gammaValue.getHeight());
		button.setOnAction(event1 -> {
			double gamma = Double.valueOf(gammaValue.getText());
			stage.close();
			ImageSetter.set(imageResultView, imageEditionService.applyPowerLawFunction(gamma, imageView.getImage()));
		});
		pane.getChildren().addAll(gammaLabel, gammaValue, button);
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();
	}
}