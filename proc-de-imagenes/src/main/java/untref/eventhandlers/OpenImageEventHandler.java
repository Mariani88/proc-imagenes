package untref.eventhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import untref.service.ImageIOService;

import java.util.Optional;
import java.util.function.Consumer;

public class OpenImageEventHandler implements EventHandler<ActionEvent> {

	private FileChooser fileChooser;
	private ImageView imageView;
	private ImageIOService imageIOService;

	public OpenImageEventHandler(FileChooser fileChooser, ImageView imageView, ImageIOService imageIOService) {
		this.fileChooser = fileChooser;
		this.imageView = imageView;
		this.imageIOService = imageIOService;
	}

	public void handle(ActionEvent event) {
		Optional<Image> image = imageIOService.openImage(fileChooser);
		image.ifPresent(new Consumer<Image>() {
			@Override
			public void accept(Image image1) {


				if ( image1.getHeight() < 500 & image1.getWidth() < 500) {
					imageView.setFitHeight(image1.getHeight());
					imageView.setFitWidth(image1.getWidth());
				}else{
					imageView.setFitHeight(500);
					imageView.setFitWidth(500);
				}
				imageView.setImage(image1);

			}
		});
	}
}