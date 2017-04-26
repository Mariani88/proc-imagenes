package untref.eventhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import untref.service.CreateThresholdService;
import untref.service.HistogramService;
import untref.service.HistogramServiceImpl;

public class CreateThresholdHandler  {

	ImageView imageView;
	ImageView imageResultView;
	int value;

	public CreateThresholdHandler(ImageView imageView,ImageView imageResultView, int value) {

		this.imageView = imageView;
		this.imageResultView=imageResultView;
		this.value=value;

	}

	
	public void handle() {

		CreateThresholdService threshold=new CreateThresholdService();
		this.imageResultView.setImage(threshold.getImageThreshold(imageView.getImage(),value));

	}}
