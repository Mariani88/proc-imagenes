package untref.controllers.nodeutils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageSetter {

	public static void set(ImageView imageView, Image image) {
		if (image.getHeight() < 500 & image.getWidth() < 500) {
			imageView.setFitHeight(image.getHeight());
			imageView.setFitWidth(image.getWidth());
		} else {
			imageView.setFitHeight(500);
			imageView.setFitWidth(500);
		}
		imageView.setImage(image);
	}

	public static void setWithImageSize(ImageView imageView, Image image) {
		imageView.setFitHeight(image.getHeight());
		imageView.setFitWidth(image.getWidth());
		imageView.setImage(image);
	}
}