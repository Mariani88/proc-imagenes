package untref.domain.edgedetectionoperators;

import javafx.scene.image.Image;

public class PrewittOperator implements EdgeDetectionOperator {

	private  static final int[][] PREWITH_OPERATOR = { { -1, -1, -1 }, { 0, 0, 0 }, { 1, 1, 1 } };

	@Override
	public Image detectEdge(Image image, Integer limitThresholdForGradientMagnitude) {
		return null;
	}
}