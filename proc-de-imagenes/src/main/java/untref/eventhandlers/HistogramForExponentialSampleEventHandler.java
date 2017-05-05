package untref.eventhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import untref.controllers.nodeutils.ParametersWindowsFactory;
import untref.service.AleatoryNumbersGeneratorService;
import untref.service.HistogramService;

import untref.service.HistogramServiceImpl;

import java.util.Arrays;

public class HistogramForExponentialSampleEventHandler implements EventHandler<ActionEvent> {
	private AleatoryNumbersGeneratorService aleatoryNumbersGeneratorService;
	private HistogramService histogramService;

	public HistogramForExponentialSampleEventHandler(AleatoryNumbersGeneratorService aleatoryNumbersGeneratorService,
			HistogramService histogramService) {
		this.aleatoryNumbersGeneratorService = aleatoryNumbersGeneratorService;
		this.histogramService = histogramService;
	}

	@Override
	public void handle(ActionEvent event) {
		ParametersWindowsFactory parametersWindowsFactory = new ParametersWindowsFactory();
		Label lambda = new Label("lambda");
		TextField lambdaValue = new TextField();
		Label sampleSize = new Label("sample size");
		TextField sampleSizeValue = new TextField();
		parametersWindowsFactory.create(Arrays.asList(lambda, lambdaValue, sampleSize, sampleSizeValue),
				getActionEventEventHandler(lambdaValue, sampleSizeValue));
	}

	private EventHandler<ActionEvent> getActionEventEventHandler(TextField lambdaValue, TextField sampleSizeValue) {
		return event1 -> {
			Double lambda1 = Double.valueOf(lambdaValue.getText());
			Integer sampleSize1 = Integer.valueOf(sampleSizeValue.getText());
			int[] sample = aleatoryNumbersGeneratorService.generateExponentialSample(lambda1, sampleSize1);
			histogramService.sampleHistogramDraw(sample);
		};
	}
}