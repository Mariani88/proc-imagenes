package untref.domain.edgedetectionoperators;

import javafx.scene.image.Image;

public interface EdgeDetectionOperator {

	Image detectEdge(Image image, Integer limitThresholdForGradientMagnitude);
}