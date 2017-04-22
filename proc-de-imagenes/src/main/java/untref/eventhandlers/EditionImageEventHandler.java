package untref.eventhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import untref.controllers.nodeutils.ImageSetter;

import java.util.function.Supplier;

public class EditionImageEventHandler implements EventHandler<ActionEvent>{

	private ImageView imageViewResult;
	private Supplier<Image> editionImage;

	public EditionImageEventHandler(ImageView imageViewResult, Supplier<Image> editionImage) {
		this.imageViewResult = imageViewResult;
		this.editionImage = editionImage;
	}

	@Override
	public void handle(ActionEvent event) {
		ImageSetter.set(imageViewResult, editionImage.get());
	}
}