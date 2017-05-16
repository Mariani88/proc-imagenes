package untref.eventhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import untref.controllers.nodeutils.ImageSetter;
import untref.controllers.nodeutils.ParametersWindowsFactory;
import untref.domain.edgedetectionoperators.firstderivative.EdgeDetectionFirstDerivativeOperator;
import untref.service.EdgeDetectionService;

import java.util.Arrays;

public class EdgeDetectorWithFirstDerivateEventHandler implements EventHandler<ActionEvent>{

	private final ImageView imageView;
	private final ImageView imageResultView;
	private final EdgeDetectionService edgeDetectionService;
	private final EdgeDetectionFirstDerivativeOperator edgeDetectionOperator;

	public EdgeDetectorWithFirstDerivateEventHandler(ImageView imageView, ImageView imageResultView, EdgeDetectionService edgeDetectionService,
			EdgeDetectionFirstDerivativeOperator edgeDetectionFirstDerivativeOperator) {
		this.imageView = imageView;
		this.imageResultView = imageResultView;
		this.edgeDetectionService = edgeDetectionService;
		this.edgeDetectionOperator = edgeDetectionFirstDerivativeOperator;
	}

	@Override
	public void handle(ActionEvent event) {
		Label limitThreshold = new Label("limit threshold");
		TextField limitThresholdValue = new TextField();
		new ParametersWindowsFactory().create(Arrays.asList(limitThreshold, limitThresholdValue), event1 -> {
			Image imageWithEdge = edgeDetectionService
					.detectEdgeWithFirstDerivative(imageView.getImage(), edgeDetectionOperator, Integer.valueOf(limitThresholdValue.getText()));
			ImageSetter.set(imageResultView, imageWithEdge);
		});
	}
}