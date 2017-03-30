package untref.service;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class CreationImageServiceImpl implements CreationImageService{

	@Override
	public Image createBinaryImageWithCenterQuadrate(int width, int height){
		WritableImage writableImage = new WritableImage(width, height);
		PixelWriter pixelWriter = writableImage.getPixelWriter();
		createBlackImage(width, height, pixelWriter);
		createCenterQuadrate(width, height, pixelWriter);
		return writableImage;
	}

	private void createBlackImage(int width, int height, PixelWriter pixelWriter) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; i < height; i++) {
				pixelWriter.setArgb(i, j, 0);
			}
		}
	}

	private void createCenterQuadrate(int width, int height, PixelWriter pixelWriter) {
		for(int i = width/3; i <= width*2/3; i++){
			for(int j = height/3; j <= height*2/3; j++){
				pixelWriter.setArgb(i, j, -0xFFFFFF);
			}
		}
	}
}