package untref.eventhandlers.threshold;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import untref.controllers.nodeutils.ImageSetter;
import untref.controllers.nodeutils.ParametersWindowsFactory;
import untref.domain.ThresholdingResult;
import untref.service.ThresholdingService;

import java.util.Arrays;

public class ThresholdBasicMethodEventHandler implements EventHandler<ActionEvent> {

	private ThresholdingService thresholdingService;
	private ImageView imageView;
	private ImageView imageResultView;
	private TextField initialThresholdValue;
	private TextField deltaThresholdValue;
	private ParametersWindowsFactory parametersWindowsFactory;

	public ThresholdBasicMethodEventHandler(ThresholdingService thresholdingService, ImageView imageView, ImageView imageResultView,
			TextField initialThresholdValue, TextField deltaThresholdValue, ParametersWindowsFactory parametersWindowsFactory) {
		this.thresholdingService = thresholdingService;
		this.imageView = imageView;
		this.imageResultView = imageResultView;
		this.initialThresholdValue = initialThresholdValue;
		this.deltaThresholdValue = deltaThresholdValue;
		this.parametersWindowsFactory = parametersWindowsFactory;
	}

	@Override
	public void handle(ActionEvent event) {
		ThresholdingResult thresholdingResult = thresholdingService
				.obtainThresholdByBasicGlobalMethod(imageView.getImage(), Double.valueOf(initialThresholdValue.getText()),
						Double.valueOf(deltaThresholdValue.getText()));
		ImageSetter.set(imageResultView, thresholdingResult.getImage());
		Label iterationsTitle = new Label("Iterations");
		TextField iterationsValue = new TextField(thresholdingResult.getIterations().toString());
		iterationsValue.setEditable(false);
		Label obtainedThresholdTitle = new Label("obtained threshold");
		TextField obtainedThresholdValue = new TextField(thresholdingResult.getDetectedThreshold().toString());
		obtainedThresholdValue.setEditable(false);
		parametersWindowsFactory.create(Arrays.asList(iterationsTitle, iterationsValue, obtainedThresholdTitle, obtainedThresholdValue), event1 -> {});
	}
}