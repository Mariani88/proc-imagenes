package untref.service;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import untref.domain.TemporalColor;

import static untref.domain.utils.ImageValuesTransformer.toRGBScale;
import static untref.utils.ImageValidator.existPosition;

public class MaskApplicationServiceImpl implements MaskApplicationService {

	@Override
	public TemporalColor applyMask(Image image, int row, int column, PixelReader pixelReader, int mask[][], int offsetI, int offsetJ) {
		int red = 0;
		int green = 0;
		int blue = 0;

		for (int i = 0; i < mask.length; i++) {
			for (int j = 0; j < mask[i].length; j++) {
				TemporalColor positionColor = getPositionColor(row - offsetI + i, column - offsetJ + j, image, pixelReader);
				red += mask[i][j] * positionColor.getRed();
				green += mask[i][j] * positionColor.getGreen();
				blue += mask[i][j] * positionColor.getBlue();
			}
		}
		return new TemporalColor(red, green, blue);
	}

	private TemporalColor getPositionColor(int row, int column, Image image, PixelReader pixelReader) {
		TemporalColor temporalColor = new TemporalColor(0, 0, 0);

		if (existPosition(image, row, column)) {
			Color color = pixelReader.getColor(column, row);
			temporalColor = new TemporalColor(toRGBScale(color.getRed()), toRGBScale(color.getGreen()), toRGBScale(color.getBlue()));
		}

		return temporalColor;
	}
}