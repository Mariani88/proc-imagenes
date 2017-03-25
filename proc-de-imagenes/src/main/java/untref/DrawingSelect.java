package untref;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class DrawingSelect {

	DrawRectangleSelection seccionSelection;
	ImageView imageView;

	Stage secondaryStage;

	public void start(Image image) {

		this.secondaryStage = new Stage();

		secondaryStage.setTitle("Image Selection");

		BorderPane root = new BorderPane();

		TitledPane scrollPane = new TitledPane();

		final Group imageLayer = new Group();

		this.imageView = new ImageView(image);
		scrollPane.autosize();

		imageLayer.getChildren().add(imageView);
		imageLayer.autosize();

		scrollPane.setContent(imageLayer);
		scrollPane.autosize();

		root.setCenter(scrollPane);
		root.autosize();

		seccionSelection = new DrawRectangleSelection(imageLayer);

	
		final ContextMenu contextMenu = new ContextMenu();

		MenuItem cropFileMenuItem = new MenuItem("Copy in new file");
		MenuItem cropWindowsMenuItem = new MenuItem("Copy in new windows");
		cropFileMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				
				Bounds selectionBounds = seccionSelection.getBounds();
////////////borrarrrrrr			
				System.out.println("area seleccionada: " + selectionBounds);

				cropSaveImage(selectionBounds);

			}
		});
		
		
		cropWindowsMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				
				Bounds selectionBounds = seccionSelection.getBounds();
////////////borrarrrrrr			
				System.out.println("area seleccionada: " + selectionBounds);

				cropSaveWindowsImage(selectionBounds);

			}
		});
		
		
		contextMenu.getItems().addAll(cropFileMenuItem,cropWindowsMenuItem);

	
		imageLayer.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				if (event.isSecondaryButtonDown()) {
					contextMenu.show(imageLayer, event.getScreenX(), event.getScreenY());
				}
			}
		});

		Scene scene = new Scene(root);

		secondaryStage.setScene(scene);
		secondaryStage.sizeToScene();
		secondaryStage.show();
	}

	private void cropSaveImage(Bounds bounds) {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Image");

		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"));

		File file = fileChooser.showSaveDialog(secondaryStage);
		if (file == null)
			return;

		int width = (int) bounds.getWidth();
		int height = (int) bounds.getHeight();

		SnapshotParameters parameters = new SnapshotParameters();
		parameters.setFill(Color.TRANSPARENT);
		parameters.setViewport(new Rectangle2D(bounds.getMinX(), bounds.getMinY(), width, height));

		WritableImage wi = new WritableImage(width, height);
		imageView.snapshot(parameters, wi);

		BufferedImage bufImageARGB = SwingFXUtils.fromFXImage(wi, null);
		BufferedImage bufImageRGB = new BufferedImage(bufImageARGB.getWidth(), bufImageARGB.getHeight(),
				BufferedImage.OPAQUE);

		Graphics2D graphics = bufImageRGB.createGraphics();
		graphics.drawImage(bufImageARGB, 0, 0, null);

		try {
			
			String extension = "";

			int i = file.getName().lastIndexOf('.');
			int p = Math.max(file.getName().lastIndexOf('/'), file.getName().lastIndexOf('\\'));

			if (i > p) {
			    extension = file.getName().substring(i+1);
			}

			ImageIO.write(bufImageRGB, extension, file);

			System.out.println("Imagen guardada en ojeteeee " + file.getAbsolutePath());

		} catch (IOException e) {
			e.printStackTrace();
		}

		graphics.dispose();

	}
	
	private void cropSaveWindowsImage(Bounds bounds) {

		

		int width = (int) bounds.getWidth();
		int height = (int) bounds.getHeight();

		SnapshotParameters parameters = new SnapshotParameters();
		parameters.setFill(Color.TRANSPARENT);
		parameters.setViewport(new Rectangle2D(bounds.getMinX(), bounds.getMinY(), width, height));

		WritableImage wi = new WritableImage(width, height);
		imageView.snapshot(parameters, wi);
this.start(wi);
		
	}

}
