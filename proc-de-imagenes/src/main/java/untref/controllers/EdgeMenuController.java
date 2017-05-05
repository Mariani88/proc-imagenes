package untref.controllers;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import untref.eventhandlers.CreateHighPassEdgeHandler;

public class EdgeMenuController {

	public Menu createEdgeMenu(ImageView imageView, ImageView imageResultView) {
		Menu edgeMenu = new Menu("Edge");
		MenuItem highPass = new MenuItem("high Pass");
		highPass.setOnAction(new CreateHighPassEdgeHandler(imageView, imageResultView));
		edgeMenu.getItems().addAll(highPass);
		return edgeMenu;
	}
}