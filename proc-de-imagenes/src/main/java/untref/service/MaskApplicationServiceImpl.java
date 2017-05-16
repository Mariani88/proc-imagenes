package untref.service;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import untref.domain.TemporalColor;

import static untref.domain.utils.ImageValuesTransformer.getPositionColorOrEmpty;

public class MaskApplicationServiceImpl implements MaskApplicationService {

	@Override
	public TemporalColor applyMask(Image image, int row, int column, PixelReader pixelReader, int mask[][], int offsetI, int offsetJ) {
		int red = 0;
		int green = 0;
		int blue = 0;

		for (int i = 0; i < mask.length; i++) {
			for (int j = 0; j < mask[i].length; j++) {
				TemporalColor positionColor = getPositionColorOrEmpty(row - offsetI + i, column - offsetJ + j, image, pixelReader);
				red += mask[i][j] * positionColor.getRed();
				green += mask[i][j] * positionColor.getGreen();
				blue += mask[i][j] * positionColor.getBlue();
			}
		}
		return new TemporalColor(red, green, blue);
	}
}