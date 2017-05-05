package untref.service;

import javafx.scene.image.Image;
import untref.domain.edgedetectionoperators.EdgeDetectionOperator;

public class EdgeDetectionServiceImpl implements EdgeDetectionService {

	@Override
	public Image detectEdge(Image image, EdgeDetectionOperator edgeDetectionOperator, Integer limitThresholdForGradientMagnitude) {
		return edgeDetectionOperator.detectEdge(image, limitThresholdForGradientMagnitude);
	}
}