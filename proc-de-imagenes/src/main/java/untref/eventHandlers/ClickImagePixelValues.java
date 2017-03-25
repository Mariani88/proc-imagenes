package untref.eventHandlers;

import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import untref.controllers.ImageDataController;

public class ClickImagePixelValues implements EventHandler<MouseEvent> {

	private ImageView imageView;
	private ImageDataController imageDataController;

	public ClickImagePixelValues(ImageView imageView, ImageDataController imageDataController) {
		this.imageView = imageView;
		this.imageDataController = imageDataController;
	}

	public void handle(MouseEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		int pixelValue = imageView.getImage().getPixelReader().getArgb(x, y);
		imageDataController.setPixelValueText(x, y, pixelValue);
	}
}
