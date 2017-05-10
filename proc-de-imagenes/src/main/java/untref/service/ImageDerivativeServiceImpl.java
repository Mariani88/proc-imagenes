package untref.service;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import untref.domain.TemporalColor;

public class ImageDerivativeServiceImpl implements ImageDerivativeService {

	private static final int LAPLACIAN_OPERATOR[][] = { { 0, -1, 0 }, { -1, 4, -1 }, { 0, -1, 0 } };
	private MaskApplicationService maskApplicationService;

	public ImageDerivativeServiceImpl(MaskApplicationService maskApplicationService) {
		this.maskApplicationService = maskApplicationService;
	}

	@Override
	public TemporalColor[][] calculateLaplacian(Image image, int width, int height) {
		PixelReader pixelReader = image.getPixelReader();
		TemporalColor imageLaplacian[][] = new TemporalColor[height][width];
		int offsetI = 1;
		int offsetJ = 1;

		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				imageLaplacian[row][column] = maskApplicationService.applyMask(image, row, column, pixelReader, LAPLACIAN_OPERATOR, offsetI,
						offsetJ);
			}
		}
		return imageLaplacian;
	}

	@Override
	public TemporalColor[][] calculateConvolution(int[][] gausseanLaplacianOperator, Image image, int width, int height) {
		PixelReader pixelReader = image.getPixelReader();
		TemporalColor imageLaplacian[][] = new TemporalColor[height][width];
		int offsetI = 1;
		int offsetJ = 1;

		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				imageLaplacian[row][column] = maskApplicationService
						.applyMask(image, row, column, pixelReader, gausseanLaplacianOperator, offsetI, offsetJ);
			}
		}
		return imageLaplacian;
	}

}