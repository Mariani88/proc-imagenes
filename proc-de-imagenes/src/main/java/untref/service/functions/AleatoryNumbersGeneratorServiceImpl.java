package untref.service.functions;

import untref.domain.aleatorygenerator.AleatoryNumberExponential;
import untref.domain.aleatorygenerator.AleatoryNumberGenerator;
import untref.domain.aleatorygenerator.AleatoryNumberNormalGauss;
import untref.domain.aleatorygenerator.AleatoryNumberRayleigh;
import untref.service.AleatoryNumbersGeneratorService;

import java.util.Random;

public class AleatoryNumbersGeneratorServiceImpl implements AleatoryNumbersGeneratorService {

	private Random random;

	public AleatoryNumbersGeneratorServiceImpl(Random random) {
		this.random = random;
	}

	@Override
	public int[] generateExponentialSample(double lambda, int sampleSize) {
		return generateSample(new AleatoryNumberExponential(random, lambda), sampleSize);
	}

	@Override
	public int[] generateRayleightSample(double epsilon, int sampleSize) {
		return generateSample(new AleatoryNumberRayleigh(new Random(), epsilon), sampleSize);
	}

	@Override
	public int[] generateNormalGaussSample(double mu, double sigma, int sampleSize) {
		return generateSample(new AleatoryNumberNormalGauss(new Random(), mu, sigma), sampleSize);
	}

	private int[] generateSample(AleatoryNumberGenerator aleatoryNumberGenerator, int sampleSize) {
		int sample[] = new int[sampleSize];

		for (int index = 0; index < sampleSize; index++) {
			sample[index] = aleatoryNumberGenerator.generate();
		}

		return sample;
	}
}