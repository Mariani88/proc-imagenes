package untref.edge.service.canny;

import javafx.scene.image.Image;
import untref.filters.services.GaussFilterService;
import untref.service.ImageGetColorRGB;
import untref.service.ImageGetColorRGBImpl;

public class Canny {
	int[][] maskMatrixY = { { -1, -2, -1 }, { 0, 0, 0 }, { 1, 2, 1 } };
	int[][] maskMatrixX = { { -1, 0, 1 }, { -2, 0, 2 }, { -1, 0, 1 } };

	public Image startCanny(Image image, int sigma, int lowThresholdValue, int highThresholdValue) {
		int[][] edge = new int[(int) image.getWidth()][(int) image.getHeight()];
		edge = applyFiltersImage(image, sigma, lowThresholdValue, highThresholdValue);
		CreateImage createImageOut=new CreateImage();
		return createImageOut.createImageOut(edge, (int) image.getWidth(), (int) image.getHeight());
	}

	private int[][] applyFiltersImage(Image image, int sigma, int lowThresholdValue, int highThresholdValue) {
		int[][] edgeMatrix = new int[(int) image.getWidth()][(int) image.getHeight()];
		int[][] edgeMagnitudeMatrix = new int[(int) image.getWidth()][(int) image.getHeight()];
		int[][] angleMatrix = new int[(int) image.getWidth()][(int) image.getHeight()];
		int[][] maximumEdge = new int[(int) image.getWidth()][(int) image.getHeight()];

		SuppressionNotMaximum notMaximum = new SuppressionNotMaximum();
		ThresholdHisteresis histeresis = new ThresholdHisteresis();
		Image imageout;
		imageout = softenImage(image, sigma);
		edgeMagnitudeMatrix = calculateMagnitudeEdge(imageout);
		angleMatrix = calculateAngle(imageout);
		maximumEdge = notMaximum.sumprimirNoMaximos(edgeMagnitudeMatrix, angleMatrix, (int) image.getWidth(),
				(int) image.getHeight());
		edgeMatrix = histeresis.threshold(maximumEdge, (int) image.getWidth(), (int) image.getHeight(),
				lowThresholdValue, highThresholdValue);
		return edgeMatrix;
	}

	private Image softenImage(Image image, int sigma) {
		GaussFilterService filterGauss = new GaussFilterService();
		return filterGauss.getImageFilterGussian(image, sigma);
	}

	private int[][] calculateMagnitudeEdge(Image gaussianImage) {

		int[][] matrixX = new int[(int) gaussianImage.getWidth()][(int) gaussianImage.getHeight()];
		int[][] matrixY = new int[(int) gaussianImage.getWidth()][(int) gaussianImage.getHeight()];
		int[][] magnitude = new int[(int) gaussianImage.getWidth()][(int) gaussianImage.getHeight()];
		matrixX = this.getMatrixGrey(gaussianImage, (int) gaussianImage.getWidth(), (int) gaussianImage.getHeight(),
				maskMatrixX);
		matrixY = this.getMatrixGrey(gaussianImage, (int) gaussianImage.getWidth(), (int) gaussianImage.getHeight(),
				maskMatrixY);
		magnitude = this.obtenerMatrizPyS(matrixX, matrixY, (int) gaussianImage.getWidth(),
				(int) gaussianImage.getHeight());
		return magnitude;
	}

	private int[][] calculateAngle(Image gaussianImage) {
		int[][] angles = new int[(int) gaussianImage.getWidth()][(int) gaussianImage.getHeight()];

		angles = calculateMatrixAngle(
				this.getMatrixGrey(gaussianImage, (int) gaussianImage.getWidth(), (int) gaussianImage.getHeight(),
						this.maskMatrixX),
				this.getMatrixGrey(gaussianImage, (int) gaussianImage.getWidth(), (int) gaussianImage.getHeight(),
						this.maskMatrixY),
				(int) gaussianImage.getWidth(), (int) gaussianImage.getHeight());
		return angles;
	}

	private int[][] calculateMatrixAngle(int[][] gx, int[][] gy, int width, int height) {
		int[][] angleEdge = new int[width][height];
		double angle;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (gx[i][j] > 0) {
					angle = Math.toDegrees(Math.atan(gy[i][j] / gx[i][j])) + 90;
					angleEdge[i][j] = angleClassify(angle);
				} else {
					angleEdge[i][j] = 0;
				}
			}
		}
		return angleEdge;
	}

	private int angleClassify(double angle) {
		int angleCorrected = 0;
		if (angle > 0 && angle <= 22.5) {
			angleCorrected = 0;
		}
		if (angle > 22.5 && angle <= 67.5) {
			angleCorrected = 45;
		}
		if (angle > 67.5 && angle <= 112.5) {
			angleCorrected = 90;
		}
		if (angle > 112.5 && angle <= 157.5) {
			angleCorrected = 135;
		}
		if (angle > 157.5 && angle <= 180) {
			angleCorrected = 0;
		}
		return angleCorrected;
	}

	

	public int[][] getMatrixGrey(Image image, int width, int height, int[][] matrizMascara) {
		ImageGetColorRGB imageService = new ImageGetColorRGBImpl(image);
		int[][] matriz = new int[width][height];
		int gris = 0;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				matriz[i][j] = 0;
			}
		}
		for (int i = 0; i <= width - 3; i++) {
			for (int j = 0; j <= height - 3; j++) {
				for (int k = 0; k < 3; k++) {
					for (int m = 0; m < 3; m++) {
						gris = gris + (imageService.getGrayAverage(i + k, j + m)) * matrizMascara[k][m];
					}
				}
				matriz[i + 1][j + 1] = gris;
				gris = 0;
			}
		}
		return matriz;
	}

	public int[][] obtenerMatrizPyS(int[][] matrizX, int[][] matrizY, int width, int height) {
		int[][] result = new int[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				result[i][j] = (int) Math.sqrt(Math.pow(matrizX[i][j], 2) + Math.pow(matrizY[i][j], 2));
			}
		}
		return result;
	}

}
