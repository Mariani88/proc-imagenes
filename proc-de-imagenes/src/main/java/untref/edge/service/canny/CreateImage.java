package untref.edge.service.canny;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class CreateImage {
	Image createImageOut(int[][] matrixEdge, int width, int height) {
		WritableImage imageOut = new WritableImage(width, height);
		PixelWriter pixelWriter = imageOut.getPixelWriter();

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (matrixEdge[i][j] > 0) {
					pixelWriter.setColor(i, j, Color.RED);
				} else {
					pixelWriter.setColor(i, j, Color.BLACK);
				}
			}
		}
		return imageOut;
	}

}
