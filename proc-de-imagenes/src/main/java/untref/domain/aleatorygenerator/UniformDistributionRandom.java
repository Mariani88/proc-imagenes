package untref.domain.aleatorygenerator;

import java.util.Random;

public class UniformDistributionRandom {

	private Random random;


	public UniformDistributionRandom() {
		random = new Random();
	}

	public int generate(int inclusiveLimit, int exclusiveLimit){
		int offset = exclusiveLimit - inclusiveLimit;
		return (int)(random.nextDouble() * offset + inclusiveLimit);
	}
}