package untref.controllers.activecotours;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import untref.controllers.OpenMenuController;
import untref.eventhandlers.activecontours.ActiveContoursEventHandler;

public class ActiveContoursController {
	private final OpenMenuController openMenuController;

	public ActiveContoursController(OpenMenuController openMenuController) {
		this.openMenuController = openMenuController;
	}

	public Menu create() {
		Menu activeContours = new Menu("active contours");
		MenuItem forOneImage = new MenuItem("open");
		forOneImage.setOnAction(new ActiveContoursEventHandler(openMenuController));
		activeContours.getItems().addAll(forOneImage);
		return activeContours;
	}

}