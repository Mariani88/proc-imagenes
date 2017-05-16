package untref.controllers;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import untref.eventhandlers.OpenImageEventHandler;
import untref.factory.FileImageChooserFactory;
import untref.service.ImageIOService;

public class MultiplesImageOpenMenuController {

	private final FileImageChooserFactory fileImageChooserFactory;
	private ImageIOService imageIOService;

	public MultiplesImageOpenMenuController(FileImageChooserFactory fileImageChooserFactory, ImageIOService imageIOService) {
		this.fileImageChooserFactory = fileImageChooserFactory;
		this.imageIOService = imageIOService;
	}

	public Menu createMultiplesImagesOpenMenu(ImageView imageView, ImageView imageView2) {
		Menu openMultiplesImages = new Menu("open multiples images");
		MenuItem addToFirstViewer = new MenuItem("add to first viewer");
		addToFirstViewer.setOnAction(new OpenImageEventHandler(fileImageChooserFactory.create("Open image"), imageView, imageIOService));
		MenuItem addToSecondViewer = new MenuItem("add to second viewer");
		addToSecondViewer.setOnAction(new OpenImageEventHandler(fileImageChooserFactory.create("Open image"), imageView2, imageIOService));
		openMultiplesImages.getItems().addAll(addToFirstViewer, addToSecondViewer);
		return openMultiplesImages;
	}
}