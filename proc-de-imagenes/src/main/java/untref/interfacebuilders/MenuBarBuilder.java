package untref.interfacebuilders;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import untref.eventhandlers.BinaryImageWithQuadrateHandler;
import untref.service.CreationImageService;
import untref.service.CreationImageServiceImpl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MenuBarBuilder {

	private CreationImageService creationImageService;

	public MenuBarBuilder() {
		this.creationImageService = new CreationImageServiceImpl();
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
		binaryImageWithQuadrate.setOnAction(new BinaryImageWithQuadrateHandler(creationImageService));
		editionMenu.getItems().add(binaryImageWithQuadrate);
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
		final FileChooser fileChooser = createOpenFileChooser();
		setOpenEvent(imageView, fileMenuItem, fileChooser);
		return fileMenuItem;
	}

	private MenuItem createSaveMenuItem(ImageView imageView) {
		MenuItem fileMenuItem = new MenuItem("Save as");
		final FileChooser fileChooser = createOpenFileChooser();
		setSaveEvent(imageView, fileMenuItem, fileChooser);
		return fileMenuItem;
	}

	private FileChooser createOpenFileChooser() {
		final FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open image");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images", "*.*"),
				new FileChooser.ExtensionFilter("JPG", "*.jpg"), new FileChooser.ExtensionFilter("PNG", "*.png"),
				new FileChooser.ExtensionFilter("RAW", "*.raw"), new FileChooser.ExtensionFilter("PGM", "*.pgm"),
				new FileChooser.ExtensionFilter("PPM", "*.ppm"), new FileChooser.ExtensionFilter("BMP", "*.bmp"));
		return fileChooser;
	}

	private void setOpenEvent(final ImageView imageView, MenuItem fileMenuItem, final FileChooser fileChooser) {
		fileMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				File file = fileChooser.showOpenDialog(null);
				try {
					BufferedImage bufferedImage = ImageIO.read(file);
					Image image = SwingFXUtils.toFXImage(bufferedImage, null);
					imageView.setImage(image);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void setSaveEvent(final ImageView imageView, MenuItem fileMenuItem, final FileChooser fileChooser) {

		fileMenuItem.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {

				fileChooser.setTitle("Save Image");
				File file = fileChooser.showSaveDialog(null);

				if (file != null) {
					try {
						ImageIO.write(SwingFXUtils.fromFXImage(imageView.getImage(), null), "jpg", file);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}

			}
		});
	}
}