import ij.plugin.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ij.*;
import java.io.*;
import java.nio.file.Path;

import java.awt.image.BufferedImage;

/** Uses the JFileChooser from Swing to open one or more images. */
public class File_Opener implements PlugIn {
	static File dir;
	Image image;

	public Image getImage() {
		return image;
	}

	public void run(String arg, Stage primaryStage) {
		openFiles(primaryStage);
		IJ.register(File_Opener.class);
	}

	public File chooserPrueba(Stage primaryStage) {
		FileChooser fileChooser = new FileChooser();

		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images", "*.*"),
				new FileChooser.ExtensionFilter("JPG", "*.jpg"), new FileChooser.ExtensionFilter("GIF", "*.gif"),
				new FileChooser.ExtensionFilter("BMP", "*.bmp"), new FileChooser.ExtensionFilter("PNG", "*.png"),
				new FileChooser.ExtensionFilter("RAW", "*.raw"), new FileChooser.ExtensionFilter("PGM", "*.pgm"),
				new FileChooser.ExtensionFilter("PPM", "*.ppm"));

		
		File file = fileChooser.showOpenDialog(primaryStage);
		return file;
	}

	;

	public void openFiles(Stage primaryStage) {

		File file = chooserPrueba(primaryStage);

		Opener opener = new Opener();
		BufferedImage bufferedImage = null;

		Path path = file.toPath();

		ImagePlus img = opener.openImage(path.toString());

		bufferedImage = img.getBufferedImage();
		image = SwingFXUtils.toFXImage(bufferedImage, null);
	}

	@Override
	public void run(String arg0) {
		// TODO Auto-generated method stub

	}

}
