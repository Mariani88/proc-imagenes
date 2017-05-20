package untref.eventhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import untref.controllers.nodeutils.ImageSetter;
import untref.controllers.nodeutils.ParametersWindowsFactory;
import untref.domain.edge.edgedetectionoperators.secondderivative.detectors.SlopesDetector;
import untref.service.EdgeDetectionService;

import java.util.Arrays;

public class EdgeDetectionWithMarrHildrethSlopeEvaluationEventHandler implements EventHandler<ActionEvent> {

	private final ImageView imageView;
	private final ImageView imageResultView;
	private final EdgeDetectionService edgeDetectionService;

	public EdgeDetectionWithMarrHildrethSlopeEvaluationEventHandler(ImageView imageView, ImageView imageResultView,
			EdgeDetectionService edgeDetectionService) {
		this.imageView = imageView;
		this.imageResultView = imageResultView;
		this.edgeDetectionService = edgeDetectionService;
	}

	@Override
	public void handle(ActionEvent event) {
		Label maxSlopePercent = new Label(" max slope percent");
		TextField maxSlopePercentValue = new TextField();
		new ParametersWindowsFactory().create(Arrays.asList(maxSlopePercent, maxSlopePercentValue), event1 -> {
			Double maxSlopePercent1 = Double.valueOf(maxSlopePercentValue.getText());
			Image imageWithEdges = edgeDetectionService.detectEdgeWithMarrHildreth(imageView.getImage(), new SlopesDetector(maxSlopePercent1));
			ImageSetter.set(imageResultView, imageWithEdges);
		});
	}

}
