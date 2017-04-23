package untref.domain.aleatorygenerator;

import java.util.Random;

public class AleatoryNumberExponential implements AleatoryNumberGenerator {

	private final double coefficient;
	private Random random;
	private double lambda;

	public AleatoryNumberExponential(Random random, double lambda) {
		this.random = random;
		this.lambda = lambda;
		this.coefficient = calculateCoefficient();
	}

	@Override
	public int generate() {
		return (int) (coefficient * calculateLogarithm());
	}

	private double calculateLogarithm() {
		return Math.log(random.nextDouble());
	}

	private double calculateCoefficient() {
		return (double) (-1) / lambda;
	}
}