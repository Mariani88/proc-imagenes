package untref.filters.services;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class GaussFilterService {
	int sizeMask;
	double[][] matMask;
	double divisor;

	public void setParametros(double detour) {

		MaskService maskGauss = new MaskService();
		sizeMask = maskGauss.sizeMaskGauss(detour);
		matMask = maskGauss.maskGauss(sizeMask, detour);
		divisor = addMask();

	}

	public double addMask() {
		double add = 0;
		for (int i = 0; i < sizeMask; i++) {
			for (int j = 0; j < sizeMask; j++) {
				add += matMask[i][j];
			}
		}
		return add;
	}

	public WritableImage getImageFilterGussian(Image image, int detour) {

		setParametros(detour);
		addMask();
		WritableImage imageOut = new WritableImage((int) image.getWidth(), (int) image.getHeight());
		PixelWriter pixelWriter = imageOut.getPixelWriter();

		double red = 0;
		double green = 0;
		double blue = 0;
		Color outColor = null;
		Color color = null;
		for (int i = 0; i <= image.getWidth() - sizeMask; i++) {
			for (int j = 0; j <= image.getHeight() - sizeMask; j++) {
				for (int k = 0; k < sizeMask; k++) {
					for (int m = 0; m < sizeMask; m++) {
						color = image.getPixelReader().getColor(i + k, j + m);
						red = red + (color.getRed()) * matMask[k][m];
						green = green + (color.getGreen()) * matMask[k][m];
						blue = blue + (color.getBlue()) * matMask[k][m];
					}
				}
				red = (int) ((red / divisor) * 255);
				green = (int) ((green / divisor) * 255);
				blue = (int) ((blue / divisor) * 255);
				outColor = Color.rgb((int) red, (int) green, (int) blue);
				pixelWriter.setColor(i + sizeMask / 2, j + sizeMask / 2, outColor);
				red = 0;
				green = 0;
				blue = 0;
			}
		}
		return imageOut;
	}

}
