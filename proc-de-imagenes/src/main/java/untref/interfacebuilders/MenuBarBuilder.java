package untref.interfacebuilders;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import untref.eventhandlers.ChangeColorFromRGBToHSVHandler;
import untref.eventhandlers.CopyImageNewWindowsHandler;
import untref.eventhandlers.CreateHistogramHandler;
import untref.eventhandlers.CreationSpecificImageHandler;
import untref.eventhandlers.OpenImageEventHandler;
import untref.eventhandlers.SaveImageEventHandler;
import untref.factory.FileImageChooserFactory;
import untref.repository.ImageRepository;
import untref.repository.ImageRepositoryImpl;
import untref.service.CreationImageService;
import untref.service.CreationImageServiceImpl;
import untref.service.ImageEditionServiceImpl;
import untref.service.ImageGetColorRGBImpl;
import untref.service.ImageIOService;
import untref.service.ImageIOServiceImpl;
import untref.service.colorbands.BlueBand;
import untref.service.colorbands.GreenBand;
import untref.service.colorbands.RedBand;

public class MenuBarBuilder {

	private CreationImageService creationImageService;
	private ImageIOService imageIOService;
	private ImageRepository imageRepository;
	private FileImageChooserFactory fileImageChooserFactory;

	public MenuBarBuilder() {
		this.imageRepository = new ImageRepositoryImpl();
		this.creationImageService = new CreationImageServiceImpl();
		this.imageIOService = new ImageIOServiceImpl(imageRepository);
		this.fileImageChooserFactory = new FileImageChooserFactory();
	}

	public MenuBar build(final ImageView imageView) {
		MenuBar menuBar = new MenuBar();
		Menu fileMenu = createFileMenu(imageView);
		Menu editionMenu = createEditionMenu(imageView);
		Menu histogramMenu = createHistogramMenu(imageView);
		menuBar.getMenus().addAll(fileMenu, editionMenu,histogramMenu);
		return menuBar;
	}

	
	private Menu createHistogramMenu(ImageView imageView) {
		Menu histogramMenu = new Menu("histogram");
	//	Menu binaryImages = createBinaryImagesSubMenu();
	//	Menu degreeImages = createDegreeImagesSubMenu();
		MenuItem create = new MenuItem("create");
		//MenuItem rgbToHsv = new MenuItem("RGB to HSV");
		//Menu colorBand = createColorBandMenu(imageView);
		create.setOnAction(new CreateHistogramHandler(imageView));
	
		histogramMenu.getItems().addAll( create);
		return histogramMenu;

	}
	
	
	private Menu createEditionMenu(ImageView imageView) {
		Menu editionMenu = new Menu("Edition");
		Menu binaryImages = createBinaryImagesSubMenu();
		Menu degreeImages = createDegreeImagesSubMenu();
		MenuItem copyImageNewWindows = new MenuItem("selection image");
		MenuItem rgbToHsv = new MenuItem("RGB to HSV");
		Menu colorBand = createColorBandMenu(imageView);
		copyImageNewWindows.setOnAction(new CopyImageNewWindowsHandler(imageView));
		rgbToHsv.setOnAction(
				new ChangeColorFromRGBToHSVHandler(imageView, new ImageGetColorRGBImpl(imageView.getImage()), new ImageEditionServiceImpl()));

		editionMenu.getItems().addAll(binaryImages, degreeImages, copyImageNewWindows, rgbToHsv, colorBand);
		return editionMenu;

	}

	private Menu createColorBandMenu(ImageView imageView) {
		Menu colorBand = new Menu("Color band");
		MenuItem redBand = new MenuItem("red band");
		redBand.setOnAction(new CreationSpecificImageHandler(creationImageService, imageIOService, fileImageChooserFactory,
				() -> creationImageService.createImageWithSpecificColorBand(imageView.getImage(), new RedBand())));
		MenuItem blueBand = new MenuItem("blue band");
		blueBand.setOnAction(new CreationSpecificImageHandler(creationImageService, imageIOService, fileImageChooserFactory,
				() -> creationImageService.createImageWithSpecificColorBand(imageView.getImage(), new BlueBand())));
		MenuItem greenBand = new MenuItem("green band");
		greenBand.setOnAction(new CreationSpecificImageHandler(creationImageService, imageIOService, fileImageChooserFactory,
				() -> creationImageService.createImageWithSpecificColorBand(imageView.getImage(), new GreenBand())));
		colorBand.getItems().addAll(redBand, blueBand, greenBand);
		return colorBand;
	}

	private Menu createDegreeImagesSubMenu() {
		Menu degrees = new Menu("degreeas");
		MenuItem grayDegree = new MenuItem("gray degree");
		MenuItem colorDegree = new MenuItem("color degree");
		grayDegree.setOnAction(new CreationSpecificImageHandler(creationImageService, imageIOService, fileImageChooserFactory,
				() -> creationImageService.createImageWithGrayDegree(250, 250)));
		colorDegree.setOnAction(new CreationSpecificImageHandler(creationImageService, imageIOService, fileImageChooserFactory,
				() -> creationImageService.createImageWithColorDegree(255, 255)));
		degrees.getItems().addAll(grayDegree, colorDegree);
		return degrees;
	}

	private Menu createBinaryImagesSubMenu() {
		Menu binaryImages = new Menu("BinaryImages");
		MenuItem binaryImageWithQuadrate = new MenuItem("binary image with quadrate");
		binaryImageWithQuadrate.setOnAction(new CreationSpecificImageHandler(creationImageService, imageIOService, fileImageChooserFactory,
				() -> creationImageService.createBinaryImageWithCenterQuadrate(200, 200)));
		MenuItem binaryImageWithCircle = new MenuItem("binary image with circle");
		binaryImageWithCircle.setOnAction(new CreationSpecificImageHandler(creationImageService, imageIOService, fileImageChooserFactory,
				() -> creationImageService.createBinaryImageWithCenterCircle(200, 200)));
		binaryImages.getItems().addAll(binaryImageWithQuadrate, binaryImageWithCircle);
		return binaryImages;
	}

	private Menu createFileMenu(ImageView imageView) {
		Menu fileMenu = new Menu("File");
		MenuItem fileMenuItemOpen = createOpenMenuItem(imageView);
		MenuItem fileMenuItemSave = createSaveMenuItem(imageView);
		fileMenu.getItems().addAll(fileMenuItemOpen, fileMenuItemSave);
		return fileMenu;
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