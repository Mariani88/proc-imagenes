package untref.service;


import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class CreateThresholdService {

	public Image getImageThreshold(Image image, int valueThreshold) {
ImageGetColorRGB colorRgb= new ImageGetColorRGBImpl(image);
		WritableImage imageOut = new WritableImage((int) image.getWidth(), (int) image.getHeight());
		PixelWriter pixelWriter = imageOut.getPixelWriter();

		
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				if (colorRgb.getValueRgb(i, j) < valueThreshold) {
					pixelWriter.setColor(i, j, Color.BLACK);
				} else {
					pixelWriter.setColor(i, j, Color.WHITE);
				}
			}
		}
		return imageOut;
	}

}
