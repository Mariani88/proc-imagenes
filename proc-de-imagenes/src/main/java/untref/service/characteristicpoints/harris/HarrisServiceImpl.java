package untref.service.characteristicpoints.harris;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import untref.domain.edge.edgedetectionoperators.firstderivative.PrewithUtils;
import untref.filters.services.MaskService;
import untref.service.ImageDerivativeService;
import untref.service.ImageDerivativeServiceImpl;
import untref.service.MaskApplicationService;
import untref.service.MaskApplicationServiceImpl;

import static untref.domain.edge.edgedetectionoperators.firstderivative.PrewithUtils.PREWITH_OPERATOR_Fx;
import static untref.domain.edge.edgedetectionoperators.firstderivative.PrewithUtils.PREWITH_OPERATOR_Fy;
import static untref.domain.utils.ImageValuesTransformer.toGrayScale;
import static untref.domain.utils.ImageValuesTransformer.toInt;

public class HarrisServiceImpl implements HarrisService {

	private static final double K = 0.04;
	private final ImageDerivativeService imageDerivativeService;
	private final double[][] maskGauss;
	private final MaskApplicationService maskApplicationService;
	private static final int OFFSET_I = 3;
	private static final int OFFSET_J = 3;

	public HarrisServiceImpl() {
		maskApplicationService = new MaskApplicationServiceImpl();
		imageDerivativeService = new ImageDerivativeServiceImpl(maskApplicationService);
		maskGauss = new MaskService().maskGauss(7, 2);
	}

	@Override
	public Image detectCharacteristicPoints(Image image, double percent) {
		PixelReader pixelReader = image.getPixelReader();
		int width = toInt(image.getWidth());
		int height = toInt(image.getHeight());
		int imageFx[][] = new int[height][width];
		int imageFy[][] = new int[height][width];
		calculateDerivatives(image, pixelReader, width, height, imageFx, imageFy);
		double imageFxQuadrate[][] = multipleMatrixes(imageFx, imageFx);
		double imageFyQuadrate[][] = multipleMatrixes(imageFy, imageFy);
		double imageFxy[][] = multipleMatrixes(imageFx, imageFy);
		double smoothedFxQuadrate[][] = applySmooth(imageFxQuadrate);
		double smoothedFyQuadrate[][] = applySmooth(imageFyQuadrate);
		double smoothedFxy[][] = applySmooth(imageFxy);
		double[][] cim = calculateCim(width, height, smoothedFxQuadrate, smoothedFyQuadrate, smoothedFxy);
		return markCharacteristicPoints(image, cim, percent);
	}

	private Image markCharacteristicPoints(Image image, double[][] cim, double percent) {
		double max = findMaxValue(cim);
		int width = toInt(image.getWidth());
		int height = toInt(image.getHeight());
		WritableImage writableImage = new WritableImage(width, height);
		PixelReader pixelReader = image.getPixelReader();
		PixelWriter pixelWriter = writableImage.getPixelWriter();

		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				if (cim[row][column] > (max * percent / 100)) {
					pixelWriter.setColor(column, row, Color.RED);
				} else {
					pixelWriter.setColor(column, row, pixelReader.getColor(column, row));
				}
			}
		}

		return writableImage;
	}

	private double findMaxValue(double[][] cim) {
		double max = Double.MIN_VALUE;

		for (int row = 0; row < cim.length; row++) {
			for (int column = 0; column < cim[0].length; column++) {
				max = Math.max(max, cim[row][column]);
			}
		}

		return max;
	}

	private double[][] calculateCim(int width, int height, double[][] smoothedFxQuadrate, double[][] smoothedFyQuadrate, double[][] smoothedFxy) {
		double cim[][] = new double[height][width];

		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				double fxQuadrate = smoothedFxQuadrate[row][column];
				double fyQuadrate = smoothedFyQuadrate[row][column];
				double fxy = smoothedFxy[row][column];
				cim[row][column] = (fxQuadrate * fyQuadrate - fxy) - K * Math.pow(fxQuadrate + fyQuadrate, 2);
			}
		}
		return cim;
	}

	private double[][] applySmooth(double[][] quadrateDerivative) {
		double[][] smoothed = new double[quadrateDerivative.length][quadrateDerivative[0].length];

		for (int row = 0; row < quadrateDerivative.length; row++) {
			for (int column = 0; column < quadrateDerivative[0].length; column++) {
				smoothed[row][column] = maskApplicationService.applyMask(quadrateDerivative, maskGauss, OFFSET_I, OFFSET_J, row, column);
			}
		}

		return smoothed;
	}

	private double[][] multipleMatrixes(int[][] matrix1, int[][] matrix2) {
		double product[][] = new double[matrix1.length][matrix1[0].length];

		for (int row = 0; row < matrix1.length; row++) {
			for (int column = 0; column < matrix1[0].length; column++) {
				product[row][column] = matrix1[row][column] * matrix2[row][column];
			}
		}

		return product;
	}

	private void calculateDerivatives(Image image, PixelReader pixelReader, int width, int height, int[][] imageFx, int[][] imageFy) {
		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				imageFx[row][column] = toGrayScale(imageDerivativeService
						.calculateDerivative(image, row, column, pixelReader, PREWITH_OPERATOR_Fx, PrewithUtils.OFFSET_I, PrewithUtils.OFFSET_J));
				imageFy[row][column] = toGrayScale(imageDerivativeService
						.calculateDerivative(image, row, column, pixelReader, PREWITH_OPERATOR_Fy, PrewithUtils.OFFSET_I, PrewithUtils.OFFSET_J));
			}
		}
	}
}