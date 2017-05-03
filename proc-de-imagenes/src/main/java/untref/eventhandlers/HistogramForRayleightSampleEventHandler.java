package untref.eventhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import untref.controllers.nodeutils.ParametersWindowsFactory;
import untref.service.AleatoryNumbersGeneratorService;
import untref.service.HistogramService;

import java.util.Arrays;

public class HistogramForRayleightSampleEventHandler implements EventHandler<ActionEvent> {

	private AleatoryNumbersGeneratorService aleatoryNumbersGeneratorService;
	private HistogramService histogramService;

	public HistogramForRayleightSampleEventHandler(AleatoryNumbersGeneratorService aleatoryNumbersGeneratorService,
			HistogramService histogramService) {
		this.aleatoryNumbersGeneratorService = aleatoryNumbersGeneratorService;
		this.histogramService = histogramService;
	}

	@Override
	public void handle(ActionEvent event) {
		ParametersWindowsFactory parametersWindowsFactory = new ParametersWindowsFactory();
		Label epsilonLabel = new Label("epsilon");
		TextField epsilonValue = new TextField();
		Label sampleSize = new Label("sample size");
		TextField sampleSizeValue = new TextField();
		parametersWindowsFactory.create(Arrays.asList(epsilonLabel, epsilonValue, sampleSize, sampleSizeValue), event1 -> {
			Double epsilon = Double.valueOf(epsilonValue.getText());
			Integer sampleSize1 = Integer.valueOf(sampleSizeValue.getText());
			int[] sample = aleatoryNumbersGeneratorService.generateRayleightSample(epsilon, sampleSize1);
			histogramService.sampleHistogramDraw(sample);
		});
	}
}
