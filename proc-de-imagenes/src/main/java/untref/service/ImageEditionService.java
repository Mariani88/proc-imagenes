package untref.service;

import javafx.scene.image.Image;

public interface ImageEditionService {
	Image modifyPixelValue(Image image, String aX, String aY, String pixelValue);

	double[] RGBtoHSV(double r, double g, double b);
}
