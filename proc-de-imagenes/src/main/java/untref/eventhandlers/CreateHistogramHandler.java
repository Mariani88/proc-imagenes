package untref.eventhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import untref.controllers.DrawingSelect;
import untref.service.EcualizeService;
import untref.service.HistogramService;
import untref.service.HistogramServiceImpl;

public class CreateHistogramHandler implements EventHandler<ActionEvent> {

	ImageView imageView;

	public CreateHistogramHandler(ImageView imageView) {

		this.imageView = imageView;

	}

	@Override
	public void handle(ActionEvent arg0) {

		HistogramService histogram = new HistogramServiceImpl(imageView.getImage());
		histogram.BarChartDraw();

	}

}
