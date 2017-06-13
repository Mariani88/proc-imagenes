package untref.controllers;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import untref.eventhandlers.CircleEventHandler;
import untref.eventhandlers.StrainghtEventHandler;

public class HoughMenuController {

	public Menu createHoughMenu(ImageView imageView, ImageView imageResultView) {
		Menu hoghTransform = new Menu("Hough Transform");
		MenuItem straight = new MenuItem("Straight");
		MenuItem circle = new MenuItem("Circle");
		straight.setOnAction(
				new StrainghtEventHandler(imageView, imageResultView));
		circle.setOnAction(new CircleEventHandler(imageView,imageResultView));
		hoghTransform.getItems().addAll(straight, circle);
		return hoghTransform;
	}


}
