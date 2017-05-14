package untref.eventhandlers;

import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import untref.controllers.ImageDataController;
import untref.service.ImageGetColorRGB;
import untref.service.ImageGetColorRGBImpl;

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
		;
		ImageGetColorRGB rgb= new ImageGetColorRGBImpl(imageView.getImage());
		 
		imageDataController.setPixelValueText(x, y,  rgb.getGrayAverage(x, y) );
	}
}
