package untref.controllers;

import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import untref.controllers.nodeutils.settertype.SetterType;
import untref.eventhandlers.OpenImageEventHandler;
import untref.factory.FileImageChooserFactory;
import untref.service.ImageIOService;

public class OpenMenuController {

	private FileImageChooserFactory fileImageChooserFactory;
	private ImageIOService imageIOService;

	public OpenMenuController(FileImageChooserFactory fileImageChooserFactory, ImageIOService imageIOService) {
		this.fileImageChooserFactory = fileImageChooserFactory;
		this.imageIOService = imageIOService;
	}

	public MenuItem createOpenMenuItem(ImageView imageView, RawImage rawImage, SetterType setterType) {
		MenuItem fileMenuItem = new MenuItem("open...");
		final FileChooser fileChooser = fileImageChooserFactory.create("open image");
		setOpenEvent(imageView, fileMenuItem, fileChooser, setterType, rawImage);
		return fileMenuItem;
	}

	private void setOpenEvent(final ImageView imageView, MenuItem fileMenuItem, final FileChooser fileChooser, SetterType setterType,
			RawImage rawImage) {
		fileMenuItem.setOnAction(new OpenImageEventHandler(fileChooser, imageView, imageIOService, rawImage, setterType));
	}
}