package untref.service.functions;

import java.util.function.Function;

import static untref.domain.utils.ImageValuesTransformer.toInt;

public class LinearFunction implements Function<Integer, Integer> {

	private final double m;
	private final double b;

	public LinearFunction(double x1, double y1, double x2, double y2) {
		m = (y2 - y1) / (x2 - x1);
		b = y1 - m * x1;
	}

	@Override
	public Integer apply(Integer value) {
		return toInt(m * value + b);
	}
}