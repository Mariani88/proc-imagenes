package untref.controllers;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import untref.eventhandlers.ChangeColorFromRGBToHSVHandler;
import untref.eventhandlers.CopyImageNewWindowsHandler;
import untref.eventhandlers.CreationSpecificImageHandler;
import untref.eventhandlers.EditionImageEventHandler;
import untref.factory.FileImageChooserFactory;
import untref.service.*;
import untref.service.colorbands.BlueBand;
import untref.service.colorbands.GreenBand;
import untref.service.colorbands.RedBand;

public class EditionMenuController {

	private ArithmeticOperationsMenuController arithmeticOperationsMenuController;
	private ImageEditionService imageEditionService;
	private ImageIOService imageIOService;
	private FileImageChooserFactory fileImageChooserFactory;
	private CreationImageService creationImageService;

	public EditionMenuController(ArithmeticOperationsMenuController arithmeticOperationsMenuController, ImageEditionService imageEditionService,
			ImageIOService imageIOService, FileImageChooserFactory fileImageChooserFactory, CreationImageService creationImageService) {
		this.arithmeticOperationsMenuController = arithmeticOperationsMenuController;
		this.imageEditionService = imageEditionService;
		this.imageIOService = imageIOService;
		this.fileImageChooserFactory = fileImageChooserFactory;
		this.creationImageService = creationImageService;
	}

	public Menu createEditionMenu(ImageView imageView, ImageView imageViewResult) {
		Menu editionMenu = new Menu("Edition");
		Menu binaryImages = createBinaryImagesSubMenu();
		Menu degreeImages = createDegreeImagesSubMenu();
		MenuItem copyImageNewWindows = new MenuItem("selection image");
		MenuItem rgbToHsv = new MenuItem("RGB to HSV");
		Menu colorBand = createColorBandMenu(imageView);
		copyImageNewWindows.setOnAction(new CopyImageNewWindowsHandler(imageView));
		rgbToHsv.setOnAction(
				new ChangeColorFromRGBToHSVHandler(imageView, new ImageGetColorRGBImpl(imageView.getImage()), new ImageEditionServiceImpl()));
		Menu arithmeticOperationsBetweenImages = arithmeticOperationsMenuController
				.createArithmeticOperationsBetweenImages(imageView, imageViewResult);

		MenuItem negativeImage = new MenuItem("Negative image");
		negativeImage.setOnAction(
				new EditionImageEventHandler(imageView, imageViewResult, () -> imageEditionService.transformToNegative(imageView.getImage())));

		editionMenu.getItems()
				.addAll(binaryImages, degreeImages, copyImageNewWindows, rgbToHsv, colorBand, arithmeticOperationsBetweenImages, negativeImage);
		return editionMenu;
	}

	private Menu createColorBandMenu(ImageView imageView) {
		Menu colorBand = new Menu("Color band");
		MenuItem redBand = new MenuItem("red band");
		redBand.setOnAction(new CreationSpecificImageHandler(imageIOService, fileImageChooserFactory,
				() -> creationImageService.createImageWithSpecificColorBand(imageView.getImage(), new RedBand())));
		MenuItem blueBand = new MenuItem("blue band");
		blueBand.setOnAction(new CreationSpecificImageHandler(imageIOService, fileImageChooserFactory,
				() -> creationImageService.createImageWithSpecificColorBand(imageView.getImage(), new BlueBand())));
		MenuItem greenBand = new MenuItem("green band");
		greenBand.setOnAction(new CreationSpecificImageHandler(imageIOService, fileImageChooserFactory,
				() -> creationImageService.createImageWithSpecificColorBand(imageView.getImage(), new GreenBand())));
		colorBand.getItems().addAll(redBand, blueBand, greenBand);
		return colorBand;
	}

	private Menu createBinaryImagesSubMenu() {
		Menu binaryImages = new Menu("BinaryImages");
		MenuItem binaryImageWithQuadrate = new MenuItem("binary image with quadrate");
		binaryImageWithQuadrate.setOnAction(new CreationSpecificImageHandler(imageIOService, fileImageChooserFactory,
				() -> creationImageService.createBinaryImageWithCenterQuadrate(200, 200)));
		MenuItem binaryImageWithCircle = new MenuItem("binary image with circle");
		binaryImageWithCircle.setOnAction(new CreationSpecificImageHandler(imageIOService, fileImageChooserFactory,
				() -> creationImageService.createBinaryImageWithCenterCircle(200, 200)));
		binaryImages.getItems().addAll(binaryImageWithQuadrate, binaryImageWithCircle);
		return binaryImages;
	}

	private Menu createDegreeImagesSubMenu() {
		Menu degrees = new Menu("degreeas");
		MenuItem grayDegree = new MenuItem("gray degree");
		MenuItem colorDegree = new MenuItem("color degree");
		grayDegree.setOnAction(new CreationSpecificImageHandler(imageIOService, fileImageChooserFactory,
				() -> creationImageService.createImageWithGrayDegree(250, 250)));
		colorDegree.setOnAction(new CreationSpecificImageHandler(imageIOService, fileImageChooserFactory,
				() -> creationImageService.createImageWithColorDegree(255, 255)));
		degrees.getItems().addAll(grayDegree, colorDegree);
		return degrees;
	}

}