package untref.eventhandlers.threshold;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import untref.controllers.nodeutils.ImageSetter;
import untref.controllers.nodeutils.ParametersWindowsFactory;
import untref.domain.OtsuMethodResult;
import untref.service.ThresholdingService;

import java.util.Arrays;

public class OtsuMethodEventHandler implements EventHandler<ActionEvent> {

	private ParametersWindowsFactory parametersWindowsFactory;
	private ImageView imageResultView;
	private ThresholdingService thresholdingService;
	private ImageView imageView;

	public OtsuMethodEventHandler(ParametersWindowsFactory parametersWindowsFactory, ImageView imageResultView,
			ThresholdingService thresholdingService, ImageView imageView) {
		this.parametersWindowsFactory = parametersWindowsFactory;
		this.imageResultView = imageResultView;
		this.thresholdingService = thresholdingService;
		this.imageView = imageView;
	}

	@Override
	public void handle(ActionEvent event) {
		Label thresholdTitle = new Label("threshold");
		TextField thresholdValue = new TextField();
		thresholdValue.setEditable(false);
		OtsuMethodResult otsuMethodResult = thresholdingService.obtainThresholdByOtsuMethod(imageView.getImage());
		ImageSetter.set(imageResultView, otsuMethodResult.getImage());
		thresholdValue.setText(otsuMethodResult.getThreshold().toString());
		parametersWindowsFactory.create(Arrays.asList(thresholdTitle, thresholdValue), event1 -> {});
	}
}