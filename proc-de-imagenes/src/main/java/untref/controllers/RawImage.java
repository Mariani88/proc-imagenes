package untref.controllers;

import java.util.Arrays;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class RawImage {
	private VBox rawData;
	private TextField fieldAlto;
	private TextField fieldAncho;

	public VBox buildBoxRaw(ImageView imageView, ImageView imageResultView) {

		rawData = new VBox();
		rawData.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		rawData.setLayoutX(imageResultView.getLayoutX() + imageResultView.getFitWidth() + 15);
		rawData.setLayoutY(imageResultView.getLayoutY() + 400);

		Label ancho = new Label();
		ancho.setLayoutX(40);
		ancho.setLayoutY(10);
		ancho.setText("Width Raw");

		Label alto = new Label();
		alto.setLayoutX(40);
		alto.setLayoutY(60);
		alto.setText("Height Raw");

		fieldAlto = new TextField();
		fieldAncho = new TextField();
		fieldAlto.setMaxWidth(50);
		fieldAncho.setMaxWidth(50);
		fieldAlto.setText("256");
		fieldAncho.setText("256");

		rawData.getChildren().addAll(Arrays.asList(ancho, fieldAncho, alto, fieldAlto));
		return rawData;

	}

	public int getValueFieldAlto() {
		return Integer.parseInt(fieldAlto.getText());
	}

	public int getValueFieldAncho() {
		return Integer.parseInt(fieldAncho.getText());
	}

}
