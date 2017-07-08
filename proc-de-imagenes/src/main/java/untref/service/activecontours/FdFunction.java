package untref.service.activecontours;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import untref.domain.ImagePosition;

import static untref.domain.utils.ImageValuesTransformer.toRGBScale;

public class FdFunction {

	public boolean apply(ImagePosition imagePosition, Image originalImage, Color objectColorAverage, Double colorDelta) {
		PixelReader pixelReader = originalImage.getPixelReader();
		Color imagePositionColor = pixelReader.getColor(imagePosition.getColumn(), imagePosition.getRow());
		int difRed = toRGBScale(imagePositionColor.getRed() - objectColorAverage.getRed());
		int difGreen = toRGBScale(imagePositionColor.getGreen() - objectColorAverage.getGreen());
		int difBlue = toRGBScale(imagePositionColor.getBlue() - objectColorAverage.getBlue());
		double module = Math.sqrt(Math.pow(difRed, 2) + Math.pow(difGreen, 2) + Math.pow(difBlue, 2));
		return !(module >= colorDelta);
	}
}