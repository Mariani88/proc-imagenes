package untref.service.arithmeticoperations.noise;

import javafx.scene.paint.Color;
import untref.domain.TemporalColor;

import static untref.domain.utils.ImageValuesTransformer.toInt;

public class MultiplicativeNoiseAdder implements ArithmeticOperationAddNoise {

	private static final double MAXIMUM_GRAY = 255;

	@Override
	public TemporalColor add(Color color, TemporalColor noise) {
		int redWithNoise = toInt(color.getRed() * MAXIMUM_GRAY) * noise.getRed();
		int greenWithNoise = toInt(color.getGreen() * MAXIMUM_GRAY) * noise.getGreen();
		int blueWithNoise = toInt(color.getBlue() * MAXIMUM_GRAY) * noise.getBlue();
		return new TemporalColor(redWithNoise, greenWithNoise, blueWithNoise);
	}
}