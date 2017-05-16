package untref.controllers;

import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import untref.controllers.nodeutils.ParametersWindowsFactory;
import untref.eventhandlers.threshold.OtsuMethodEventHandler;
import untref.eventhandlers.threshold.ThresholdBasicMethodEventHandler;
import untref.service.ImageStatisticService;
import untref.service.ImageStatisticServiceImpl;
import untref.service.ThresholdingService;
import untref.service.ThresholdingServiceImpl;

import java.util.Arrays;

public class ThresholdingMenuController {

	private final ThresholdingService thresholdingService;
	private ParametersWindowsFactory parametersWindowsFactory;

	public ThresholdingMenuController() {
		thresholdingService = new ThresholdingServiceImpl(new ImageStatisticServiceImpl());
		parametersWindowsFactory = new ParametersWindowsFactory();
	}

	public Menu createThresholdingMenu(ImageView imageView, ImageView imageResultView) {
		Label initialThreshold = new Label(" initial threshold");
		TextField initialThresholdValue = new TextField();
		Label deltaThreshold = new Label("delta threshold");
		TextField deltaThresholdValue = new TextField();
		Menu thresholdingMenu = new Menu("Thresholding");
		MenuItem basicGlobalThresholding = new MenuItem("Basic Global Thresholding");
		MenuItem thresholdAutomaticEstimation = new MenuItem("Threshold automatic estimation");
		basicGlobalThresholding.setOnAction(event -> parametersWindowsFactory
				.create(Arrays.asList(initialThreshold, initialThresholdValue, deltaThreshold, deltaThresholdValue),
						new ThresholdBasicMethodEventHandler(thresholdingService, imageView, imageResultView, initialThresholdValue,
								deltaThresholdValue, parametersWindowsFactory)));
		thresholdAutomaticEstimation
				.setOnAction(new OtsuMethodEventHandler(parametersWindowsFactory, imageResultView, thresholdingService, imageView));
		thresholdingMenu.getItems().addAll(basicGlobalThresholding, thresholdAutomaticEstimation);
		return thresholdingMenu;
	}
}