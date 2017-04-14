package untref.service.transformationoutrang;

public class InferiorLimitTransformation implements Transformation {
	@Override
	public int apply(int grayScale) {
		return Math.max(0, grayScale);
	}
}
