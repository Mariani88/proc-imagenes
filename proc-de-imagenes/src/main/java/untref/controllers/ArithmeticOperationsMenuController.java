package untref.controllers;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import untref.eventhandlers.CreationSpecificImageHandler;
import untref.eventhandlers.IntroduceScalarEventHandler;
import untref.factory.FileImageChooserFactory;
import untref.service.CreationImageService;
import untref.service.ImageIOService;

public class ArithmeticOperationsMenuController {

	private CreationImageService creationImageService;
	private ImageIOService imageIOService;
	private FileImageChooserFactory fileImageChooserFactory;

	public ArithmeticOperationsMenuController(CreationImageService creationImageService, ImageIOService imageIOService,
			FileImageChooserFactory fileImageChooserFactory) {
		this.creationImageService = creationImageService;
		this.imageIOService = imageIOService;
		this.fileImageChooserFactory = fileImageChooserFactory;
	}

	public Menu createArithmeticOperationsBetweenImages(ImageView imageView, ImageView imageViewResult) {
		Menu arithmeticOperationsBetweenImages = new Menu("arithmetic operations between images");
		MenuItem plusImages = new MenuItem("plus images");
		plusImages.setOnAction(new CreationSpecificImageHandler(imageIOService, fileImageChooserFactory,
				() -> creationImageService.plusImages(imageView.getImage(), imageViewResult.getImage())));
		MenuItem subtractImages = new MenuItem("subtract images");
		subtractImages.setOnAction(new CreationSpecificImageHandler(imageIOService, fileImageChooserFactory,
				() -> creationImageService.subtractImages(imageView.getImage(), imageViewResult.getImage())));
		MenuItem multiplyImages = new MenuItem("multiply images");
		multiplyImages.setOnAction(new CreationSpecificImageHandler(imageIOService, fileImageChooserFactory,
				() -> creationImageService.multiplyImages(imageView.getImage(), imageViewResult.getImage())));
		MenuItem multiplyImageByScalar = new MenuItem("multiply image by scalar");
		multiplyImageByScalar.setOnAction(new IntroduceScalarEventHandler(creationImageService, imageView, imageViewResult));
		arithmeticOperationsBetweenImages.getItems().addAll(plusImages, subtractImages, multiplyImages, multiplyImageByScalar);
		return arithmeticOperationsBetweenImages;
	}
}