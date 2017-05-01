package untref.service;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import untref.domain.GrayScaleFunctionsContainer;
import untref.domain.TemporalColor;
import untref.service.arithmeticoperations.ArithmeticOperationBetweenImages;
import untref.service.arithmeticoperations.ArithmeticOperationBetweenImagesMultiply;
import untref.service.arithmeticoperations.ArithmeticOperationBetweenImagesPlus;
import untref.service.arithmeticoperations.ArithmeticOperationBetweenImagesSubtract;
import untref.service.evaluators.ImagePositionEvaluator;
import untref.service.functions.DynamicRangeFunction;
import untref.service.functions.TemporalGrayScaleToGrayScaleFunction;

import java.util.function.Supplier;

public class ImageArithmeticOperationServiceImpl implements ImageArithmeticOperationService {

	private static final int LIMIT_SCALE = 255;
	private final ImagePositionEvaluator imagePositionEvaluator;
	private final ArithmeticOperationBetweenImagesSubtract arithmeticOperationSubtract;
	private final ArithmeticOperationBetweenImagesMultiply arithmeticOperationMultiply;
	private ArithmeticOperationBetweenImages arithmeticOperationBetweenImagesPlus;

	public ImageArithmeticOperationServiceImpl() {
		this.arithmeticOperationBetweenImagesPlus = new ArithmeticOperationBetweenImagesPlus();
		this.imagePositionEvaluator = new ImagePositionEvaluator();
		this.arithmeticOperationSubtract = new ArithmeticOperationBetweenImagesSubtract();
		this.arithmeticOperationMultiply = new ArithmeticOperationBetweenImagesMultiply();
	}

	@Override
	public Image plusImages(Image image, Image image2) {
		return applyArithmeticOperation(image, image2, arithmeticOperationBetweenImagesPlus);
	}

	@Override
	public Image subtractImages(Image image, Image image2) {
		return applyArithmeticOperation(image, image2, arithmeticOperationSubtract);
	}

	@Override
	public Image multiplyImages(Image image, Image image2) {
		return applyArithmeticOperation(image, image2, arithmeticOperationMultiply);
	}

	@Override
	public Image multiplyImageByScalar(double scalar, Image image) {
		PixelReader pixelReader = image.getPixelReader();
		TemporalColor temporalImageData[][] = new TemporalColor[(int) image.getHeight()][(int) image.getWidth()];

		for (int row = 0; row < image.getHeight(); row++) {
			for (int column = 0; column < image.getWidth(); column++) {
				Color color = pixelReader.getColor(column, row);
				int blue = (int) (color.getBlue() * LIMIT_SCALE * scalar);
				int green = (int) (color.getGreen() * LIMIT_SCALE * scalar);
				int red = (int) (color.getRed() * LIMIT_SCALE * scalar);
				TemporalColor temporalColor = new TemporalColor(red, green, blue);
				temporalImageData[row][column] = temporalColor;
			}
		}

		GrayScaleFunctionsContainer grayScaleFunctionsContainer = obtainDynamicRangeFunctions(temporalImageData);
		return parseToImage(temporalImageData, (int) image.getWidth(), (int) image.getHeight(), grayScaleFunctionsContainer);
	}

	private GrayScaleFunctionsContainer obtainDynamicRangeFunctions(TemporalColor[][] temporalImageData) {
		int maxRed = Integer.MIN_VALUE;
		int maxGreen = Integer.MIN_VALUE;
		int maxBlue = Integer.MIN_VALUE;

		for (int row = 0; row < temporalImageData.length; row++) {
			for (int column = 0; column < temporalImageData[row].length; column++) {
				maxRed = Math.max(maxRed, temporalImageData[row][column].getRed());
				maxGreen = Math.max(maxGreen, temporalImageData[row][column].getGreen());
				maxBlue = Math.max(maxBlue, temporalImageData[row][column].getBlue());
			}
		}

		return new GrayScaleFunctionsContainer(new DynamicRangeFunction(maxRed), new DynamicRangeFunction(maxGreen),
				new DynamicRangeFunction(maxBlue));
	}

	private WritableImage applyArithmeticOperation(Image image, Image image2, ArithmeticOperationBetweenImages arithmeticOperationBetweenImages) {
		int maxHeight = (int) Math.max(image.getHeight(), image2.getHeight());
		int maxWidth = (int) Math.max(image.getWidth(), image2.getWidth());
		TemporalColor[][] temporalImageData = calculateTemporalImageData(image, image2, arithmeticOperationBetweenImages, maxHeight, maxWidth);
		GrayScaleFunctionsContainer grayScaleFunctionsContainer = obtainFunctionsForExceededRGB(temporalImageData);
		return parseToImage(temporalImageData, maxWidth, maxHeight, grayScaleFunctionsContainer);
	}

