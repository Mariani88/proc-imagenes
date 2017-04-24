package untref.domain.aleatorygenerator;

import java.util.Random;

public class AleatoryNumberRayleigh implements AleatoryNumberGenerator {

	private Random random;
	private double epsilon;

	public AleatoryNumberRayleigh(Random random, double epsilon) {
		this.random = random;
		this.epsilon = epsilon;
	}

	@Override
	public int generate() {
		return (int)(epsilon * Math.sqrt(-2 * calculateLogarithm()));
	}

	private double calculateLogarithm() {
		return Math.log(1 - random.nextDouble());
	}
}