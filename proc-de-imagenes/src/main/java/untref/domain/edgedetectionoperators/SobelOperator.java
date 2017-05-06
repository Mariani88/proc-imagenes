package untref.domain.edgedetectionoperators;

import javafx.scene.image.Image;
import untref.service.MaskApplicationService;
import untref.service.MaskApplicationServiceImpl;

public class SobelOperator implements EdgeDetectionOperator {

	private static final int[][] SOBEL_OPERATOR_Fx = { { -1, -2, -1 }, { 0, 0, 0 }, { 1, 2, 1 } };
	private static final int[][] SOBEL_OPERATOR_Fy = { { -1, 0, 1 }, { -2, 0, 2 }, { -1, 0, 1 } };
	private final MaskApplicationService maskApplicationService;

	public SobelOperator() {
		maskApplicationService = new MaskApplicationServiceImpl();
	}

	@Override
	public Image detectEdge(Image image, Integer limitThresholdForGradientMagnitude) {
		int offsetI = 1;
		int offsetJ = 1;
		return new EdgeDetectionAplicator(maskApplicationService)
				.applyEdgeDetectionOperator(image, SOBEL_OPERATOR_Fx, SOBEL_OPERATOR_Fy, limitThresholdForGradientMagnitude, offsetI, offsetJ);
	}
}