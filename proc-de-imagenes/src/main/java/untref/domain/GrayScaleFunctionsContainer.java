package untref.domain;

import untref.service.functions.TemporalGrayScaleToGrayScaleFunction;

public class GrayScaleFunctionsContainer {
	private TemporalGrayScaleToGrayScaleFunction redGrayScaleFunction;
	private TemporalGrayScaleToGrayScaleFunction greenGrayScaleFunction;
	private TemporalGrayScaleToGrayScaleFunction blueGrayScaleFunction;

	public GrayScaleFunctionsContainer(TemporalGrayScaleToGrayScaleFunction redGrayScaleFunction,
			TemporalGrayScaleToGrayScaleFunction greenGrayScaleFunction, TemporalGrayScaleToGrayScaleFunction blueGrayScaleFunction) {
		this.redGrayScaleFunction = redGrayScaleFunction;
		this.greenGrayScaleFunction = greenGrayScaleFunction;
		this.blueGrayScaleFunction = blueGrayScaleFunction;
	}

	public TemporalGrayScaleToGrayScaleFunction getRedGrayScaleFunction() {
		return redGrayScaleFunction;
	}

	public TemporalGrayScaleToGrayScaleFunction getGreenGrayScaleFunction() {
		return greenGrayScaleFunction;
	}

	public TemporalGrayScaleToGrayScaleFunction getBlueGrayScaleFunction() {
		return blueGrayScaleFunction;
	}
}
