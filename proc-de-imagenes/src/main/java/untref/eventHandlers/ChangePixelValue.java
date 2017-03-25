package untref.eventHandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChangePixelValue implements EventHandler<ActionEvent> {

	private Label valueLabel;
	private TextField valueField;
	private ImageView imageView;
	private ImageView imageResultView;
	private TextField xField;
	private TextField yField;
	private TextField editableValueField;

	public ChangePixelValue(Label valueLabel, TextField valueField, ImageView imageView, ImageView imageResultView, TextField xField,
			TextField yField) {
		this.valueLabel = valueLabel;
		this.valueField = valueField;
		this.imageView = imageView;
		this.imageResultView = imageResultView;
		this.xField = xField;
		this.yField = yField;
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

	private void modifyImage(Image image) {
		PixelReader pixelReader = image.getPixelReader();
		int width = (int) image.getWidth();
		int height = (int) image.getHeight();
		WritableImage writableImage = new WritableImage(width, height);
		PixelWriter pixelWriter = writableImage.getPixelWriter();

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				pixelWriter.setColor(x, y, pixelReader.getColor(x, y));
			}
		}

		int x = toInt(xField.getText());
		int y = toInt(yField.getText());
		pixelWriter.setArgb(x, y, toInt(editableValueField.getText()));
		imageResultView.setImage(writableImage);
		System.out.println(toInt(editableValueField.getText()));
		System.out.println(imageResultView.getImage().getPixelReader().getArgb(x, y));
	}

	private int toInt(String text) {
		return Integer.parseInt(text);
	}

}