package untref.controllers;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import untref.controllers.nodeutils.ImageSetter;
import untref.eventhandlers.*;
import untref.factory.FileImageChooserFactory;
import untref.service.*;
import untref.service.colorbands.BlueBand;
import untref.service.colorbands.GreenBand;
import untref.service.colorbands.RedBand;

public class EditionMenuController {

	private final ContrastService contrastService;
	private ArithmeticOperationsMenuController arithmeticOperationsMenuController;
	private ImageEditionService imageEditionService;
	private ImageIOService imageIOService;
	private FileImageChooserFactory fileImageChooserFactory;
	private CreationImageService creationImageService;
	private final NoiseService noiseService;

	public EditionMenuController(ArithmeticOperationsMenuController arithmeticOperationsMenuController, ImageEditionService imageEditionService,
			ImageIOService imageIOService, FileImageChooserFactory fileImageChooserFactory, CreationImageService creationImageService) {
		this.arithmeticOperationsMenuController = arithmeticOperationsMenuController;
		this.imageEditionService = imageEditionService;
		this.imageIOService = imageIOService;
		this.fileImageChooserFactory = fileImageChooserFactory;
		this.creationImageService = creationImageService;
		noiseService = new NoiseServiceImpl(new ImageArithmeticOperationServiceImpl());
		this.contrastService = new ContrastServiceImpl(new ImageStatisticServiceImpl());
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
		MenuItem negativeImage = createNegativeImageMenuItem(imageView, imageViewResult);
		MenuItem powerLawFunction = createPowerLawMenuItem(imageView, imageViewResult);
		MenuItem contrast = new MenuItem("add contrast");
		contrast.setOnAction(event -> ImageSetter.set(imageViewResult, contrastService.addContrast(imageView.getImage())));
		MenuItem addNoise = new MenuItem("add noise");
		addNoise.setOnAction(new NoiseParametersEventHandler(imageView, imageViewResult, noiseService));
		editionMenu.getItems()
				.addAll(binaryImages, degreeImages, copyImageNewWindows, rgbToHsv, colorBand, arithmeticOperationsBetweenImages, negativeImage,
						contrast, powerLawFunction, addNoise);
		return editionMenu;
	}

	private MenuItem createPowerLawMenuItem(ImageView imageView, ImageView imageViewResult) {
		MenuItem powerLawFunction = new MenuItem("power law function");
		powerLawFunction.setOnAction(new PowerLawGammaEventHandler(imageEditionService, imageView, imageViewResult));
		return powerLawFunction;
	}

	private MenuItem createNegativeImageMenuItem(ImageView imageView, ImageView imageViewResult) {
		MenuItem negativeImage = new MenuItem("Negative image");
		negativeImage.setOnAction(new EditionImageEventHandler(imageViewResult, () -> imageEditionService.transformToNegative(imageView.getImage()
		)));
		return negativeImage;
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
