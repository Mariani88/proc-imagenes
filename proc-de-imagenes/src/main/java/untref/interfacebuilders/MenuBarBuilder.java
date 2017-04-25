package untref.interfacebuilders;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import untref.controllers.ArithmeticOperationsMenuController;
import untref.controllers.EditionMenuController;
import untref.eventhandlers.CreateHistogramHandler;
import untref.eventhandlers.CreateMediaFilterHandler;
import untref.eventhandlers.CreateMedianaHandler;
import untref.eventhandlers.CreateThresholdHandler;
import untref.eventhandlers.EqualizeHandler;
import untref.eventhandlers.OpenImageEventHandler;
import untref.eventhandlers.SaveImageEventHandler;
import untref.factory.FileImageChooserFactory;
import untref.repository.ImageRepository;
import untref.repository.ImageRepositoryImpl;
import untref.service.*;

public class MenuBarBuilder {

	private final ArithmeticOperationsMenuController arithmeticOperationsMenuController;
	private final EditionMenuController editionMenuController;
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
		editionMenuController = new EditionMenuController(arithmeticOperationsMenuController, imageEditionService, imageIOService,
				fileImageChooserFactory, creationImageService);
	}

	public MenuBar build(final ImageView imageView, final ImageView imageResultView) {
		MenuBar menuBar = new MenuBar();
		Menu fileMenu = createFileMenu(imageView, imageResultView);
		Menu editionMenu = editionMenuController.createEditionMenu(imageView, imageResultView);
		Menu histogramMenu = createHistogramMenu(imageView, imageResultView);
		Menu filterMenu = createFilterMenu(imageView, imageResultView);
		Menu thresholdAndContrastMenu = createThresholdAndContrastMenu(imageView, imageResultView);
		
		menuBar.getMenus().addAll(fileMenu, editionMenu, histogramMenu,filterMenu);
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
	
	private Menu createThresholdAndContrastMenu(ImageView imageView, ImageView imageResultView) {
		Menu thresholdAndContrastMenu = new Menu("Threshold/Contrast");
		MenuItem threshold = new MenuItem("Threshold");
		MenuItem contrast = new MenuItem("Contrast");
		//threshold.setOnAction(new CreateThresholdHandler(imageView,imageResultView,5));
		//equalize.setOnAction(new EqualizeHandler(imageView, imageResultView));
		thresholdAndContrastMenu.getItems().addAll(threshold, contrast);
		return thresholdAndContrastMenu;
	}

	
	private Menu createFilterMenu(ImageView imageView, ImageView imageResultView) {
		Menu filterMenu = new Menu("Filter");
		MenuItem media = new MenuItem("media");
		MenuItem mediana = new MenuItem("mediana");
		media.setOnAction(new CreateMediaFilterHandler(imageView,imageResultView));
		mediana.setOnAction(new CreateMedianaHandler(imageView, imageResultView));
		filterMenu.getItems().addAll(media,mediana);
		return filterMenu;
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