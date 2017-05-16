package untref.domain.edgedetectionoperators.secondderivative.detectors;

import javafx.scene.image.WritableImage;
import untref.domain.TemporalColor;

public interface EdgeDetector {

	WritableImage detectEdges(TemporalColor[][] imageLaplacian, int width, int height);
}