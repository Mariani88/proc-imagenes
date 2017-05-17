package untref.difusor.service;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import untref.service.ImageGetColorRGB;
import untref.service.ImageGetColorRGBImpl;

public class ApplyBroadcastIsotropic {

	public WritableImage difussion(Image image, int numberRepeat) {
		WritableImage imageOut = new WritableImage((int) image.getWidth(), (int) image.getHeight());
		PixelWriter pixelWriter = imageOut.getPixelWriter();

		DerivativeCalculations derived = new DerivativeCalculations();
		ImageGetColorRGB imageService = new ImageGetColorRGBImpl(image);
		int[][] greyMatrix = new int[(int) image.getWidth()][(int) image.getHeight()];

		Isotropic isotropic = new Isotropic();
		LinearTrasnformation linearTrasnformation = new LinearTrasnformation();

		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				greyMatrix[i][j] = imageService.getGrayAverage(i, j);
			}
		}

		for (int h = 0; h < numberRepeat; h++) {
			for (int i = 0; i < (int) image.getWidth(); i++) {
				for (int j = 0; j < (int) image.getHeight(); j++) {

					int grey = greyMatrix[i][j];

					float northDerivative = derived.calcularDerivadaNorte(greyMatrix, i, j);
					float eastDerivative = derived.calcularDerivadaEste(greyMatrix, i, j);
					float westDerivative = derived.calcularDerivadaOeste(greyMatrix, i, j);
					float southDerivative = derived.calcularDerivadaSur(greyMatrix, i, j);

					float newGrey;

					newGrey = isotropic.valueDiffusion(grey, northDerivative, southDerivative, eastDerivative,
							westDerivative);

					greyMatrix[i][j] = (int) newGrey;
				}
			}

			greyMatrix = linearTrasnformation.aplicarTransformacionLineal(greyMatrix);

		}
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {

				int color = greyMatrix[i][j];
				Color outColor = Color.rgb(color, color, color);
				pixelWriter.setColor(i, j, outColor);

			}
		}
		return imageOut;
	}

}
