package untref.service;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import untref.domain.GrayScaleFunctionsContainer;
import untref.domain.TemporalColor;
import untref.service.functions.LinearFunction;

import java.util.function.Function;

import static untref.domain.utils.ImageValuesTransformer.toInt;

public class ContrastServiceImpl implements ContrastService {

	private static final int LIMIT_GRAY_SCALE = 255;
	private static final double ABSENT_COLOR = 0;
	private ImageStatisticService imageStatisticService;

	public ContrastServiceImpl(ImageStatisticService imageStatisticService) {
		this.imageStatisticService = imageStatisticService;
	}

	@Override
	public Image addContrast(Image image) {
		TemporalColor colorAverage = imageStatisticService.calculateColorAverage(image);
		TemporalColor colorStandardDeviation = imageStatisticService.calculateColorStandardDeviation(image, colorAverage);
		TemporalColor r1 = subtract(colorAverage, colorStandardDeviation);
		TemporalColor r2 = plus(colorAverage, colorStandardDeviation);
		TemporalColor s1 = calculateS1(colorAverage, colorStandardDeviation);
		TemporalColor s2 = calculateS2(r2, colorAverage, colorStandardDeviation);

		GrayScaleFunctionsContainer lowerFunctionsContainer = new GrayScaleFunctionsContainer(
				new LinearFunction(ABSENT_COLOR, ABSENT_COLOR, r1.getRed(), s1.getRed()),
				new LinearFunction(ABSENT_COLOR, ABSENT_COLOR, r1.getGreen(), s1.getGreen()),
				new LinearFunction(ABSENT_COLOR, ABSENT_COLOR, r1.getBlue(), s1.getBlue()));   //F1  apply for 0 <= r <= r1, for RGB

		GrayScaleFunctionsContainer mediumFunctionsContainer = new GrayScaleFunctionsContainer(
				new LinearFunction(r1.getRed(), s1.getRed(), r2.getRed(), s2.getRed()),
				new LinearFunction(r1.getGreen(), s1.getGreen(), r2.getGreen(), s2.getGreen()),
				new LinearFunction(r1.getBlue(), s1.getBlue(), r2.getBlue(), s2.getBlue()));   //F2  apply for r1 < r <= r2, for RGB

		GrayScaleFunctionsContainer higherFunctionsContainer = new GrayScaleFunctionsContainer(
				new LinearFunction(r2.getRed(), s2.getRed(), LIMIT_GRAY_SCALE, LIMIT_GRAY_SCALE),
				new LinearFunction(r2.getGreen(), s2.getGreen(), LIMIT_GRAY_SCALE, LIMIT_GRAY_SCALE),
				new LinearFunction(r2.getBlue(), s2.getBlue(), LIMIT_GRAY_SCALE, LIMIT_GRAY_SCALE));   //F3  apply for r2 < r <= 255, for RGB

		return applyContrast(r1, r2, image, lowerFunctionsContainer, mediumFunctionsContainer, higherFunctionsContainer);
	}

	private TemporalColor subtract(TemporalColor colorAverage, TemporalColor colorStandardDeviation) {
		int red = colorAverage.getRed() - colorStandardDeviation.getRed();
		int green = colorAverage.getGreen() - colorStandardDeviation.getGreen();
		int blue = colorAverage.getBlue() - colorStandardDeviation.getBlue();
		return new TemporalColor(red,green,blue);
	}

	private TemporalColor plus(TemporalColor colorAverage, TemporalColor colorStandardDeviation) {
		int red = colorAverage.getRed() + colorStandardDeviation.getRed();
		int green = colorAverage.getGreen() + colorStandardDeviation.getGreen();
		int blue = colorAverage.getBlue() + colorStandardDeviation.getBlue();
		return new TemporalColor(red,green,blue);
	}

