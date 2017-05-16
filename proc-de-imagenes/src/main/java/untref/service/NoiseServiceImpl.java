package untref.service;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import untref.domain.ImagePosition;
import untref.domain.TemporalColor;
import untref.domain.aleatorygenerator.AleatoryNumberGenerator;
import untref.domain.aleatorygenerator.UniformDistributionRandom;
import untref.domain.noisetypes.NoiseType;

import java.util.ArrayList;
import java.util.List;

import static untref.domain.utils.ImageValuesTransformer.toInt;

public class NoiseServiceImpl implements NoiseService {

	private final UniformDistributionRandom uniformDistributionRandom;
	private ImageArithmeticOperationServiceImpl imageArithmeticOperationService;

	public NoiseServiceImpl(ImageArithmeticOperationServiceImpl imageArithmeticOperationService) {
		this.imageArithmeticOperationService = imageArithmeticOperationService;
		uniformDistributionRandom = new UniformDistributionRandom();
	}

	@Override
	public Image addNoiseToImage(Image image, Double contaminationPercent, AleatoryNumberGenerator aleatoryNumberGenerator, NoiseType noiseType) {
		int allPositions = (int) (image.getHeight() * image.getWidth());
		int amountPositionsToAddNoise = (int) (allPositions * contaminationPercent / 100);
		int[] noise = aleatoryNumberGenerator.generate(amountPositionsToAddNoise);
		List<ImagePosition> imagePositions = createImagePositionsList(image, allPositions);
		TemporalColor[][] noiseMatrix = generateNoiseMatrix(image, noiseType, amountPositionsToAddNoise, noise, imagePositions);
		return noiseType.applyNoise(image, noiseMatrix, imageArithmeticOperationService);
	}

	@Override
	public Image addSaltAndPepperNoiseToImage(Image image, Double blackPixelNoiseProbability) {
		WritableImage imageWithNoise = new WritableImage(toInt(image.getWidth()), toInt(image.getHeight()));
		PixelReader pixelReader = image.getPixelReader();
		PixelWriter pixelWriter = imageWithNoise.getPixelWriter();
		double whitePixelNoiseProbability = 1 - blackPixelNoiseProbability;
		int blackPixelNoisePercent = toInt(blackPixelNoiseProbability * 100);
		int whitePixelNoisePercent = toInt(whitePixelNoiseProbability * 100);

		for(int row = 0; row < toInt(image.getHeight()); row++){
			for (int column = 0; column < toInt(image.getWidth()); column++){
				int aleatoryValue = uniformDistributionRandom.generate(0, 100);
				addSaltAndPepper(pixelReader, pixelWriter, blackPixelNoisePercent, whitePixelNoisePercent, row, column, aleatoryValue);
			}
		}

		return imageWithNoise;
	}

	private void addSaltAndPepper(PixelReader pixelReader, PixelWriter pixelWriter, int blackPixelNoisePercent, int whitePixelNoisePercent, int row,
			int column, int aleatoryValue) {
		if(aleatoryValue <= blackPixelNoisePercent){
			pixelWriter.setColor(column,row, Color.rgb(0,0,0));
		}else if(aleatoryValue >= whitePixelNoisePercent){
			pixelWriter.setColor(column,row, Color.rgb(255,255,255));
		}else{
			pixelWriter.setColor(column,row, pixelReader.getColor(column,row));
		}
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