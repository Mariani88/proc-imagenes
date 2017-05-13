package untref.service;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import untref.domain.ThresholdingResult;
import untref.domain.utils.ImageValuesTransformer;

import static untref.domain.utils.ImageValuesTransformer.toGrayScale;
import static untref.domain.utils.ImageValuesTransformer.toInt;

public class ThresholdingServiceImpl implements ThresholdingService {

	@Override
	public Image getImageThreshold(Image image, int valueThreshold) {
		ImageGetColorRGB colorRgb = new ImageGetColorRGBImpl(image);
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

	@Override
	public ThresholdingResult obtainThresholdByBasicGlobalMethod(Image image, Double initialThreshold, Double deltaThreshold) {
		double beforeThreshold = 0;
		double actualThreshold = beforeThreshold + deltaThreshold + 1;
		int iterations = 0;

		while (!isLowerOrEqualsToDelta(beforeThreshold, actualThreshold, deltaThreshold)) {
			beforeThreshold = actualThreshold;
			actualThreshold = calculateActualThreshold(image, actualThreshold);
			iterations++;
		}

		Image thresholdingimage = getImageThreshold(image, toInt(actualThreshold));
		return new ThresholdingResult(thresholdingimage, toInt(actualThreshold), iterations);
	}

	private double calculateActualThreshold(Image image, double actualThreshold) {
		int width = toInt(image.getWidth());
		int height = toInt(image.getHeight());
		PixelReader pixelReader = image.getPixelReader();
		int sumG1 = 0;
		int sumG2 = 0;
		int totalG1 = 0;
		int totalG2 = 0;

		for(int row = 0; row < height; row++){
			for (int column = 0; column < width; column++){
				int grayValue = toGrayScale(pixelReader.getColor(column, row));
				if(grayValue < actualThreshold){
					sumG1+= grayValue;
					totalG1++;
				}else {
					sumG2+= grayValue;
					totalG2++;
				}
			}
		}

		return (average(sumG1, totalG1) + average(sumG2, totalG2))/2;
	}

	private int average(int sumG, int totalG) {
		if(totalG == 0){
			return 0;
		}

		return sumG/totalG;
	}

	private boolean isLowerOrEqualsToDelta(double beforeThreshold, double actualThreshold, Double deltaThreshold) {
		return deltaThreshold.compareTo(Math.abs(actualThreshold - beforeThreshold)) >= 0;
	}
}