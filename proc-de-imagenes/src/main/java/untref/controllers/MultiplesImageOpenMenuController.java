package untref.controllers;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import untref.controllers.nodeutils.settertype.SetterAdjustToView;
import untref.eventhandlers.OpenImageEventHandler;
import untref.factory.FileImageChooserFactory;
import untref.service.ImageIOService;

public class MultiplesImageOpenMenuController {

	private final FileImageChooserFactory fileImageChooserFactory;
	private ImageIOService imageIOService;
	RawImage rawImage;

	public MultiplesImageOpenMenuController(FileImageChooserFactory fileImageChooserFactory, ImageIOService imageIOService, RawImage rawImage) {
		this.fileImageChooserFactory = fileImageChooserFactory;
		this.imageIOService = imageIOService;
		this.rawImage = rawImage;
	}

	public Menu createMultiplesImagesOpenMenu(ImageView imageView, ImageView imageView2) {
		Menu openMultiplesImages = new Menu("open multiples images");
		MenuItem addToFirstViewer = new MenuItem("add to first viewer");
		addToFirstViewer.setOnAction(new OpenImageEventHandler(fileImageChooserFactory.create("Open image"), imageView, imageIOService, rawImage,
				new SetterAdjustToView()));
		MenuItem addToSecondViewer = new MenuItem("add to second viewer");
		addToSecondViewer.setOnAction(new OpenImageEventHandler(fileImageChooserFactory.create("Open image"), imageView2, imageIOService, rawImage,
				new SetterAdjustToView()));
		openMultiplesImages.getItems().addAll(addToFirstViewer, addToSecondViewer);
		return openMultiplesImages;
	}
}