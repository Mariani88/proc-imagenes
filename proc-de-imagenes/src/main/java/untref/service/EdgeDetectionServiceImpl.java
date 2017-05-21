package untref.service;

import javafx.scene.image.Image;
import untref.domain.TemporalColor;
import untref.domain.edge.edgedetectionoperators.firstderivative.EdgeDetectionFirstDerivativeOperator;
import untref.domain.edge.edgedetectionoperators.firstderivative.PrewittOperator;
import untref.domain.edge.edgedetectionoperators.secondderivative.detectors.EdgeDetector;

import static untref.domain.utils.ImageValuesTransformer.toInt;

public class EdgeDetectionServiceImpl implements EdgeDetectionService {

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
	public Image detectHorizontalEdgeWithFirstDerivative(Image image, PrewittOperator prewittOperator, Integer limitThresholdForGradientMagnitude) {
		return prewittOperator.detectHorizontalEdge(image, limitThresholdForGradientMagnitude);
	}

	@Override
	public Image detectVerticalEdgeWithFirstDerivative(Image image, PrewittOperator prewittOperator, Integer limitThreshold) {
		return prewittOperator.detectVerticalEdge(image, limitThreshold);
	}

	@Override
	public Image detectEdgeWithLaplacian(Image image, EdgeDetector edgeDetector) {
		int width = toInt(image.getWidth());
		int height = toInt(image.getHeight());
		TemporalColor[][] imageLaplacian = imageDerivativeService.calculateLaplacian(image, width, height);
		return edgeDetector.detectEdges(imageLaplacian, width, height);
	}

	@Override
	public Image detectEdgeWithMarrHildreth(Image image, EdgeDetector edgeDetector, double sigma) {
		int width = toInt(image.getWidth());
		int height = toInt(image.getHeight());
		double[][] gasseanLaplacianOperator = calculateMarrHildrethOperator(sigma);
		TemporalColor[][] convolution = imageConvolutionService.calculateConvolution(gasseanLaplacianOperator, image, width, height);
		return edgeDetector.detectEdges(convolution, width, height);
	}

	private double[][] calculateMarrHildrethOperator(double sigma) {
		int dimension = toInt(2 * sigma + 1);
		double marrHildrethOperator[][] = new double[dimension][dimension];
		int offsetI = dimension / 2;
		int offsetJ = dimension / 2;

		for (int row = 0; row < dimension; row++) {
			for (int column = 0; column < dimension; column++) {
				marrHildrethOperator[row][column] = calculateValue(row - offsetI, column - offsetJ, sigma);
			}
		}

		return marrHildrethOperator;
	}

	private double calculateValue(int row, int column, double sigma) {
		double r2 = Math.pow(row, 2) + Math.pow(column, 2);
		double sigmaQuadrate = Math.pow(sigma, 2);
		double coefficient = -((r2 - sigmaQuadrate) / Math.pow(sigma, 4));
		double exponent = -r2 / 2 * sigmaQuadrate;
		return coefficient * Math.exp(exponent);
	}

}