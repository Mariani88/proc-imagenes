package untref.domain.noisetypes;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import untref.domain.GrayScaleFunctionsContainer;
import untref.domain.TemporalColor;
import untref.domain.utils.TemporalColorMatrixInitializer;
import untref.service.ImageArithmeticOperationService;
import untref.service.ImageArithmeticOperationServiceImpl;
import untref.service.arithmeticoperations.noise.MultiplicativeNoiseAdder;

import static untref.domain.utils.ImageValuesTransformer.toInt;

public class MultiplicativeNoise implements NoiseType {

	private static final int NEUTRAL_VALUE = 1;
	private MultiplicativeNoiseAdder multiplicativeNoiseAdder;

	@Override
	public TemporalColor[][] initializeNoiseMatrix(int width, int height) {
		return new TemporalColorMatrixInitializer().initialize(width, height, NEUTRAL_VALUE);
	}

	@Override
	public Image applyNoise(Image image, TemporalColor[][] noiseMatrix, ImageArithmeticOperationService imageArithmeticOperationService) {
		PixelReader pixelReader = image.getPixelReader();
		TemporalColor temporalImage[][] = new TemporalColor[toInt(image.getHeight())][toInt(image.getWidth())];

		for (int row = 0; row < image.getHeight(); row++) {
			for (int column = 0; column < image.getWidth(); column++) {
				Color color = pixelReader.getColor(column, row);
				multiplicativeNoiseAdder = new MultiplicativeNoiseAdder();
				temporalImage[row][column] = multiplicativeNoiseAdder.add(color, noiseMatrix[row][column]);
			}
		}

		GrayScaleFunctionsContainer grayScaleFunctionsContainer = imageArithmeticOperationService.obtainFunctionsForExceededRGB(temporalImage);
		return imageArithmeticOperationService
				.parseToImageWithNoise(temporalImage, toInt(image.getWidth()), toInt(image.getHeight()), grayScaleFunctionsContainer);
	}
}