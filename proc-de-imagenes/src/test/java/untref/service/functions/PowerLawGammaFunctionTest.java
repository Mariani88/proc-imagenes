package untref.service.functions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PowerLawGammaFunctionTest {

	private static final double GAMMA = 0.4;
	private PowerLawGammaFunction powerLawGammaFunction;

	@Before
	public void setUp() throws Exception {
		powerLawGammaFunction = new PowerLawGammaFunction(GAMMA);
	}

	@Test
	public void whenApplyToMaxValueThenReturnValueLowerOrEqualsThan255(){
		Integer value = powerLawGammaFunction.apply(255);
		Assert.assertTrue(value <= 255);
	}
}