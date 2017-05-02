package untref.interfacebuilders;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import untref.controllers.ArithmeticOperationsMenuController;
import untref.controllers.EditionMenuController;
import untref.controllers.HistogramMenuController;
import untref.eventhandlers.CreateGaussianHandler;
import untref.eventhandlers.CreateHighPassEdgeHandler;
import untref.eventhandlers.CreateMediaFilterHandler;
import untref.eventhandlers.CreateMedianWeightedHandler;
import untref.eventhandlers.CreateMedianaHandler;
import untref.eventhandlers.OpenImageEventHandler;
import untref.eventhandlers.SaveImageEventHandler;
import untref.factory.FileImageChooserFactory;
import untref.repository.ImageRepository;
import untref.repository.ImageRepositoryImpl;
import untref.service.*;
import untref.service.functions.AleatoryNumbersGeneratorServiceImpl;

import java.util.Random;

public class MenuBarBuilder {

	private final ArithmeticOperationsMenuController arithmeticOperationsMenuController;
	private final EditionMenuController editionMenuController;

	private final AleatoryNumbersGeneratorService aleatoryNumbersGeneratorService;
	private final HistogramService histogramService;
	private final HistogramMenuController histogramMenuController;

	private CreationImageService creationImageService;
	private ImageIOService imageIOService;
	private ImageRepository imageRepository;
	private FileImageChooserFactory fileImageChooserFactory;
	private final ImageArithmeticOperationService imageArithmeticOperationService;
	private final ImageEditionService imageEditionService;

	public MenuBarBuilder() {

		this.imageEditionService = new ImageEditionServiceImpl();

		this.imageRepository = new ImageRepositoryImpl();
		this.creationImageService = new CreationImageServiceImpl();
		this.imageIOService = new ImageIOServiceImpl(imageRepository);
		this.fileImageChooserFactory = new FileImageChooserFactory();

		this.imageArithmeticOperationService = new ImageArithmeticOperationServiceImpl();
		this.arithmeticOperationsMenuController = new ArithmeticOperationsMenuController(
				imageArithmeticOperationService, imageIOService, fileImageChooserFactory);
		this.editionMenuController = new EditionMenuController(arithmeticOperationsMenuController, imageEditionService,
				imageIOService, fileImageChooserFactory, creationImageService);
		this.aleatoryNumbersGeneratorService = new AleatoryNumbersGeneratorServiceImpl(new Random());
		histogramService = new HistogramServiceImpl();
		histogramMenuController = new HistogramMenuController(aleatoryNumbersGeneratorService, histogramService);
	}

	public MenuBar build(final ImageView imageView, final ImageView imageResultView) {
		MenuBar menuBar = new MenuBar();
		Menu fileMenu = createFileMenu(imageView, imageResultView);
		Menu editionMenu = editionMenuController.createEditionMenu(imageView, imageResultView);

		Menu histogramMenu = histogramMenuController.createHistogramMenu(imageView, imageResultView);
		Menu filterMenu = createFilterMenu(imageView, imageResultView);
		Menu thresholdAndContrastMenu = createThresholdAndContrastMenu(imageView, imageResultView);
		Menu EdgeMenu = createEdgeMenu(imageView, imageResultView);
		;
		menuBar.getMenus().addAll(fileMenu, editionMenu, histogramMenu, filterMenu, EdgeMenu);
		return menuBar;
	}

	private Menu createThresholdAndContrastMenu(ImageView imageView, ImageView imageResultView) {
		Menu thresholdAndContrastMenu = new Menu("Threshold/Contrast");
		MenuItem threshold = new MenuItem("Threshold");
		MenuItem contrast = new MenuItem("Contrast");
				thresholdAndContrastMenu.getItems().addAll(threshold, contrast);
		return thresholdAndContrastMenu;
	}

	private Menu createFilterMenu(ImageView imageView, ImageView imageResultView) {
		Menu filterMenu = new Menu("Filter");
		MenuItem media = new MenuItem("media");
		MenuItem mediana = new MenuItem("median");
		MenuItem gaussian = new MenuItem("gaussian");
		MenuItem medianWeighted = new MenuItem("Median weighted ");

		media.setOnAction(new CreateMediaFilterHandler(imageView, imageResultView));
		mediana.setOnAction(new CreateMedianaHandler(imageView, imageResultView));
		gaussian.setOnAction(new CreateGaussianHandler(imageView, imageResultView));

		medianWeighted.setOnAction(new CreateMedianWeightedHandler(imageView, imageResultView));
		filterMenu.getItems().addAll(media, mediana, medianWeighted, gaussian);
		return filterMenu;
	}

	private Menu createEdgeMenu(ImageView imageView, ImageView imageResultView) {
		Menu edgeMenu = new Menu("Edge");
		MenuItem highPass = new MenuItem("high Pass");

		highPass.setOnAction(new CreateHighPassEdgeHandler(imageView, imageResultView));
		edgeMenu.getItems().addAll(highPass);
		return edgeMenu;
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
		addToFirstViewer.setOnAction(
				new OpenImageEventHandler(fileImageChooserFactory.create("Open image"), imageView, imageIOService));
		MenuItem addToSecondViewer = new MenuItem("add to second viewer");
		addToSecondViewer.setOnAction(
				new OpenImageEventHandler(fileImageChooserFactory.create("Open image"), imageView2, imageIOService));
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