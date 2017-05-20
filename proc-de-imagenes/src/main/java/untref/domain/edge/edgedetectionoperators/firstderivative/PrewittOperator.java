package untref.domain.edge.edgedetectionoperators.firstderivative;

import javafx.scene.image.Image;
import untref.service.MaskApplicationService;
import untref.service.MaskApplicationServiceImpl;

public class PrewittOperator implements EdgeDetectionFirstDerivativeOperator {

	private static final int[][] PREWITH_OPERATOR_Fx = { { -1, -1, -1 }, { 0, 0, 0 }, { 1, 1, 1 } };
	private static final int[][] PREWITH_OPERATOR_Fy = { { -1, 0, 1 }, { -1, 0, 1 }, { -1, 0, 1 } };
	private final MaskApplicationService maskApplicationService;
	private static final int OFFSET_I = 1;
	private static final int OFFSET_J = 1;

	public PrewittOperator() {
		maskApplicationService = new MaskApplicationServiceImpl();
	}

	@Override
	public Image detectEdge(Image image, Integer limitThresholdForGradientMagnitude) {
		return new EdgeDetectionAplicator(maskApplicationService)
				.applyEdgeDetectionOperator(image, PREWITH_OPERATOR_Fx, PREWITH_OPERATOR_Fy, limitThresholdForGradientMagnitude, OFFSET_I, OFFSET_J);
	}

	public Image detectHorizontalEdge(Image image, Integer limitThresholdForGradientMagnitude) {
		return new EdgeDetectionAplicator(maskApplicationService)
				.applyEdgeDetectionForHorizontalEdge(image, PREWITH_OPERATOR_Fx, limitThresholdForGradientMagnitude, OFFSET_I, OFFSET_J);
	}

	public Image detectVerticalEdge(Image image, Integer limitThresholdForGradientMagnitude) {
		return new EdgeDetectionAplicator(maskApplicationService)
				.applyEdgeDetectionForVerticalEdge(image, PREWITH_OPERATOR_Fy, limitThresholdForGradientMagnitude, OFFSET_I, OFFSET_J);
	}
}