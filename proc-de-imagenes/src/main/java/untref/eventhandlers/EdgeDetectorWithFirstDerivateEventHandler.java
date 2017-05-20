package untref.eventhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import untref.controllers.nodeutils.ImageSetter;
import untref.controllers.nodeutils.ParametersWindowsFactory;
import untref.domain.edge.detectors.EdgeDetector;
import untref.domain.edge.edgedetectionoperators.firstderivative.EdgeDetectionFirstDerivativeOperator;
import untref.service.EdgeDetectionService;

import java.util.Arrays;

public class EdgeDetectorWithFirstDerivateEventHandler implements EventHandler<ActionEvent>{

	private final ImageView imageView;
	private final ImageView imageResultView;
	private final EdgeDetector edgeDetector;

	public EdgeDetectorWithFirstDerivateEventHandler(ImageView imageView, ImageView imageResultView, EdgeDetector edgeDetector) {
		this.imageView = imageView;
		this.imageResultView = imageResultView;
		this.edgeDetector = edgeDetector;
	}

	@Override
	public void handle(ActionEvent event) {
		Label limitThreshold = new Label("limit threshold");
		TextField limitThresholdValue = new TextField();
		new ParametersWindowsFactory().create(Arrays.asList(limitThreshold, limitThresholdValue), event1 -> {
			Image imageWithEdge = this.edgeDetector.detectEdge(imageView.getImage(), Integer.valueOf(limitThresholdValue.getText()));
			ImageSetter.set(imageResultView, imageWithEdge);
		});
	}
}