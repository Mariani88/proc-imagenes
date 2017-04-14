package untref.eventhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import untref.factory.FileImageChooserFactory;
import untref.service.CreationImageService;
import untref.service.ImageIOService;

import java.util.function.Supplier;

public class CreationSpecificImageHandler implements EventHandler<ActionEvent> {

	private CreationImageService creationImageService;
	private ImageIOService imageIOService;
	private FileImageChooserFactory fileImageChooserFactory;
	private Supplier<Image> creationCenterFigure;

	public CreationSpecificImageHandler(CreationImageService creationImageService, ImageIOService imageIOService,
			FileImageChooserFactory fileImageChooserFactory, Supplier<Image> specificCreation) {
		this.creationImageService = creationImageService;
		this.imageIOService = imageIOService;
		this.fileImageChooserFactory = fileImageChooserFactory;
		this.creationCenterFigure = specificCreation;
	}

	@Override
	public void handle(ActionEvent event) {
		Stage stage = new Stage();
		AnchorPane anchorPane = new AnchorPane();
		ImageView imageView = new ImageView();
		Menu fileMenu = new Menu("File");
		MenuBar menuBar = new MenuBar(fileMenu);
		configImageViewAndSetImage(imageView, menuBar);
		MenuItem saveMenuItem = createSaveMenuItem(imageView);
		fileMenu.getItems().addAll(saveMenuItem);
		anchorPane.getChildren().addAll(imageView, menuBar);
		Scene scene = new Scene(anchorPane);
		stage.setScene(scene);
		stage.show();
	}

	private void configImageViewAndSetImage(ImageView imageView, MenuBar menuBar) {
		imageView.setLayoutY(menuBar.getLayoutY() + 30);
		imageView.autosize();
		Image writableImage = creationCenterFigure.get();
		imageView.setImage(writableImage);
	}

	private MenuItem createSaveMenuItem(ImageView imageView) {
		MenuItem saveMenuItem = new MenuItem("Save");
		FileChooser fileChooser = fileImageChooserFactory.create("Save image");
		saveMenuItem.setOnAction(new SaveImageEventHandler(fileChooser, imageView, imageIOService));
		return saveMenuItem;
	}
}