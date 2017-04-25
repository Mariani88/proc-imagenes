package untref.domain;

import java.util.function.Function;

public class GrayScaleFunctionsContainer {
	private Function<Integer, Integer> redGrayScaleFunction;
	private Function<Integer, Integer> greenGrayScaleFunction;
	private Function<Integer, Integer> blueGrayScaleFunction;

	public GrayScaleFunctionsContainer(Function<Integer, Integer> redGrayScaleFunction, Function<Integer, Integer> greenGrayScaleFunction,
			Function<Integer, Integer> blueGrayScaleFunction) {
		this.redGrayScaleFunction = redGrayScaleFunction;
		this.greenGrayScaleFunction = greenGrayScaleFunction;
		this.blueGrayScaleFunction = blueGrayScaleFunction;
	}

	public Function<Integer, Integer> getRedGrayScaleFunction() {
		return redGrayScaleFunction;
	}

	public Function<Integer, Integer> getGreenGrayScaleFunction() {
		return greenGrayScaleFunction;
	}

	public Function<Integer, Integer> getBlueGrayScaleFunction() {
		return blueGrayScaleFunction;
	}
}
