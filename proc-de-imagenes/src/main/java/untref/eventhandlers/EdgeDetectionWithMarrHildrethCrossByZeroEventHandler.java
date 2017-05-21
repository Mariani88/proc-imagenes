package untref.eventhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import untref.controllers.nodeutils.ImageSetter;
import untref.controllers.nodeutils.ParametersWindowsFactory;
import untref.domain.edge.edgedetectionoperators.secondderivative.detectors.CrossByZeroDetector;
import untref.service.EdgeDetectionService;

import java.util.Arrays;

public class EdgeDetectionWithMarrHildrethCrossByZeroEventHandler implements EventHandler<ActionEvent> {

	private final ImageView imageView;
	private final ImageView imageResultView;
	private final EdgeDetectionService edgeDetectionService;

	public EdgeDetectionWithMarrHildrethCrossByZeroEventHandler(ImageView imageView, ImageView imageResultView,
			EdgeDetectionService edgeDetectionService) {
		this.imageView = imageView;
		this.imageResultView = imageResultView;
		this.edgeDetectionService = edgeDetectionService;
	}

	@Override
	public void handle(ActionEvent event) {
		Label sigma = new Label("sigma");
		TextField sigmaValue = new TextField();
		new ParametersWindowsFactory().create(Arrays.asList(sigma, sigmaValue), event1 -> {
			Image imageWithEdges = edgeDetectionService
					.detectEdgeWithMarrHildreth(imageView.getImage(), new CrossByZeroDetector(), Double.valueOf(sigmaValue.getText()));
			ImageSetter.set(imageResultView, imageWithEdges);
		});
	}
}