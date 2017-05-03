package untref.service;

import javafx.scene.image.Image;
import untref.domain.aleatorygenerator.AleatoryNumberGenerator;
import untref.domain.noisetypes.NoiseType;

public interface NoiseService {
	Image addNoiseToImage(Image image, Double contaminationPercent, AleatoryNumberGenerator aleatoryNumberGenerator, NoiseType noiseType);

	Image addSaltAndPepperNoiseToImage(Image image, Double blackPixelNoiseProbability);
}
