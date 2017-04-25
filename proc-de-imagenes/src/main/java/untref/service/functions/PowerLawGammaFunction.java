package untref.service.functions;

import java.util.function.Function;

public class PowerLawGammaFunction implements Function<Integer, Integer> {

	private static final int AMOUNT_COLORS = 256;
	private final double gamma;
	private double c;

	public PowerLawGammaFunction(double gamma) {
		this.gamma = gamma;
		c = Math.pow(AMOUNT_COLORS - 1, 1 - this.gamma);
	}

	@Override
	public Integer apply(Integer grayValue) {
		return (int) (c * Math.pow(grayValue, gamma));
	}
}