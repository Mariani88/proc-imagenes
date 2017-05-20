package untref.domain.edge.detectors;

import javafx.scene.image.Image;
import untref.domain.edge.edgedetectionoperators.firstderivative.EdgeDetectionFirstDerivativeOperator;
import untref.service.EdgeDetectionService;

public class AllEdges implements EdgeDetector {

	private final EdgeDetectionService edgeDetectionService;
	private final EdgeDetectionFirstDerivativeOperator edgeDetectionOperator;

	public AllEdges(EdgeDetectionService edgeDetectionService, EdgeDetectionFirstDerivativeOperator edgeDetectionOperator) {
		this.edgeDetectionService = edgeDetectionService;
		this.edgeDetectionOperator = edgeDetectionOperator;
	}

	@Override
	public Image detectEdge(Image image, Integer limitThresholdValue) {
		return edgeDetectionService.detectEdgeWithFirstDerivative(image, edgeDetectionOperator, limitThresholdValue);
	}
}