	private WritableImage parseToImage(TemporalColor[][] temporalImageData, int maxWidth, int maxHeight,
			GrayScaleFunctionsContainer grayScaleFunctionsContainer) {
		WritableImage imageResult = new WritableImage(maxWidth, maxHeight);
		PixelWriter pixelWriter = imageResult.getPixelWriter();

		for (int row = 0; row < maxHeight; row++) {
			for (int column = 0; column < maxWidth; column++) {
				TemporalColor temporalColor = temporalImageData[row][column];
				int red = grayScaleFunctionsContainer.getRedGrayScaleFunction().apply(temporalColor.getRed());
				int blue = grayScaleFunctionsContainer.getBlueGrayScaleFunction().apply(temporalColor.getBlue());
				int green = grayScaleFunctionsContainer.getGreenGrayScaleFunction().apply(temporalColor.getGreen());
				pixelWriter.setColor(column, row, Color.rgb(red, green, blue));
			}
		}

		return imageResult;
	}

	@Override
	public WritableImage parseToImageWithNoise(TemporalColor[][] temporalImageData, int maxWidth, int maxHeight,
			GrayScaleFunctionsContainer grayScaleFunctionsContainer) {
		WritableImage imageResult = new WritableImage(maxWidth, maxHeight);
		PixelWriter pixelWriter = imageResult.getPixelWriter();

		for (int row = 0; row < maxHeight; row++) {
			for (int column = 0; column < maxWidth; column++) {
				TemporalColor temporalColor = temporalImageData[row][column];

				if (isNotIntoScale(temporalColor.getRed()) || isNotIntoScale(temporalColor.getGreen()) || isNotIntoScale(temporalColor.getBlue())) {
					int red = grayScaleFunctionsContainer.getRedGrayScaleFunction().apply(temporalColor.getRed());
					int blue = grayScaleFunctionsContainer.getBlueGrayScaleFunction().apply(temporalColor.getBlue());
					int green = grayScaleFunctionsContainer.getGreenGrayScaleFunction().apply(temporalColor.getGreen());
					pixelWriter.setColor(column, row, Color.rgb(red, green, blue));
				} else {
					pixelWriter.setColor(column, row, Color.rgb(temporalColor.getRed(), temporalColor.getGreen(), temporalColor.getBlue()));
				}
			}
		}

		return imageResult;
	}

	private boolean isNotIntoScale(int grayScale) {
		return grayScale > LIMIT_SCALE || grayScale < 0;
	}

	@Override
	public GrayScaleFunctionsContainer obtainFunctionsForExceededRGB(TemporalColor[][] temporalImageData) {
		int minRed = Integer.MAX_VALUE;
		int minGreen = Integer.MAX_VALUE;
		int minBlue = Integer.MAX_VALUE;
		int maxRed = Integer.MIN_VALUE;
		int maxGreen = Integer.MIN_VALUE;
		int maxBlue = Integer.MIN_VALUE;

		for (int row = 0; row < temporalImageData.length; row++) {
			for (int column = 0; column < temporalImageData[row].length; column++) {
				minRed = evaluateMin(minRed, temporalImageData[row][column].getRed());
				minGreen = evaluateMin(minBlue, temporalImageData[row][column].getGreen());
				minBlue = evaluateMin(minGreen, temporalImageData[row][column].getBlue());
				maxRed = evaluateMax(maxRed, temporalImageData[row][column].getRed());
				maxGreen = evaluateMax(maxBlue, temporalImageData[row][column].getGreen());
				maxBlue = evaluateMax(maxGreen, temporalImageData[row][column].getBlue());
			}
		}

		TemporalGrayScaleToGrayScaleFunction redGrayScaleFunction = new TemporalGrayScaleToGrayScaleFunction(minRed, maxRed);
		TemporalGrayScaleToGrayScaleFunction blueGrayScaleFunction = new TemporalGrayScaleToGrayScaleFunction(minBlue, maxBlue);
		TemporalGrayScaleToGrayScaleFunction greenGrayScaleFunction = new TemporalGrayScaleToGrayScaleFunction(minGreen, maxGreen);
		return new GrayScaleFunctionsContainer(redGrayScaleFunction, greenGrayScaleFunction, blueGrayScaleFunction);
	}

	private int evaluateMax(int maxRed, int red) {
		return Math.max(maxRed, red);
	}

	private int evaluateMin(int min, int temporalValue) {
		return Math.min(temporalValue, min);
	}

	private TemporalColor[][] calculateTemporalImageData(Image image, Image image2, ArithmeticOperationBetweenImages
			arithmeticOperationBetweenImages,
			int maxHeight, int maxWidth) {
		TemporalColor temporalImageData[][] = new TemporalColor[maxHeight][maxWidth];

		for (int row = 0; row < maxHeight; row++) {
			for (int column = 0; column < maxWidth; column++) {
				Color sumsColor1 = imagePositionEvaluator.obtainSumsColor(image, row, column);
				Color sumsColor2 = imagePositionEvaluator.obtainSumsColor(image2, row, column);
				Supplier<TemporalColor> operation = () -> arithmeticOperationBetweenImages.calculateColor(sumsColor1, sumsColor2);
				temporalImageData[row][column] = operation.get();
			}
		}
		return temporalImageData;
	}
}
