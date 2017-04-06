package untref.controllers;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import untref.eventhandlers.ChangePixelValueEventHandler;
import untref.interfacebuilders.TextFieldBuilder;
import untref.service.ImageEditionService;
import untref.service.ImageEditionServiceImpl;

import java.util.Arrays;

public class ImageDataController {

	private VBox imageData;
	private Label xLabel;
	private Label yLabel;
	private Label valueLabel;
	private TextField xField;
	private TextField yField;
	private TextField valueField;
	private Button changePixelValueButton;
	private ImageEditionService imageEditionService;

	public ImageDataController() {
		imageEditionService = new ImageEditionServiceImpl();
	}

	public VBox build(ImageView imageView, ImageView imageResultView) {
		imageData = new VBox();
		imageData.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		imageData.setLayoutX(imageResultView.getLayoutX() + imageResultView.getFitWidth() + 15);
		imageData.setLayoutY(imageResultView.getLayoutY());

		Label imageDataTitle = new Label("IMAGE DATA");
		xLabel = new Label("x");
		yLabel = new Label("y");
		valueLabel = new Label("Value");
		xField = new TextFieldBuilder().withEditable(false).withAutosize().withMaxWidth(50).build();
		yField = new TextFieldBuilder().withEditable(false).withAutosize().withMaxWidth(50).build();
		valueField = new TextFieldBuilder().withEditable(false).withAutosize().build();
		changePixelValueButton = new Button("Change value");
		changePixelValueButton.setOnAction(
				new ChangePixelValueEventHandler(valueLabel, valueField, imageView, imageResultView, xField, yField, imageEditionService));
		imageData.getChildren().addAll(Arrays.asList(imageDataTitle, xLabel, xField, yLabel, yField, valueLabel, valueField,
				changePixelValueButton));
		return imageData;
	}

	public void setPixelValueText(int x, int y, int pixelValueText) {
		xField.setText(String.valueOf(x));
		yField.setText(String.valueOf(y));
		valueField.setText(String.valueOf(pixelValueText));
	}
}