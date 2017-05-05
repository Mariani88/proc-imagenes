package untref.domain.edgedetectionoperators;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import untref.domain.TemporalColor;

import static untref.domain.utils.ImageValuesTransformer.toInt;
import static untref.domain.utils.ImageValuesTransformer.toRGBScale;
import static untref.utils.ImageValidator.existPosition;

public class RobertOperator implements EdgeDetectionOperator {

	private static final int[][] ROBERT_OPERATOR_Fx = { { 1, 0 }, { 0, -1 } };
	private static final int[][] ROBERT_OPERATOR_Fy = { { 0, 1 }, { -1, 0 } };

	@Override
	public Image detectEdge(Image image, Integer limitThresholdForGradientMagnitude) {
		int width = toInt(image.getWidth());
		int height = toInt(image.getHeight());
		WritableImage imageWithEdges = new WritableImage(width, height);
		PixelReader pixelReader = image.getPixelReader();
		PixelWriter pixelWriter = imageWithEdges.getPixelWriter();

		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				TemporalColor colorToTransform = getPositionColor(row, column, image, pixelReader);
				TemporalColor colorRightPosition = getPositionColor(row, column + 1, image, pixelReader);
				TemporalColor colorDownPosition = getPositionColor(row + 1, column, image, pixelReader);
				TemporalColor colorDownRightPosition = getPositionColor(row + 1, column + 1, image, pixelReader);
				Color resultColor = applyDetection(colorToTransform, colorRightPosition, colorDownPosition, colorDownRightPosition,
						limitThresholdForGradientMagnitude);
				pixelWriter.setColor(column, row, resultColor);
			}
		}

		return imageWithEdges;
	}

	private Color applyDetection(TemporalColor colorToTransform, TemporalColor colorRightPosition, TemporalColor colorDownPosition,
			TemporalColor colorDownRightPosition, Integer limitThresholdForGradientMagnitude) {
		int red = applyOperator(colorToTransform.getRed(), colorRightPosition.getRed(), colorDownPosition.getRed(), colorDownRightPosition.getRed()
				, limitThresholdForGradientMagnitude);
		int green = applyOperator(colorToTransform.getGreen(), colorRightPosition.getGreen(), colorDownPosition.getGreen(),
				colorDownRightPosition.getGreen(), limitThresholdForGradientMagnitude);
		int blue = applyOperator(colorToTransform.getBlue(), colorRightPosition.getBlue(), colorDownPosition.getBlue(),
				colorDownRightPosition.getBlue(), limitThresholdForGradientMagnitude);
		return Color.rgb(red, green, blue);
	}

	private int applyOperator(int grayToTransform, int grayRightPosition, int grayDownPosition, int grayDownRightPosition,
			Integer limitThresholdForGradientMagnitude) {
		int fx = grayToTransform * ROBERT_OPERATOR_Fx[0][0] + grayRightPosition * ROBERT_OPERATOR_Fx[0][1] + grayDownPosition * ROBERT_OPERATOR_Fx[1][0]
				+ grayDownRightPosition * ROBERT_OPERATOR_Fx[1][1];
		int fy = grayToTransform * ROBERT_OPERATOR_Fy[0][0] + grayRightPosition * ROBERT_OPERATOR_Fy[0][1] + grayDownPosition * ROBERT_OPERATOR_Fy[1][0]
				+ grayDownRightPosition * ROBERT_OPERATOR_Fy[1][1];
		double gradientMagnitudeForPixel = Math.sqrt(Math.pow(fx, 2) + Math.pow(fy, 2));

		if (gradientMagnitudeForPixel <= limitThresholdForGradientMagnitude) {
			return 0;
		} else {
			return 255;
		}

	}

	private TemporalColor getPositionColor(int row, int column, Image image, PixelReader pixelReader) {
		TemporalColor temporalColor = new TemporalColor(0, 0, 0);

		if (existPosition(image, row, column)) {
			Color color = pixelReader.getColor(column, row);
			temporalColor = new TemporalColor(toRGBScale(color.getRed()), toRGBScale(color.getGreen()), toRGBScale(color.getBlue()));
		}

		return temporalColor;
	}
}