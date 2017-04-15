package untref.service.arithmeticoperations;

import javafx.scene.paint.Color;
import untref.service.transformationoutrang.InferiorLimitTransformation;

public class ArithmeticOperationBetweenImagesSubtract implements ArithmeticOperationBetweenImages {

	private static final int LIMIT_SCALE = 255;
	private InferiorLimitTransformation inferiorLimitTransformation;

	public ArithmeticOperationBetweenImagesSubtract() {
		this.inferiorLimitTransformation = new InferiorLimitTransformation();
	}

	@Override
	public Color calculateColor(Color sumsColor1, Color sumsColor2) {
		int red = (int) ((sumsColor1.getRed() - sumsColor2.getRed()) * LIMIT_SCALE);
		int blue = (int) ((sumsColor1.getBlue() - sumsColor2.getBlue()) * LIMIT_SCALE);
		int green = (int) ((sumsColor1.getGreen() - sumsColor2.getGreen()) * LIMIT_SCALE);
		red = inferiorLimitTransformation.apply(red);
		blue = inferiorLimitTransformation.apply(blue);
		green = inferiorLimitTransformation.apply(green);
		return Color.rgb(red, green, blue);
	}
}