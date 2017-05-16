package untref.controllers;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import untref.eventhandlers.CreateGaussianHandler;
import untref.eventhandlers.CreateMediaFilterHandler;
import untref.eventhandlers.CreateMedianWeightedHandler;
import untref.eventhandlers.CreateMedianaHandler;

public class FilterMenuController {

	public Menu createFilterMenu(ImageView imageView, ImageView imageResultView) {
		Menu filterMenu = new Menu("Filter");
		MenuItem media = new MenuItem("media");
		MenuItem mediana = new MenuItem("median");
		MenuItem gaussian = new MenuItem("gaussian");
		MenuItem medianWeighted = new MenuItem("Median weighted ");
		media.setOnAction(new CreateMediaFilterHandler(imageView, imageResultView));
		mediana.setOnAction(new CreateMedianaHandler(imageView, imageResultView));
		gaussian.setOnAction(new CreateGaussianHandler(imageView, imageResultView));
		medianWeighted.setOnAction(new CreateMedianWeightedHandler(imageView, imageResultView));
		filterMenu.getItems().addAll(media, mediana, medianWeighted, gaussian);
		return filterMenu;
	}
}