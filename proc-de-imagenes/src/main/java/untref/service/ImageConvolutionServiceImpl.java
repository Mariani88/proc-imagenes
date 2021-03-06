package untref.service;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import untref.domain.TemporalColor;

public class ImageConvolutionServiceImpl implements ImageConvolutionService {

	private final MaskApplicationService maskApplicationService;

	public ImageConvolutionServiceImpl(MaskApplicationService maskApplicationService) {
		this.maskApplicationService = maskApplicationService;
	}

	@Override
	public TemporalColor[][] calculateConvolution(double[][] convolutionOperator, Image image, int width, int height) {
		PixelReader pixelReader = image.getPixelReader();
		TemporalColor imageLaplacian[][] = new TemporalColor[height][width];
		int offsetI = convolutionOperator.length/2;
		int offsetJ = convolutionOperator.length/2;

		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				imageLaplacian[row][column] = maskApplicationService
						.applyMask(image, row, column, pixelReader, convolutionOperator, offsetI, offsetJ);
			}
		}
		return imageLaplacian;
	}
}