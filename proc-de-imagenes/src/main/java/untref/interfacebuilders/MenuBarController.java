package untref.interfacebuilders;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import untref.controllers.*;
import untref.controllers.activecotours.ActiveContoursController;
import untref.controllers.nodeutils.settertype.SetterAdjustToView;
import untref.eventhandlers.SaveImageEventHandler;
import untref.factory.FileImageChooserFactory;
import untref.repository.ImageRepository;
import untref.repository.ImageRepositoryImpl;
import untref.service.*;
import untref.service.functions.AleatoryNumbersGeneratorServiceImpl;

import java.util.Random;

public class MenuBarController {

	private final ArithmeticOperationsMenuController arithmeticOperationsMenuController;
	private final EditionMenuController editionMenuController;

	private final AleatoryNumbersGeneratorService aleatoryNumbersGeneratorService;
	private final HistogramService histogramService;
	private final HistogramMenuController histogramMenuController;
	private final EdgeMenuController edgeMenuController;
	private final EdgeDetectionService edgeDetectionService;
	private final OpenMenuController openMenuController;

	private CreationImageService creationImageService;
	private ImageIOService imageIOService;
	private ImageRepository imageRepository;
	private FileImageChooserFactory fileImageChooserFactory;
	private final ImageArithmeticOperationService imageArithmeticOperationService;
	private final ImageEditionService imageEditionService;
	private FilterMenuController filterMenuController;
	private MultiplesImageOpenMenuController multiplesImageOpenMenuController;
	private ThresholdingMenuController thresholdingMenuController;
	private final DiffusionMenuControler diffusionMenuControler;
	private ActiveContoursController activeContoursController;
	private HoughMenuController houghMenuController;
	private SiftController siftController;
	private RawImage rawImage;
	private CharacteristicPointsController CharacteristicPointsController;

	public MenuBarController() {
		this.siftController=new SiftController();
		this.diffusionMenuControler = new DiffusionMenuControler();
		this.houghMenuController = new HoughMenuController();
		this.edgeDetectionService = new EdgeDetectionServiceImpl();
		this.imageEditionService = new ImageEditionServiceImpl();
		this.imageRepository = new ImageRepositoryImpl();
		this.creationImageService = new CreationImageServiceImpl();
		this.imageIOService = new ImageIOServiceImpl(imageRepository);
		this.fileImageChooserFactory = new FileImageChooserFactory();
		this.imageArithmeticOperationService = new ImageArithmeticOperationServiceImpl();
		this.arithmeticOperationsMenuController = new ArithmeticOperationsMenuController(imageArithmeticOperationService, imageIOService,
				fileImageChooserFactory);
		this.editionMenuController = new EditionMenuController(arithmeticOperationsMenuController, imageEditionService, imageIOService,
				fileImageChooserFactory, creationImageService);
		this.aleatoryNumbersGeneratorService = new AleatoryNumbersGeneratorServiceImpl(new Random());
		histogramService = new HistogramServiceImpl();
		histogramMenuController = new HistogramMenuController(aleatoryNumbersGeneratorService, histogramService);
		edgeMenuController = new EdgeMenuController(edgeDetectionService);
		filterMenuController = new FilterMenuController();
		multiplesImageOpenMenuController = new MultiplesImageOpenMenuController(fileImageChooserFactory, imageIOService, rawImage);
		thresholdingMenuController = new ThresholdingMenuController();
		openMenuController = new OpenMenuController(fileImageChooserFactory, imageIOService);
		activeContoursController = new ActiveContoursController(openMenuController);
		this.CharacteristicPointsController = new CharacteristicPointsController();
	}

	public MenuBar build(final ImageView imageView, final ImageView imageResultView, RawImage rawImage) {
		this.rawImage = rawImage;
		MenuBar menuBar = new MenuBar();
		Menu sift=this.siftController.createSift(imageView, imageResultView);
		Menu fileMenu = createFileMenu(imageView, imageResultView, rawImage);
		Menu editionMenu = editionMenuController.createEditionMenu(imageView, imageResultView);
		Menu histogramMenu = histogramMenuController.createHistogramMenu(imageView, imageResultView);
		Menu filterMenu = filterMenuController.createFilterMenu(imageView, imageResultView);
		Menu EdgeMenu = edgeMenuController.createEdgeMenu(imageView, imageResultView);
		Menu thresholdingMenu = thresholdingMenuController.createThresholdingMenu(imageView, imageResultView);
		Menu difussionMenu = this.diffusionMenuControler.createDiffusionMenu(imageView, imageResultView);
		Menu activeContours = this.activeContoursController.create();
		Menu houghTransform = this.houghMenuController.createHoughMenu(imageView, imageResultView);
		Menu harris = this.CharacteristicPointsController.create(imageView, imageResultView);
		menuBar.getMenus()
				.addAll(fileMenu, editionMenu, histogramMenu, filterMenu, EdgeMenu, thresholdingMenu, difussionMenu, houghTransform, activeContours,
						harris,sift);
		return menuBar;
	}

	private Menu createFileMenu(ImageView imageView, ImageView imageView2, RawImage rawImage) {
		Menu fileMenu = new Menu("File");
		MenuItem fileMenuItemOpen = openMenuController.createOpenMenuItem(imageView, rawImage, new SetterAdjustToView());
		Menu multiplesImagesOpen = multiplesImageOpenMenuController.createMultiplesImagesOpenMenu(imageView, imageView2);
		MenuItem fileMenuItemSave = createSaveMenuItem(imageView);
		fileMenu.getItems().addAll(fileMenuItemOpen, multiplesImagesOpen, fileMenuItemSave);
		return fileMenu;
	}

	private MenuItem createSaveMenuItem(ImageView imageView) {
		MenuItem fileMenuItem = new MenuItem("Save as");
		final FileChooser fileChooser = fileImageChooserFactory.create("save image");
		setSaveEvent(imageView, fileMenuItem, fileChooser);
		return fileMenuItem;
	}

	private void setSaveEvent(final ImageView imageView, MenuItem fileMenuItem, final FileChooser fileChooser) {
		fileMenuItem.setOnAction(new SaveImageEventHandler(fileChooser, imageView, imageIOService));
	}
}