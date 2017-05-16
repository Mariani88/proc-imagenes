package untref.service;

import untref.service.functions.TemporalGrayScaleToGrayScaleFunction;

import org.junit.Assert;
import org.junit.Test;

public class TemporalGrayScaleToGrayScaleFunctionTest {

	@Test
	public void whenTransformExceededValuesThenTransformThemWithLinearFunction() throws Exception {
		int minValue = 5;
		int maxValue = 2550;
		TemporalGrayScaleToGrayScaleFunction function = new TemporalGrayScaleToGrayScaleFunction(minValue, maxValue);
		Integer maxTransformed = function.apply(maxValue);
		Integer minTransformed = function.apply(minValue);
		int temporalGrayScale = 1275;
		Integer transformedValue = function.apply(temporalGrayScale);
		Assert.assertEquals(255, maxTransformed.intValue());
		Assert.assertEquals(0, minTransformed.intValue());
		Assert.assertEquals(127, transformedValue.intValue());
	}

	@Test
	public void whenTransformInferiorValuesThenTransformThemWithLinearFunction() throws Exception {
		int minValue = -5;
		int maxValue = 2545;
		TemporalGrayScaleToGrayScaleFunction function = new TemporalGrayScaleToGrayScaleFunction(minValue, maxValue);
		Integer maxTransformed = function.apply(maxValue);
		Integer minTransformed = function.apply(minValue);
		int temporalGrayScale = 1270;
		Integer transformedValue = function.apply(temporalGrayScale);
		Assert.assertEquals(255, maxTransformed.intValue());
		Assert.assertEquals(0, minTransformed.intValue());
		Assert.assertEquals(127, transformedValue.intValue());
	}

	@Test
	public void whenTransformValidValuesThenNotTransform() throws Exception {
		int minValue = 5;
		int maxValue = 25;
		TemporalGrayScaleToGrayScaleFunction function = new TemporalGrayScaleToGrayScaleFunction(minValue, maxValue);
		Integer maxTransformed = function.apply(maxValue);
		Integer minTransformed = function.apply(minValue);
		int temporalGrayScale = 10;
		Integer transformedValue = function.apply(temporalGrayScale);
		Assert.assertEquals(maxValue, maxTransformed.intValue());
		Assert.assertEquals(minValue, minTransformed.intValue());
		Assert.assertEquals(temporalGrayScale, transformedValue.intValue());
	}
}