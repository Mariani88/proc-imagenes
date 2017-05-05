package untref.domain.utils;

public class ImageValuesTransformer {

	private static final int TOTAL_COLORS = 255;

	public static int toInt(double value) {
		return (int) value;
	}

	public static double toDouble(int value) {
		return (double) value;
	}

	public static int toRGBScale(double grayValue){
		return toInt(grayValue* TOTAL_COLORS);
	}
}