package untref.eventhandlers.activecontours;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import untref.controllers.PixelPaneController;
import untref.controllers.nodeutils.ImageSetter;
import untref.domain.activecontours.Contour;
import untref.service.activecontours.ActiveContoursService;

import static untref.domain.utils.ImageValuesTransformer.toInt;

public class InitialContourEventHandler implements EventHandler<MouseEvent> {

	private PixelPaneController firstPixelPaneController;
	private PixelPaneController secondPixelPaneController;
	private Contour[] contour;
	private ActiveContoursService activeContoursService;
	private ImageView imageView;
	private Image imageToShow;

	public InitialContourEventHandler(PixelPaneController firstPixelPaneController, PixelPaneController secondPixelPaneController, Contour[] contour,
			ActiveContoursService activeContoursService, ImageView imageView, Image imageToShow) {
		this.firstPixelPaneController = firstPixelPaneController;
		this.secondPixelPaneController = secondPixelPaneController;
		this.contour = contour;
		this.activeContoursService = activeContoursService;
		this.imageView = imageView;
		this.imageToShow = imageToShow;
	}

	@Override
	public void handle(MouseEvent event) {
		int x = toInt(event.getX());
		int y = toInt(event.getY());

		if (!firstPixelPaneController.setedValues()) {
			firstPixelPaneController.setValues(x, y);
		} else if (!secondPixelPaneController.setedValues()) {
			secondPixelPaneController.setValues(x, y);
			contour[0] = activeContoursService
					.initializeActiveContours(imageView.getImage(), firstPixelPaneController.getPosition(), secondPixelPaneController.getPosition());
			ImageSetter.setWithImageSize(imageView, contour[0].getImageWithContour());
		} else {
			firstPixelPaneController.clearValues();
			secondPixelPaneController.clearValues();
			contour[0] = null;
			ImageSetter.setWithImageSize(imageView, imageToShow);
		}
	}
}