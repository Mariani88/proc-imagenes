package untref.eventhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import untref.controllers.nodeutils.ImageSetter;
import untref.controllers.nodeutils.ParametersWindowsFactory;
import untref.domain.susan.ImageElementSusan;
import untref.service.boundary.BoundaryDetectionBySusanService;

import java.util.Arrays;

public class SusanEventHandler implements EventHandler<ActionEvent> {

	private final ImageView imageView;
	private final ImageView imageResultView;
	private final BoundaryDetectionBySusanService boundaryDetectionBySusanService;
	private final ImageElementSusan imageElementSusan;

	public SusanEventHandler(ImageView imageView, ImageView imageResultView, BoundaryDetectionBySusanService boundaryDetectionBySusanService,
			ImageElementSusan imageElementSusan) {
		this.imageView = imageView;
		this.imageResultView = imageResultView;
		this.boundaryDetectionBySusanService = boundaryDetectionBySusanService;
		this.imageElementSusan = imageElementSusan;
	}

	@Override
	public void handle(ActionEvent event) {
		Label threshold = new Label("threshold");
		TextField thresholdValue = new TextField("27");
		Label accumulateDelta = new Label("accumulate delta");
		TextField accumulateDeltaValue = new TextField("0");
		new ParametersWindowsFactory()
				.create(Arrays.asList(threshold, thresholdValue, accumulateDelta, accumulateDeltaValue),
						event1 -> ImageSetter.set(imageResultView, boundaryDetectionBySusanService
								.detect(imageView.getImage(), imageElementSusan, Integer.valueOf(thresholdValue.getText()),
										Double.valueOf(accumulateDeltaValue.getText()))));
	}
}