package untref.controllers.nodeutils.settertype;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import untref.controllers.nodeutils.ImageSetter;

public class FullSetter implements SetterType{

	@Override
	public void setImage(ImageView imageView, Image image) {
		ImageSetter.setWithImageSize(imageView, image);
	}
}
