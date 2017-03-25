package untref.interfacebuilders;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import untref.DrawingSelect;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MenuBarBuilder {

	public MenuBar build( ImageView imageView) {
		MenuBar menuBar = new MenuBar();
		Image  image=imageView.getImage();
		Menu fileMenu = createFileMenu(imageView);
		Menu selectionMenu = createSelectionMenu(imageView);
		menuBar.getMenus().addAll(fileMenu,selectionMenu);
		return menuBar;
	}

	private Menu createFileMenu(ImageView imageView) {
		Menu fileMenu = new Menu("File");
		MenuItem fileMenuItemOpen = createOpenMenuItem(imageView);
		MenuItem fileMenuItemSave = createSaveMenuItem(imageView);
		fileMenu.getItems().addAll(fileMenuItemOpen, fileMenuItemSave);
		return fileMenu;
	}
	private Menu createSelectionMenu(ImageView  imageView ) {
		Menu selectionMenu = new Menu("Selection");
		MenuItem selectionImage = createSelectionMenuItem(imageView );
		
		selectionMenu.getItems().addAll(selectionImage);
		return selectionMenu;
	}
	private MenuItem createSelectionMenuItem(ImageView imageView ) {
		MenuItem selectionMenuItem = new MenuItem("Selection Image");
		final FileChooser fileChooser = createOpenFileChooser();
		setSelectionEvent(imageView , selectionMenuItem, fileChooser);
		return selectionMenuItem;
	}

	private void setSelectionEvent( final ImageView  imageView, MenuItem selectionMenuItem, FileChooser fileChooser) {
		selectionMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				// PixelReader pixelReader = imageView.getPixelReader();
		         
			       /* int width = (int)imageView.getWidth();
			      /*  int height = (int)imageView.getHeight();
			         */
			        //Copy from source to destination pixel by pixel
			        WritableImage writableImage 
			                = 			        imageView.snapshot(new SnapshotParameters(), null);
			       /* PixelWriter pixelWriter = writableImage.getPixelWriter();
			         
			        for (int y = 0; y < height; y++){
			            for (int x = 0; x < width; x++){
			                Color color = pixelReader.getColor(x, y);
			                pixelWriter.setColor(x, y, color);
			            }
			        }
				*/
				DrawingSelect a=new DrawingSelect();
				a.start(writableImage );
				
			}
		});
		
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