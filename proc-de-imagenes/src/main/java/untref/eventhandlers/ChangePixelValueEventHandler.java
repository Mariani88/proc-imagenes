package untref.eventhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import untref.service.ImageEditionService;

public class ChangePixelValue implements EventHandler<ActionEvent> {

	private Label valueLabel;
	private TextField valueField;
	private ImageView imageView;
	private ImageView imageResultView;
	private TextField xField;
	private TextField yField;
	private ImageEditionService imageEditionService;
	private TextField editableValueField;

	public ChangePixelValue(Label valueLabel, TextField valueField, ImageView imageView, ImageView imageResultView, TextField xField,
			TextField yField, ImageEditionService imageEditionService) {
		this.valueLabel = valueLabel;
		this.valueField = valueField;
		this.imageView = imageView;
		this.imageResultView = imageResultView;
		this.xField = xField;
		this.yField = yField;
		this.imageEditionService = imageEditionService;
		this.editableValueField = new TextField();
	}

	public void handle(ActionEvent event) {
		final Stage stage = new Stage();
		Button saveButton = new Button("save");
		editableValueField.setText(valueField.getText());
		final Scene scene = new Scene(new VBox(valueLabel, editableValueField, saveButton));
		stage.setScene(scene);
		stage.setMaxWidth(200);
		stage.setHeight(200);
		stage.show();
		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				modifyImage(imageView.getImage());
				stage.close();
			}
		});
	}

	//TODO testear mandando esto a otro objeto
	private void modifyImage(Image image) {
		Image writableImage = imageEditionService.modifyPixelValue(image, xField.getText(), yField.getText(), editableValueField.getText());
		imageResultView.setImage(writableImage);
	}

}