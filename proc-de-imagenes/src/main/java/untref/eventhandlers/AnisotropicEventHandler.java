package untref.eventhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import untref.difusor.service.StageDiffusionAnisotropic;
import untref.difusor.service.StageDiffusionIsotropic;

public class AnisotropicEventHandler implements EventHandler<ActionEvent> {
	ImageView imageView;
	ImageView imageResultView;

	public AnisotropicEventHandler(ImageView imageView, ImageView imageResultView) {
		this.imageResultView = imageResultView;
		this.imageView = imageView;
	}

	@Override
	public void handle(ActionEvent arg0) {
		StageDiffusionAnisotropic stage=new StageDiffusionAnisotropic();
		stage.start(imageView.getImage(), imageResultView);

	}

}
