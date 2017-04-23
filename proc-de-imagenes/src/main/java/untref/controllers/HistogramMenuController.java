package untref.controllers;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import untref.eventhandlers.CreateHistogramHandler;
import untref.eventhandlers.EqualizeHandler;
import untref.eventhandlers.HistogramForExponentialSampleEventHandler;
import untref.service.AleatoryNumbersGeneratorService;
import untref.service.HistogramService;

public class HistogramMenuController {

	private AleatoryNumbersGeneratorService aleatoryNumbersGeneratorService;
	private HistogramService histogramService;

	public HistogramMenuController(AleatoryNumbersGeneratorService aleatoryNumbersGeneratorService, HistogramService histogramService) {
		this.aleatoryNumbersGeneratorService = aleatoryNumbersGeneratorService;
		this.histogramService = histogramService;
	}

	public Menu createHistogramMenu(ImageView imageView, ImageView imageResultView) {
		Menu histogramMenu = new Menu("Histogram");
		MenuItem create = new MenuItem("create");
		MenuItem equalize = new MenuItem("Equalize");
		Menu createForDistribution = new Menu("create for distribution");
		MenuItem createForExponential = new MenuItem("exponential");
		createForExponential.setOnAction(new HistogramForExponentialSampleEventHandler(aleatoryNumbersGeneratorService, histogramService));
		createForDistribution.getItems().addAll(createForExponential);
		create.setOnAction(new CreateHistogramHandler(imageView));
		equalize.setOnAction(new EqualizeHandler(imageView, imageResultView));
		histogramMenu.getItems().addAll(create, equalize, createForDistribution);
		return histogramMenu;
	}
}