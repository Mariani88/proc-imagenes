package untref.domain.edge.detectors;

import javafx.scene.image.Image;
import untref.domain.edge.edgedetectionoperators.firstderivative.PrewittOperator;
import untref.service.EdgeDetectionService;

public class VerticalEdge implements EdgeDetector {

	private final EdgeDetectionService edgeDetectionService;
	private final PrewittOperator prewittOperator;

	public VerticalEdge(EdgeDetectionService edgeDetectionService, PrewittOperator prewittOperator) {
		this.edgeDetectionService = edgeDetectionService;
		this.prewittOperator = prewittOperator;
	}

	@Override
	public Image detectEdge(Image image, Integer limitThreshold) {
		return this.edgeDetectionService.detectVerticalEdgeWithFirstDerivative(image, prewittOperator, limitThreshold);
	}
}