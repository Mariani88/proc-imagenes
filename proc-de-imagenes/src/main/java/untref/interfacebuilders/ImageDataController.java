package untref.interfacebuilders;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Arrays;

public class ImageDataController {

	private VBox imageData;
	private Label label1;
	private Label label2;
	private Label label3;
	private TextField xField;
	private TextField yField;
	private TextField valueField;

	public VBox build(ImageView imageResultView) {
		imageData = new VBox();
		imageData.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		imageData.setLayoutX(imageResultView.getLayoutX() + imageResultView.getFitWidth() + 15);
		imageData.setLayoutY(imageResultView.getLayoutY());

		Label imageDataTitle = new Label("IMAGE DATA");
		label1 = new Label("x");
		label2 = new Label("y");
		label3 = new Label("Value");
		xField = new TextFieldBuilder().withEditable(false).withAutosize().withMaxWidth(50).build();
		yField = new TextFieldBuilder().withEditable(false).withAutosize().withMaxWidth(50).build();
		valueField = new TextFieldBuilder().withEditable(false).withAutosize().build();
		imageData.getChildren().addAll(Arrays.asList(imageDataTitle, label1, xField, label2, yField, label3, valueField));
		return imageData;
	}

	public void setPixelValueText(int x, int y, int pixelValueText) {
		xField.setText(String.valueOf(x));
		yField.setText(String.valueOf(y));
		valueField.setText(String.valueOf(pixelValueText));
	}
}