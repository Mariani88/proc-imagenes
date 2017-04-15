package untref.service.transformationoutrang;


public class SuperiorLimitTransformation implements Transformation{

	private static final int LIMIT_SCALE = 255;

	//TODO testear. esto va achicando recursivamente hasta que el valor este en el rango deseado
	@Override
	public int apply(int grayScale) {
		int transformed = grayScale;

		if (LIMIT_SCALE < grayScale) {
			transformed = grayScale / 2;
			transformed= apply(transformed);
		}

		return transformed;
	}
}