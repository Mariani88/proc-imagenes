package untref.service.functions;

import java.util.function.Function;

public class DynamicRangeFunction implements Function<Integer, Integer> {

	private static final int AMOUNT_COLORS = 256;
	private final double c;
	private boolean shouldApply;

	public DynamicRangeFunction(int maxGrayValue) {
		c = (double) (AMOUNT_COLORS - 1) / Math.log10(1 + maxGrayValue);
		shouldApply = maxGrayValue >= AMOUNT_COLORS || maxGrayValue < 0;
	}

	@Override
	public Integer apply(Integer grayValue) {
		int transformed = grayValue;

		if (shouldApply) {
			transformed = (int) (c * Math.log10(grayValue + 1));
		}

		return transformed;
	}
}