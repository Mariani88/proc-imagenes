package untref.domain.edgedetectionoperators;

import javafx.scene.image.Image;

public class RobertOperator implements EdgeDetectionOperator {

	private static final int[][] ROBERT_OPERATOR_Fx = { { 1, 0 }, { 0, -1 } };
	private static final int[][] ROBERT_OPERATOR_Fy = { { 0, 1 }, { -1, 0 } };

	@Override
	public Image detectEdge(Image image, Integer limitThresholdForGradientMagnitude) {
		int offsetI = 0;
		int offsetJ = 0;
		return new EdgeDetectionAplicator()
				.applyEdgeDetectionOperator(image, ROBERT_OPERATOR_Fx, ROBERT_OPERATOR_Fy, limitThresholdForGradientMagnitude, offsetI, offsetJ);
	}
}