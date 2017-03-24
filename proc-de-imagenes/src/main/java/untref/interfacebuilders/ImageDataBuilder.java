package untref.interfacebuilders;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Arrays;

public class ImageDataBuilder {

	public VBox build(ImageView imageResultView) {
		VBox imageData = new VBox();
		imageData.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		imageData.setLayoutX(imageResultView.getLayoutX() + imageResultView.getFitWidth() + 15);
		imageData.setLayoutY(imageResultView.getLayoutY());

		Label imageDataTitle = new Label("IMAGE DATA");
		Label label1 = new Label("text field1");
		Label label2 = new Label("text field2");
		TextField textField1 = new TextFieldBuilder().withEditable(false).withAutosize().build();
		TextField textField2 = new TextFieldBuilder().withEditable(false).withAutosize().build();
		imageData.getChildren().addAll(Arrays.asList(imageDataTitle, label1, textField1, label2, textField2));
		return imageData;
	}
}