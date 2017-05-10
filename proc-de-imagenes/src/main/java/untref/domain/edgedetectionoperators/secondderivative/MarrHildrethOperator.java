package untref.domain.edgedetectionoperators.secondderivative;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import untref.domain.TemporalColor;
import untref.service.ImageDerivativeServiceImpl;
import untref.service.MaskApplicationService;
import untref.service.MaskApplicationServiceImpl;

import static untref.domain.utils.ImageValuesTransformer.toInt;

public class MarrHildrethOperator implements EdgeDetectionSecondDerivativeOperator {

	private static final int[][] GAUSSEAN_LAPLACIAN_OPERATOR = { { 0, 0, -1, 0, 0 }, { 0, -1, -2, -1, 0 }, { -1, -2, 16, -1, -2 },
			{ 0, -1, -2, -1, 0 }, { 0, 0, -1, 0, 0 } };
	private final MaskApplicationService maskApplicationService;

	public MarrHildrethOperator() {
		maskApplicationService = new MaskApplicationServiceImpl();
	}

	@Override
	public Image detectEdges(Image image) {
		int width = toInt(image.getWidth());
		int height = toInt(image.getHeight());
		TemporalColor[][] convolution = new ImageDerivativeServiceImpl(maskApplicationService).calculateConvolution
				(GAUSSEAN_LAPLACIAN_OPERATOR, image, width,  height);
		WritableImage imageWithEdges = new WritableImage(width, height);
		new CrossByZeroDetector().detectEdges(imageWithEdges, convolution, width, height);
		return imageWithEdges;
	}
}