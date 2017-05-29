package untref.controllers;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import untref.eventhandlers.ActiveContoursEventHandler;

public class ActiveContoursController {
	private final OpenMenuController openMenuController;

	public ActiveContoursController(OpenMenuController openMenuController) {
		this.openMenuController = openMenuController;
	}

	public Menu create() {
		Menu activeContours = new Menu("active contours");
		MenuItem forOneImage = new MenuItem("for one image");
		MenuItem forVideo = new MenuItem("for video");
		forOneImage.setOnAction(new ActiveContoursEventHandler(openMenuController));
		activeContours.getItems().addAll(forOneImage, forVideo);
		return activeContours;
	}


}