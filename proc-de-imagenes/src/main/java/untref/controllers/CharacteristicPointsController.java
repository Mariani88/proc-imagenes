package untref.controllers;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import untref.service.characteristicpoints.harris.HarrisService;
import untref.service.characteristicpoints.harris.HarrisServiceImpl;

public class CharacteristicPointsController {

	private final HarrisService harrisService;

	public CharacteristicPointsController() {
		harrisService = new HarrisServiceImpl();
	}

	public Menu create(ImageView imageView, ImageView imageResultView) {
		Menu characteristicPoints = new Menu("Characteristic points");
		MenuItem harris = new MenuItem("harris");



		characteristicPoints.getItems().addAll(harris);
		return characteristicPoints;
	}
}