package untref.eventhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class BinaryImageWithQuadrateHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		Stage stage = new Stage();
		AnchorPane anchorPane = new AnchorPane();
		ImageView imageView = new ImageView();

		int width = 200;
		int height = 200;

		imageView.setFitWidth(width);
		imageView.setFitHeight(height);

		WritableImage writableImage = new WritableImage(width, height);
		PixelWriter pixelWriter = writableImage.getPixelWriter();

		for (int i = 0; i < width; i++) {
			for (int j = 0; i < height; i++) {
				pixelWriter.setArgb(i, j, 0);
			}
		}

		for(int i = 67; i <= 134; i++){
			for(int j = 67; j <= 134; j++){
				pixelWriter.setArgb(i, j, -0xFFFFFF);
			}
		}

		imageView.setImage(writableImage);
		anchorPane.getChildren().add(imageView);
		Scene scene = new Scene(anchorPane);
		stage.setScene(scene);
		stage.show();

	}
}