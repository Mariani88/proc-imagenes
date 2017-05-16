package untref.service;

import javafx.scene.image.Image;
import untref.domain.TemporalColor;
import untref.domain.edgedetectionoperators.firstderivative.EdgeDetectionFirstDerivativeOperator;
import untref.domain.edgedetectionoperators.secondderivative.detectors.EdgeDetector;

import static untref.domain.utils.ImageValuesTransformer.toInt;

public class EdgeDetectionServiceImpl implements EdgeDetectionService {

	private static final int[][] GAUSSEAN_LAPLACIAN_OPERATOR = { { 0, 0, -1, 0, 0 }, { 0, -1, -2, -1, 0 }, { -1, -2, 16, -1, -2 },
			{ 0, -1, -2, -1, 0 }, { 0, 0, -1, 0, 0 } };
	private final ImageDerivativeService imageDerivativeService;
	private final ImageConvolutionService imageConvolutionService;

	public EdgeDetectionServiceImpl() {
		MaskApplicationService maskApplicationService = new MaskApplicationServiceImpl();
		imageDerivativeService = new ImageDerivativeServiceImpl(maskApplicationService);
		imageConvolutionService = new ImageConvolutionServiceImpl(maskApplicationService);
	}

	@Override
	public Image detectEdgeWithFirstDerivative(Image image, EdgeDetectionFirstDerivativeOperator firstDerivativeOperator,
			Integer limitThresholdForGradientMagnitude) {
		return firstDerivativeOperator.detectEdge(image, limitThresholdForGradientMagnitude);
	}

	@Override
	public Image detectEdgeWithLaplacian(Image image, EdgeDetector edgeDetector) {
		int width = toInt(image.getWidth());
		int height = toInt(image.getHeight());
		TemporalColor[][] imageLaplacian = imageDerivativeService.calculateLaplacian(image, width, height);
		return edgeDetector.detectEdges(imageLaplacian, width, height);
	}

	@Override
	public Image detectEdgeWithMarrHildreth(Image image, EdgeDetector edgeDetector) {
		int width = toInt(image.getWidth());
		int height = toInt(image.getHeight());
		TemporalColor[][] convolution = imageConvolutionService.calculateConvolution(GAUSSEAN_LAPLACIAN_OPERATOR, image, width, height);
		return edgeDetector.detectEdges(convolution, width, height);
	}
}