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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MenuBarBuilder {

	public MenuBar build(final ImageView imageView) {
		MenuBar menuBar = new MenuBar();
		Menu fileMenu = new Menu("File");
		MenuItem fileMenuItem = new MenuItem("open...");

		final FileChooser fileChooser = new FileChooser();

		fileChooser.getExtensionFilters()
				.addAll(new FileChooser.ExtensionFilter("All Images", "*.*"), new FileChooser.ExtensionFilter("JPG", "*.jpg"),
						new FileChooser.ExtensionFilter("PNG", "*.png"), new FileChooser.ExtensionFilter("RAW", "*.raw"),
						new FileChooser.ExtensionFilter("PGM", "*.pgm"), new FileChooser.ExtensionFilter("PPM", "*.ppm"),
						new FileChooser.ExtensionFilter("BMP", "*.bmp"));

		fileChooser.setTitle("Open Resource File");
		fileMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				File file = fileChooser.showOpenDialog(null);
				if (file == null) {
					System.out.println("file is null");
				}

				BufferedImage bufferedImage = null;
				try {
					bufferedImage = ImageIO.read(file);
				} catch (IOException e) {
					e.printStackTrace();
				}

				Image image = SwingFXUtils.toFXImage(bufferedImage, null);
				imageView.setImage(image);




			}
		});

		fileMenu.getItems().addAll(fileMenuItem);
		menuBar.getMenus().addAll(fileMenu, new Menu("hola"));
		return menuBar;
	}
}
