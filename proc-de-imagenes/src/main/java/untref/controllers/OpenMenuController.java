package untref.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import untref.controllers.nodeutils.settertype.SetterType;
import untref.eventhandlers.OpenImageEventHandler;
import untref.factory.FileImageChooserFactory;
import untref.service.ImageIOService;

import java.util.function.Consumer;

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

	public MenuItem createOpenMenuItemWithSpecificEvent(ImageView imageView, RawImage rawImage, SetterType setterType, Consumer<Object> consumer){
		MenuItem fileMenuItem = new MenuItem("open...");
		final FileChooser fileChooser = fileImageChooserFactory.create("open image");
		setOpenEvent(imageView, fileMenuItem, fileChooser, setterType, rawImage, consumer);
		return fileMenuItem;
	}

	private void setOpenEvent(ImageView imageView, MenuItem fileMenuItem, FileChooser fileChooser, SetterType setterType, RawImage rawImage,
			Consumer<Object> consumer) {
		fileMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				new OpenImageEventHandler(fileChooser, imageView, imageIOService, rawImage, setterType).handle(event);
				consumer.accept(null);
			}
		});

	}

	private void setOpenEvent(final ImageView imageView, MenuItem fileMenuItem, final FileChooser fileChooser, SetterType setterType,
			RawImage rawImage) {
		fileMenuItem.setOnAction(new OpenImageEventHandler(fileChooser, imageView, imageIOService, rawImage, setterType));
	}
}