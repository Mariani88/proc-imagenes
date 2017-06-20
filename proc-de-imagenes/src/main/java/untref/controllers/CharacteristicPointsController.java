package untref.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import untref.controllers.nodeutils.ImageSetter;
import untref.controllers.nodeutils.ParametersWindowsFactory;
import untref.service.characteristicpoints.harris.HarrisService;
import untref.service.characteristicpoints.harris.HarrisServiceImpl;

import java.util.Arrays;

public class CharacteristicPointsController {

	private final HarrisService harrisService;
	private ParametersWindowsFactory parametersWindowsFactory;

	public CharacteristicPointsController() {
		harrisService = new HarrisServiceImpl();
		parametersWindowsFactory = new ParametersWindowsFactory();
	}

	public Menu create(ImageView imageView, ImageView imageResultView) {
		Menu characteristicPoints = new Menu("Characteristic points");
		MenuItem harris = new MenuItem("harris");
		harris.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Label maximumPercent = new Label("maximum percent");
				TextField maximumPercentValue = new TextField();
				parametersWindowsFactory.create(Arrays.asList(maximumPercent, maximumPercentValue), event1 -> {
					Image imageWithCharacteristicPoints = harrisService
							.detectCharacteristicPoints(imageView.getImage(), Double.valueOf(maximumPercentValue.getText()));
					ImageSetter.set(imageResultView, imageWithCharacteristicPoints);
				});
			}
		});

		characteristicPoints.getItems().addAll(harris);
		return characteristicPoints;
	}
}