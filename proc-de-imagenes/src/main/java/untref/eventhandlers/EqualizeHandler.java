package untref.eventhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import untref.service.EcualizeService;
import untref.service.HistogramService;
import untref.service.HistogramServiceImpl;

public class EqualizeHandler implements EventHandler<ActionEvent> {

	ImageView imageView;
	ImageView imageResultView;
	public EqualizeHandler(ImageView imageView,ImageView imageResultView) {

		this.imageView = imageView;
		this.imageResultView=imageResultView;
	}

	@Override
	public void handle(ActionEvent arg0) {
		HistogramService histogram = new HistogramServiceImpl(this.imageView.getImage());

		EcualizeService ecualizeImage = new EcualizeService();

		
		this.imageResultView.setImage(ecualizeImage.ecualizerHistogram(histogram.getArrayHistogram(), this.imageView.getImage()));

	}

}
