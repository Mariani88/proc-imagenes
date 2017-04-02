package untref.interfacebuilders;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import untref.eventhandlers.BinaryImageWithCenterFigureHandler;
import untref.eventhandlers.OpenImageEventHandler;
import untref.eventhandlers.SaveImageEventHandler;
import untref.factory.FileImageChooserFactory;
import untref.repository.ImageRepository;
import untref.repository.ImageRepositoryImpl;
import untref.service.CreationImageService;
import untref.service.CreationImageServiceImpl;
import untref.service.ImageIOService;
import untref.service.ImageIOServiceImpl;

import java.util.function.Supplier;

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
		Menu editionMenu = createEditionMenu();
		menuBar.getMenus().addAll(fileMenu, editionMenu);
		return menuBar;
	}

	private Menu createEditionMenu() {
		Menu editionMenu = new Menu("Edition");
		MenuItem binaryImageWithQuadrate = new MenuItem("binary image with quadrate");
		binaryImageWithQuadrate.setOnAction(new BinaryImageWithCenterFigureHandler(creationImageService, imageIOService, fileImageChooserFactory,
				() -> creationImageService.createBinaryImageWithCenterQuadrate(200, 200)));
		MenuItem binaryImageWithCircle = new MenuItem("binary image with circle");
		binaryImageWithCircle.setOnAction(new BinaryImageWithCenterFigureHandler(creationImageService, imageIOService, fileImageChooserFactory,
				() -> creationImageService.createBinaryImageWithCenterCircle(200, 200)));
		editionMenu.getItems().addAll(binaryImageWithQuadrate, binaryImageWithCircle);
		return editionMenu;
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