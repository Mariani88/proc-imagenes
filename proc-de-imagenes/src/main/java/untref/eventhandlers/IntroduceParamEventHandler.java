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
import untref.service.CreationImageService;
import untref.service.ImageArithmeticOperationService;

public class IntroduceParamEventHandler implements EventHandler<ActionEvent> {

	private ImageArithmeticOperationService imageArithmeticOperationService;
	private ImageView imageView;
	private ImageView imageResultView;

	public IntroduceParamEventHandler(ImageArithmeticOperationService imageArithmeticOperationService, ImageView imageView,
			ImageView imageResultView) {
		this.imageArithmeticOperationService = imageArithmeticOperationService;
		this.imageView = imageView;
		this.imageResultView = imageResultView;
	}

	@Override
	public void handle(ActionEvent event) {
		Stage stage = new Stage();
		VBox pane = new VBox();
		pane.setMaxWidth(200);
		pane.setMaxHeight(200);
		Label scalarLabel = new Label("scalar");
		TextField scalarValue = new TextField();
		scalarValue.setLayoutY(scalarLabel.getLayoutY() + scalarLabel.getHeight());
		Button button = new Button("accept");
		button.setLayoutY(scalarValue.getLayoutY() + scalarValue.getHeight());
		button.setOnAction(event1 -> {
			double scalar = Double.valueOf(scalarValue.getText());
			stage.close();
			ImageSetter.set(imageResultView, imageArithmeticOperationService.multiplyImageByScalar(scalar, imageView.getImage()));
		});
		pane.getChildren().addAll(scalarLabel, scalarValue, button);
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();
	}
}