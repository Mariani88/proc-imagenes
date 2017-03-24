package untref.interfacebuilders;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;


public class MenuBarBuilder {
	MenuBar menuBar;

	ImageView imageView, imageResultView;
	// Stage primaryStage;

	public MenuBarBuilder(ImageView imageView, ImageView imageResultView) {

		this.menuBar = new MenuBar();
		this.imageView = imageView;
		this.imageResultView = imageResultView;
		// this.primaryStage=primaryStage;
	}

	private Menu menuFileCreate() {

		Menu fileMenu = new Menu("Archivo");
		MenuItem openMenuItem = new MenuItem("Abrir");
		MenuItem saveMenuItem = new MenuItem("Guardar");
		this.eventOpenItemMenu(openMenuItem);
		this.eventSaveItemMenu(saveMenuItem);

		fileMenu.getItems().addAll(openMenuItem, saveMenuItem);

		return fileMenu;
	}

	public MenuBar getMenuBar() {
		this.menuBar.getMenus().addAll(this.menuFileCreate());

		return this.menuBar;
	}

	private void eventOpenItemMenu(MenuItem openMenuItem) {

		openMenuItem.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {

				FileChooser fileChooser = new FileChooser();
				fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.bmp", "*.raw", "*.pgm", "*.ppm", "*.jpg"));
				File selectedFile = fileChooser.showOpenDialog(null);

				if (selectedFile == null) {
					System.out.println("file is null");
				}

				BufferedImage bufferedImage = null;
				try {
					bufferedImage = ImageIO.read(selectedFile);
				} catch (IOException e) {
					e.printStackTrace();
				}

				Image image = SwingFXUtils.toFXImage(bufferedImage, null);
				imageView.setImage(image);


			}
		});

	}

	private void eventSaveItemMenu(MenuItem saveMenuItem) {

		saveMenuItem.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {

				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Save Image");
				fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("BMP Files (*.bmp)", "*.bmp"));
				fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Files (*.png)", "*.png"));

				File file = fileChooser.showSaveDialog(null);
				if (file != null) {
					try {
						ImageIO.write(SwingFXUtils.fromFXImage(imageView.getImage(), null), "jpg", file);
					} catch (IOException ex) {
											}
				}

			}
		});
	}
}
