package untref.domain.noisetypes;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import untref.domain.GrayScaleFunctionsContainer;
import untref.domain.TemporalColor;
import untref.domain.utils.TemporalColorMatrixInitializer;
import untref.service.ImageArithmeticOperationService;
import untref.service.ImageArithmeticOperationServiceImpl;
import untref.service.arithmeticoperations.noise.AdditiveNoiseAdder;

import static untref.domain.utils.ImageValuesTransformer.toInt;

public class AdditiveNoise implements NoiseType {

	private AdditiveNoiseAdder additiveNoiseAdder;

	public AdditiveNoise(AdditiveNoiseAdder additiveNoiseAdder) {
		this.additiveNoiseAdder = additiveNoiseAdder;
	}

	@Override
	public TemporalColor[][] initializeNoiseMatrix(int width, int height) {
		return new TemporalColorMatrixInitializer().initialize(width, height, 0);
	}

	@Override
	public Image applyNoise(Image image, TemporalColor[][] noiseMatrix, ImageArithmeticOperationService imageArithmeticOperationService) {
		PixelReader pixelReader = image.getPixelReader();
		TemporalColor temporalImage[][] = new TemporalColor[toInt(image.getHeight())][toInt(image.getWidth())];

		for (int row = 0; row < image.getHeight(); row++) {
			for (int column = 0; column < image.getWidth(); column++) {
				Color color = pixelReader.getColor(column, row);
				temporalImage[row][column] = additiveNoiseAdder.add(color, noiseMatrix[row][column]);
			}
		}

		GrayScaleFunctionsContainer grayScaleFunctionsContainer = imageArithmeticOperationService.obtainFunctionsForExceededRGB(temporalImage);
		return imageArithmeticOperationService.parseToImageWithNoise(temporalImage, toInt(image.getWidth()), toInt(image.getHeight()),
				grayScaleFunctionsContainer);
	}
}