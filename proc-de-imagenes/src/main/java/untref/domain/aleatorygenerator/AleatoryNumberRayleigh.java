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

	@Override
	public int[] generate(int sampleSize) {
		int sample[] = new int[sampleSize];

		for (int index = 0; index < sampleSize; index++){
			sample[index] = generate();
		}

		return sample;
	}

	private double calculateLogarithm() {
		return Math.log(1 - random.nextDouble());
	}
}