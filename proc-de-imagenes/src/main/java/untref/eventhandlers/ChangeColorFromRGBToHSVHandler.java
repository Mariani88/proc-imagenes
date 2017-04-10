package untref.eventhandlers;

import java.awt.image.BufferedImage;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import untref.service.ImageEditionServiceImpl;
import untref.service.ImageGetColorRGBImpl;

public class ChangeColorFromRGBToHSVHandler implements EventHandler<ActionEvent> {
	private ImageView imageViewResult;
	private ImageGetColorRGBImpl imageGetColorRGBImpl;
	private ImageEditionServiceImpl imageEditionServiceImpl;
	private ImageView imageView;

	public ChangeColorFromRGBToHSVHandler(ImageView imageView, ImageView imageViewResult,
			ImageGetColorRGBImpl imageGetColorRGBImpl, ImageEditionServiceImpl imageEditionServiceImpl) {
		this.imageViewResult = imageViewResult;
		this.imageGetColorRGBImpl = imageGetColorRGBImpl;
		this.imageEditionServiceImpl = imageEditionServiceImpl;
		this.imageView = imageView;
	}

	@Override
	public void handle(ActionEvent arg0) {

		double r = imageGetColorRGBImpl.getTotalValueChannelR();
		double g = imageGetColorRGBImpl.getTotalValueChannelG();
		double b = imageGetColorRGBImpl.getTotalValueChannelB();

		double[] hsv = imageEditionServiceImpl.RGBtoHSV(r, g, b);

		Double width = imageView.getImage().getWidth();
		Double height = imageView.getImage().getHeight();

		WritableImage writableImage = new WritableImage(width.intValue(), height.intValue());
		PixelWriter pixelWriter = writableImage.getPixelWriter();
		Color colorRGB;

		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {

				colorRGB = Color.rgb(column, row, 0);
				pixelWriter.setColor(column, row, colorRGB);
				
			}

		}
		
		imageViewResult.setImage(writableImage);

	}
}