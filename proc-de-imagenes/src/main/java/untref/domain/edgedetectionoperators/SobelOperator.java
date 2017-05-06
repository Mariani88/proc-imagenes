package untref.domain.edgedetectionoperators;

import javafx.scene.image.Image;

public class SobelOperator implements EdgeDetectionOperator {

	private static final int[][] SOBEL_OPERATOR_Fx = { { -1, -2, -1 }, { 0, 0, 0 }, { 1, 2, 1 } };
	private static final int[][] SOBEL_OPERATOR_Fy = { { -1, 0, 1 }, { -2, 0, 2 }, { -1, 0, 1 } };

	@Override
	public Image detectEdge(Image image, Integer limitThresholdForGradientMagnitude) {
		int offsetI = 1;
		int offsetJ = 1;
		return new EdgeDetectionAplicator()
				.applyEdgeDetectionOperator(image, SOBEL_OPERATOR_Fx, SOBEL_OPERATOR_Fy, limitThresholdForGradientMagnitude, offsetI, offsetJ);
	}
}