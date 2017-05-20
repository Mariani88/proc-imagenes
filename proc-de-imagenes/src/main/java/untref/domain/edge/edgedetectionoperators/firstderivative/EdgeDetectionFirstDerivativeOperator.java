package untref.domain.edge.edgedetectionoperators.firstderivative;

import javafx.scene.image.Image;

public interface EdgeDetectionFirstDerivativeOperator {

	Image detectEdge(Image image, Integer limitThresholdForGradientMagnitude);
}