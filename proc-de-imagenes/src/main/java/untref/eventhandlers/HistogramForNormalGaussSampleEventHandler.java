package untref.eventhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import untref.controllers.nodeutils.ParametersWindowsFactory;
import untref.service.AleatoryNumbersGeneratorService;
import untref.service.HistogramService;

import java.util.Arrays;

public class HistogramForNormalGaussSampleEventHandler implements EventHandler<ActionEvent> {

	private AleatoryNumbersGeneratorService aleatoryNumbersGeneratorService;
	private HistogramService histogramService;

	public HistogramForNormalGaussSampleEventHandler(AleatoryNumbersGeneratorService aleatoryNumbersGeneratorService,
			HistogramService histogramService) {
		this.aleatoryNumbersGeneratorService = aleatoryNumbersGeneratorService;
		this.histogramService = histogramService;
	}

	@Override
	public void handle(ActionEvent event) {
		ParametersWindowsFactory parametersWindowsFactory = new ParametersWindowsFactory();
		Label muLabel = new Label("mu");
		TextField muValue = new TextField();
		Label sigmaLabel = new Label("sigma");
		TextField sigmaValue = new TextField();
		Label sampleSize = new Label("sample size");
		TextField sampleSizeValue = new TextField();
		parametersWindowsFactory.create(Arrays.asList(muLabel, muValue, sigmaLabel, sigmaValue, sampleSize, sampleSizeValue), event1 -> {
			Double mu = Double.valueOf(muValue.getText());
			Double sigma = Double.valueOf(sigmaValue.getText());
			Integer sampleSize1 = Integer.valueOf(sampleSizeValue.getText());
			int[] sample = aleatoryNumbersGeneratorService.generateNormalGaussSample(mu,sigma, sampleSize1);
			histogramService.sampleHistogramDraw(sample);
		});
	}
}
