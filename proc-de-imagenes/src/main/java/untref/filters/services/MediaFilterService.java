package untref.filters.services;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class MediaFilterService {

	public Image getImageMedia(Image image, int sizeMask) {
		int[][] matrizMask = null;
		int numbreDivider = (int) Math.pow(sizeMask, 2);
		MaskService maskMedia = new MaskService();
		matrizMask = maskMedia.mediaMask(sizeMask);

		int red = 0;
		int green = 0;
		int blue = 0;
		Color color = null;

		WritableImage imageOut = new WritableImage((int) image.getWidth(), (int) image.getHeight());

		for (int i = 0; i <= image.getWidth() - sizeMask; i++) {

			for (int j = 0; j <= image.getHeight() - sizeMask; j++) {

				red = 0;
				green = 0;
				blue = 0;
				for (int k = 0; k < sizeMask; k++) {
					for (int m = 0; m < sizeMask; m++) {
						color = image.getPixelReader().getColor(i + k, j + m);
						red = red + (int) (color.getRed()*255) * matrizMask[k][m];
						green = green + (int) (color.getGreen()*255) * matrizMask[k][m];
						blue = blue +  (int)(color.getBlue()*255) * matrizMask[k][m];
					}
				}
				red =   (red / numbreDivider);
				green =  (green / numbreDivider);
				blue =  (blue / numbreDivider);

				 Color colorRGB = Color.rgb(red, green, blue);
				//Color colorRGB = Color.rgb(1,1,1);

				PixelWriter pixelWriter = imageOut.getPixelWriter();

				pixelWriter.setColor(i + sizeMask / 2, j + sizeMask / 2, colorRGB);

			}
		}
		return imageOut;
	}

}
