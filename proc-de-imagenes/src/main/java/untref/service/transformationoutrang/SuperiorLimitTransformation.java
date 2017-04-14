package untref.service.transformationoutrang;


public class SuperiorLimitTransformation implements Transformation{

	private static final int LIMIT_SCALE = 255;

	@Override
	public int apply(int grayScale) {
		int transformed = grayScale;

		if (LIMIT_SCALE < grayScale) {
			transformed = grayScale / 2;
		}

		return transformed;
	}
}