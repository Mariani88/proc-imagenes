package untref.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import untref.eventhandlers.CreateThresholdHandler;
import untref.interfacebuilders.SliderBuilder;

import java.util.Arrays;

public class ThresholdController {

	private VBox sliderData;

	public ThresholdController() {

	}

	public VBox build(ImageView imageView, ImageView imageResultView) {
		sliderData = new VBox();
		sliderData.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		sliderData.setLayoutX(imageResultView.getLayoutX() + imageResultView.getFitWidth() + 15);
		sliderData.setLayoutY(imageResultView.getLayoutY() + 200);

		Slider sliderT = null;
		Label titleLabel = new Label("Threshold Level:");
		Label levelValue = new Label("0");

		SliderBuilder slider = new SliderBuilder(0, 1, 0.5);

		sliderT = slider.getSlider();
		sliderT.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {

				String value = String.format("%.0f", new_val);
				levelValue.setText(value);
				CreateThresholdHandler threshold = new CreateThresholdHandler(imageView, imageResultView, Integer.parseInt(value));
				threshold.handle();
			}
		});

		sliderData.getChildren().addAll(Arrays.asList(titleLabel, levelValue, sliderT));
		return sliderData;
	}

}
