package untref.service;

import javafx.scene.image.Image;
import untref.domain.ImagePosition;
import untref.domain.TemporalColor;
import untref.domain.aleatorygenerator.AleatoryNumberGenerator;
import untref.domain.aleatorygenerator.UniformDistributionRandom;
import untref.domain.noisetypes.NoiseType;

import java.util.ArrayList;
import java.util.List;

public class NoiseServiceImpl implements NoiseService {

	private final UniformDistributionRandom uniformDistributionRandom;

	public NoiseServiceImpl() {
		uniformDistributionRandom = new UniformDistributionRandom();
	}

	@Override
	public Image addNoiseToImage(Image image, Double contaminationPercent, AleatoryNumberGenerator aleatoryNumberGenerator, NoiseType noiseType) {
		int allPositions = (int) (image.getHeight() * image.getWidth());
		int amountPositionsToAddNoise = (int) (allPositions * contaminationPercent / 100);
		int[] noise = aleatoryNumberGenerator.generate(amountPositionsToAddNoise);
		List<ImagePosition> imagePositions = createImagePositionsList(image, allPositions);
		TemporalColor[][] noiseMatrix = generateNoiseMatrix(image, noiseType, amountPositionsToAddNoise, noise, imagePositions);
		return noiseType.applyNoise(image, noiseMatrix);
	}

	private TemporalColor[][] generateNoiseMatrix(Image image, NoiseType noiseType, int amountPositionsToAddNoise, int[] noise,
			List<ImagePosition> imagePositions) {
		TemporalColor noiseMatrix[][] = noiseType.initializeNoiseMatrix((int) image.getWidth(), (int) image.getHeight());

		for (int index = 0; index < amountPositionsToAddNoise; index++) {
			int indexPosition = uniformDistributionRandom.generate(0, imagePositions.size());
			ImagePosition position = imagePositions.remove(indexPosition);
			noiseMatrix[position.getRow()][position.getColumn()] = new TemporalColor(noise[index], noise[index], noise[index]);
		}

		return noiseMatrix;

	}

	private List<ImagePosition> createImagePositionsList(Image image, int allPositions) {
		List<ImagePosition> imagePositions = new ArrayList<>(allPositions);

		for (int row = 0; row < image.getHeight(); row++) {
			for (int column = 0; column < image.getWidth(); column++) {
				imagePositions.add(new ImagePosition(row, column));
			}
		}

		return imagePositions;
	}
}