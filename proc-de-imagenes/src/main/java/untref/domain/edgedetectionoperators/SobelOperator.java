package untref.domain.edgedetectionoperators;

import javafx.scene.image.Image;

public class SobelOperator implements EdgeDetectionOperator{

	private  static  final int[][] SOBEL_OPERATOR = { { -1, -2, -1 }, { 0, 0, 0 }, { 1, 2, 1 } };

	@Override
	public Image detectEdge(Image image, Integer limitThresholdForGradientMagnitude) {
		return null;
	}
}