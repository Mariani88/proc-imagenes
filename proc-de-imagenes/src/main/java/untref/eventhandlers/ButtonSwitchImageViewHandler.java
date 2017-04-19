package untref.eventhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;

public class ButtonSwitchImageViewHandler implements EventHandler<ActionEvent> {
	ImageView imageView;
	ImageView imageResultView;
	
	
	public ButtonSwitchImageViewHandler(ImageView imageView,ImageView imageResultView) {
		this.imageResultView=imageResultView;
		this.imageView=imageView;
		
	}


	@Override
	public void handle(ActionEvent arg0) {
		ImageView tempView = new ImageView();
		
		tempView.setImage(imageView.getImage());
		imageView.setImage(this.imageResultView.getImage());
		this.imageResultView.setImage(tempView.getImage());
		
	}

}
