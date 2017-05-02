package untref.service.arithmeticoperations;

import javafx.scene.paint.Color;
import untref.domain.TemporalColor;

public class ArithmeticOperationBetweenImagesMultiply implements ArithmeticOperationBetweenImages {

	private static final int LIMIT_SCALE = 255;

	@Override
	public TemporalColor calculateColor(Color sumsColor1, Color sumsColor2) {
		int red = (int) ((sumsColor1.getRed() * sumsColor2.getRed()) * LIMIT_SCALE);
		int blue = (int) ((sumsColor1.getBlue() * sumsColor2.getBlue()) * LIMIT_SCALE);
		int green = (int) ((sumsColor1.getGreen() * sumsColor2.getGreen()) * LIMIT_SCALE);
		return new TemporalColor(red, green, blue);
	}
}