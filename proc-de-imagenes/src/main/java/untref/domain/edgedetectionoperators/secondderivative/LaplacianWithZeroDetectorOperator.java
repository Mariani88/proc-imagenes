package untref.domain.edgedetectionoperators.secondderivative;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import untref.domain.TemporalColor;
import untref.service.ImageDerivativeServiceImpl;
import untref.service.MaskApplicationService;
import untref.service.MaskApplicationServiceImpl;

import static untref.domain.utils.ImageValuesTransformer.getOrEmpty;
import static untref.domain.utils.ImageValuesTransformer.toInt;

public class LaplacianWithZeroDetectorOperator implements EdgeDetectionSecondDerivativeOperator {

	private final MaskApplicationService maskApplicationService;
	private final ImageDerivativeServiceImpl imageDerivativeService;

	public LaplacianWithZeroDetectorOperator() {
		maskApplicationService = new MaskApplicationServiceImpl();
		imageDerivativeService = new ImageDerivativeServiceImpl(maskApplicationService);
	}

	@Override
	public Image detectEdges(Image image) {
		int width = toInt(image.getWidth());
		int height = toInt(image.getHeight());
		TemporalColor[][] imageLaplacian = imageDerivativeService.calculateLaplacian(image, width, height);
		WritableImage imageWithEdges = new WritableImage(width, height);
		new CrossByZeroDetector().detectEdges(imageWithEdges, imageLaplacian, width, height);
		return imageWithEdges;
	}
}