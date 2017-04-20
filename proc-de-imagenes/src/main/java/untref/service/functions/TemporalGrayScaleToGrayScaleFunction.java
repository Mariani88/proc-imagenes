package untref.service.functions;

import java.util.function.Function;

public class TemporalGrayScaleToGrayScaleFunction implements Function<Integer, Integer> {

	private static final double MIN_GRAY_SCALE = 0;
	private static final double MAX_GRAY_SCALE = 255;
	private double m;
	private double b;

	public TemporalGrayScaleToGrayScaleFunction(int minValue, int maxValue) {
		this.createLinearFunction(minValue, maxValue);
	}

	@Override
	public Integer apply(Integer temporalGrayScale) {
		return (int) (m * temporalGrayScale + b);
	}

	private void createLinearFunction(int minValue, int maxValue) {
		m = 1;
		b = 0;

		if (minValue < MIN_GRAY_SCALE || maxValue > MAX_GRAY_SCALE) {
			m = MAX_GRAY_SCALE / (double) (maxValue - minValue);
			b = -m * minValue;
		}
	}
}