package untref.eventhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import untref.difusor.service.ApplyBroadcastIsotropic;
import untref.difusor.service.StageDiffusionIsotropic;

public class IsotropicEventHandler implements EventHandler<ActionEvent> {
	ImageView imageView;
	ImageView imageResultView;

	public IsotropicEventHandler(ImageView imageView, ImageView imageResultView) {
		this.imageResultView = imageResultView;
		this.imageView = imageView;
	}

	@Override
	public void handle(ActionEvent event) {
		StageDiffusionIsotropic stage=new StageDiffusionIsotropic();
		stage.start(imageView.getImage(), imageResultView);
		/*
		ApplyBroadcast isotropic= new ApplyBroadcast();
		this.imageResultView.setImage(isotropic.difussion(, stage.getRepeat(), true, stage.getSigma()));
	*/	
	}

}
