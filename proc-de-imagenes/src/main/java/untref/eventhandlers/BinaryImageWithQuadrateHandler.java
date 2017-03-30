package untref.eventhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import untref.service.CreationImageService;

public class BinaryImageWithQuadrateHandler implements EventHandler<ActionEvent> {

	private CreationImageService creationImageService;

	public BinaryImageWithQuadrateHandler (CreationImageService creationImageService){
		this.creationImageService = creationImageService;
	}

	@Override
	public void handle(ActionEvent event) {
		Stage stage = new Stage();
		AnchorPane anchorPane = new AnchorPane();
		ImageView imageView = new ImageView();
		int width = 200;
		int height = 200;
		imageView.setFitWidth(width);
		imageView.setFitHeight(height);
		Image writableImage = creationImageService.createBinaryImageWithCenterQuadrate(width, height);
		imageView.setImage(writableImage);
		anchorPane.getChildren().add(imageView);
		Scene scene = new Scene(anchorPane);
		stage.setScene(scene);
		stage.show();
	}
}