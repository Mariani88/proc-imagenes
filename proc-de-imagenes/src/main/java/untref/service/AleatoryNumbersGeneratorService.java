package untref.service;

public interface AleatoryNumbersGeneratorService {

	int[] generateExponentialSample(double lambda, int sampleSize);

	int[] generateRayleightSample(double epsilon, int sampleSize);

	int[] generateNormalGaussSample(double mu, double sigma, int sampleSize);
}