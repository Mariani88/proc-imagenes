package untref.domain.edgedetectionoperators;

import javafx.scene.image.Image;
import untref.service.MaskApplicationService;
import untref.service.MaskApplicationServiceImpl;

public class PrewittOperator implements EdgeDetectionOperator {

	private static final int[][] PREWITH_OPERATOR_Fx = { { -1, -1, -1 }, { 0, 0, 0 }, { 1, 1, 1 } };
	private static final int[][] PREWITH_OPERATOR_Fy = { { -1, 0, 1 }, { -1, 0, 1 }, { -1, 0, 1 } };
	private final MaskApplicationService maskApplicationService;

	public PrewittOperator() {
		maskApplicationService = new MaskApplicationServiceImpl();
	}

	@Override
	public Image detectEdge(Image image, Integer limitThresholdForGradientMagnitude) {
		int offsetI = 1;
		int offsetJ = 1;
		return new EdgeDetectionAplicator(maskApplicationService)
				.applyEdgeDetectionOperator(image, PREWITH_OPERATOR_Fx, PREWITH_OPERATOR_Fy, limitThresholdForGradientMagnitude, offsetI, offsetJ);
	}
}