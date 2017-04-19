package untref.interfacebuilders;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import untref.controllers.ArithmeticOperationsMenuController;
import untref.eventhandlers.*;
import untref.factory.FileImageChooserFactory;
import untref.repository.ImageRepository;
import untref.repository.ImageRepositoryImpl;
import untref.service.*;
import untref.service.colorbands.BlueBand;
import untref.service.colorbands.GreenBand;
import untref.service.colorbands.RedBand;

import java.util.function.Supplier;

public class MenuBarBuilder {

	private final ArithmeticOperationsMenuController arithmeticOperationsMenuController;
	private CreationImageService creationImageService;
	private ImageIOService imageIOService;
	private ImageRepository imageRepository;
	private FileImageChooserFactory fileImageChooserFactory;
	private final ImageArithmeticOperationService imageArithmeticOperationService;
	private final ImageEditionService imageEditionService;

	public MenuBarBuilder() {
		imageEditionService = new ImageEditionServiceImpl();
		this.imageRepository = new ImageRepositoryImpl();
		this.creationImageService = new CreationImageServiceImpl();
		this.imageIOService = new ImageIOServiceImpl(imageRepository);
		this.fileImageChooserFactory = new FileImageChooserFactory();
		imageArithmeticOperationService = new ImageArithmeticOperationServiceImpl();
		arithmeticOperationsMenuController = new ArithmeticOperationsMenuController(imageArithmeticOperationService, imageIOService,
				fileImageChooserFactory);
	}

	public MenuBar build(final ImageView imageView, final ImageView imageResultView) {
		MenuBar menuBar = new MenuBar();
		Menu fileMenu = createFileMenu(imageView, imageResultView);
		Menu editionMenu = createEditionMenu(imageView, imageResultView);
		Menu histogramMenu = createHistogramMenu(imageView, imageResultView);
		menuBar.getMenus().addAll(fileMenu, editionMenu, histogramMenu);
		return menuBar;
	}

	private Menu createHistogramMenu(ImageView imageView, ImageView imageResultView) {
		Menu histogramMenu = new Menu("Histogram");
		MenuItem create = new MenuItem("create");
		MenuItem equalize = new MenuItem("Equalize");
		create.setOnAction(new CreateHistogramHandler(imageView));
		equalize.setOnAction(new EqualizeHandler(imageView, imageResultView));
		histogramMenu.getItems().addAll(create, equalize);
		return histogramMenu;
	}

	private Menu createEditionMenu(ImageView imageView, ImageView imageViewResult) {
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

	private Menu createFileMenu(ImageView imageView, ImageView imageView2) {
		Menu fileMenu = new Menu("File");
		MenuItem fileMenuItemOpen = createOpenMenuItem(imageView);
		Menu multiplesImagesOpen = createMultiplesImagesOpenMenu(imageView, imageView2);
		MenuItem fileMenuItemSave = createSaveMenuItem(imageView);
		fileMenu.getItems().addAll(fileMenuItemOpen, multiplesImagesOpen, fileMenuItemSave);
		return fileMenu;
	}

	private Menu createMultiplesImagesOpenMenu(ImageView imageView, ImageView imageView2) {
		Menu openMultiplesImages = new Menu("open multiples images");
		MenuItem addToFirstViewer = new MenuItem("add to first viewer");
		addToFirstViewer.setOnAction(new OpenImageEventHandler(fileImageChooserFactory.create("Open image"), imageView, imageIOService));
		MenuItem addToSecondViewer = new MenuItem("add to second viewer");
		addToSecondViewer.setOnAction(new OpenImageEventHandler(fileImageChooserFactory.create("Open image"), imageView2, imageIOService));
		openMultiplesImages.getItems().addAll(addToFirstViewer, addToSecondViewer);
		return openMultiplesImages;
	}

	private MenuItem createOpenMenuItem(ImageView imageView) {
		MenuItem fileMenuItem = new MenuItem("open...");
		final FileChooser fileChooser = fileImageChooserFactory.create("open image");
		setOpenEvent(imageView, fileMenuItem, fileChooser);
		return fileMenuItem;
	}

	private MenuItem createSaveMenuItem(ImageView imageView) {
		MenuItem fileMenuItem = new MenuItem("Save as");
		final FileChooser fileChooser = fileImageChooserFactory.create("save image");
		setSaveEvent(imageView, fileMenuItem, fileChooser);
		return fileMenuItem;
	}

	private void setOpenEvent(final ImageView imageView, MenuItem fileMenuItem, final FileChooser fileChooser) {
		fileMenuItem.setOnAction(new OpenImageEventHandler(fileChooser, imageView, imageIOService));
	}

	private void setSaveEvent(final ImageView imageView, MenuItem fileMenuItem, final FileChooser fileChooser) {
		fileMenuItem.setOnAction(new SaveImageEventHandler(fileChooser, imageView, imageIOService));
	}
}