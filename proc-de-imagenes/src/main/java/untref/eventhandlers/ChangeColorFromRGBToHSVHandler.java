package untref.eventhandlers;

import java.awt.Color;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import untref.service.ImageEditionServiceImpl;
import untref.service.ImageGetColorRGBImpl;

public class ChangeColorFromRGBToHSVHandler implements EventHandler<ActionEvent> {
	private ImageView imageViewResult;
	private ImageGetColorRGBImpl imageGetColorRGBImpl;

	public ChangeColorFromRGBToHSVHandler(  ImageView imageViewResult, ImageGetColorRGBImpl imageGetColorRGBImpl,
			ImageEditionServiceImpl imageEditionServiceImpl) {
		this.imageViewResult = imageViewResult;
		this.imageGetColorRGBImpl = imageGetColorRGBImpl;
	}

	@Override
	public void handle(ActionEvent arg0) {
		
		//////aca codigooooooo//////
		ImageView a=new ImageView("1.jpg");
		
	this.imageViewResult.setImage(a.getImage());
int b=0;
	
	}
}