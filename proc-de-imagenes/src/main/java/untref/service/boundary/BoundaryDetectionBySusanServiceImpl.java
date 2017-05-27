package untref.service.boundary;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import untref.domain.susan.ImageElementSusan;
import untref.domain.susan.operation.SusanAccumulationOperation;

import static untref.domain.utils.ImageValuesTransformer.*;

public class BoundaryDetectionBySusanServiceImpl implements BoundaryDetectionBySusanService {

	private static final double SUSAN_MASK_SIZE = 37;
	private static final int OFFSET = 3;

	@Override
	public Image detect(Image image, ImageElementSusan imageElementSusan, Integer threshold, Double accumulateDelta) {
		int width = toInt(image.getWidth());
		int height = toInt(image.getHeight());
		WritableImage imageWithDetectedElement = new WritableImage(width, height);
		PixelReader pixelReader = image.getPixelReader();
		PixelWriter pixelWriter = imageWithDetectedElement.getPixelWriter();

		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				double elementDetectionParameter = calculateElementDetectionParameter(threshold, row, column, pixelReader, image);
				Color imageColor = pixelReader.getColor(column, row);
				pixelWriter.setColor(column, row, imageElementSusan.calculateElement(elementDetectionParameter, accumulateDelta, imageColor));
			}
		}

		return imageWithDetectedElement;
	}

	private double calculateElementDetectionParameter(Integer threshold, int row, int column, PixelReader pixelReader, Image image) {
		SusanAccumulationOperation susanMask[][] = SusanMaskBuilder.build();
		int centralGray = toGrayScale(pixelReader.getColor(column, row));
		double accumulator = 0;

		for (int maskRow = 0; maskRow < susanMask.length; maskRow++) {
			for (int maskColumn = 0; maskColumn < susanMask.length; maskColumn++) {
				int gray = toGrayScaleOrEmpty(row - OFFSET + maskRow, column - OFFSET + maskColumn, image);
				accumulator += susanMask[maskRow][maskColumn].calculate(gray, centralGray, threshold);
			}
		}

		return accumulator / SUSAN_MASK_SIZE;
	}
}