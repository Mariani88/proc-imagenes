package untref.controllers;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import untref.eventhandlers.SiftEventHandler;

public class SiftController {
	
	public Menu createSift(ImageView imageView, ImageView imageResultView) {
	Menu sift = new Menu("Sift");
	MenuItem apply = new MenuItem("Apply");

	apply.setOnAction(
			new SiftEventHandler(imageView, imageResultView));
 
	sift.getItems().addAll(apply);
	return sift;
	}
}