	private TemporalColor calculateS1(TemporalColor colorAverage, TemporalColor colorStandardDeviation) {
		int red = Math.max(0, colorAverage.getRed() - 2 * colorStandardDeviation.getRed());
		int green = Math.max(0, colorAverage.getGreen() - 2 * colorStandardDeviation.getGreen());
		int blue = Math.max(0, colorAverage.getBlue() - 2 * colorStandardDeviation.getBlue());
		return new TemporalColor(red, green, blue);
	}

	private TemporalColor calculateS2(TemporalColor r2, TemporalColor colorAverage, TemporalColor colorStandardDeviation) {
		int red = (LIMIT_GRAY_SCALE - (colorAverage.getRed() + colorStandardDeviation.getRed())) / 2 + r2.getRed();
		int green = (LIMIT_GRAY_SCALE - (colorAverage.getGreen() + colorStandardDeviation.getGreen())) / 2 + r2.getGreen();
		int blue = (LIMIT_GRAY_SCALE - (colorAverage.getBlue() + colorStandardDeviation.getBlue())) / 2 + r2.getBlue();
		return new TemporalColor(red, green, blue);
	}

	private Image applyContrast(TemporalColor r1, TemporalColor r2, Image image, GrayScaleFunctionsContainer lowerFunctionsContainer,
			GrayScaleFunctionsContainer mediumFunctionsContainer, GrayScaleFunctionsContainer higherFunctionsContainer) {
		WritableImage imageWithContrast = new WritableImage(toInt(image.getWidth()), toInt(image.getHeight()));
		PixelWriter pixelWriter = imageWithContrast.getPixelWriter();
		PixelReader pixelReader = image.getPixelReader();

		for (int row = 0; row < imageWithContrast.getHeight(); row++) {
			for (int column = 0; column < imageWithContrast.getWidth(); column++) {
				pixelWriter.setColor(column, row,
						applyContrastTransformation(r1, r2, pixelReader.getColor(column, row), lowerFunctionsContainer, mediumFunctionsContainer,
								higherFunctionsContainer));
			}
		}

		return imageWithContrast;
	}

	private Color applyContrastTransformation(TemporalColor r1, TemporalColor r2, Color color, GrayScaleFunctionsContainer lowerFunctionsContainer,
			GrayScaleFunctionsContainer mediumFunctionsContainer, GrayScaleFunctionsContainer higherFunctionsContainer) {
		int redWithContrast = applyContrastToChannel(r1.getRed(), r2.getRed(), color.getRed(), lowerFunctionsContainer.getRedGrayScaleFunction(),
				mediumFunctionsContainer.getRedGrayScaleFunction(), higherFunctionsContainer.getRedGrayScaleFunction());

		int greenWithContrast = applyContrastToChannel(r1.getGreen(), r2.getGreen(), color.getGreen(),
				lowerFunctionsContainer.getGreenGrayScaleFunction(), mediumFunctionsContainer.getGreenGrayScaleFunction(),
				higherFunctionsContainer.getGreenGrayScaleFunction());

		int blueWithContrast = applyContrastToChannel(r1.getBlue(), r2.getBlue(), color.getBlue(), lowerFunctionsContainer
						.getBlueGrayScaleFunction(),
				mediumFunctionsContainer.getBlueGrayScaleFunction(), higherFunctionsContainer.getBlueGrayScaleFunction());

		return Color.rgb(redWithContrast, greenWithContrast, blueWithContrast);
	}

	private int applyContrastToChannel(int r1Gray, int r2Gray, double gray, Function<Integer, Integer> lowerGrayScaleFunction,
			Function<Integer, Integer> mediumGrayScaleFunction, Function<Integer, Integer> higherGrayScaleFunction) {
		int RGBGray = toInt(gray * LIMIT_GRAY_SCALE);

		if (RGBGray <= r1Gray) {
			RGBGray = lowerGrayScaleFunction.apply(RGBGray);
		} else if (r1Gray < RGBGray && RGBGray <= r2Gray) {
			RGBGray = mediumGrayScaleFunction.apply(RGBGray);
		} else {
			RGBGray = higherGrayScaleFunction.apply(RGBGray);
		}

		return RGBGray;
	}
}