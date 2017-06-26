package untref.domain.edge.edgedetectionoperators.firstderivative;

import javafx.scene.image.Image;
import untref.service.MaskApplicationService;
import untref.service.MaskApplicationServiceImpl;

import static untref.domain.edge.edgedetectionoperators.firstderivative.PrewithUtils.*;

public class PrewittOperator implements EdgeDetectionFirstDerivativeOperator {

	private final MaskApplicationService maskApplicationService;

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