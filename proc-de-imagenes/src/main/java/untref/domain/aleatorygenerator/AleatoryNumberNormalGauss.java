package untref.domain.aleatorygenerator;

import java.util.Random;

public class AleatoryNumberNormalGauss implements AleatoryNumberGenerator{

	private Random random;
	private double mu;
	private double sigma;

	public AleatoryNumberNormalGauss(Random random, double mu, double sigma) {
		this.random = random;
		this.mu = mu;
		this.sigma = sigma;
	}

	@Override
	public int generate() {
		return 0;
	}
}