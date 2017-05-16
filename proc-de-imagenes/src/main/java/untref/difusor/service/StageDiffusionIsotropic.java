package untref.difusor.service;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class StageDiffusionIsotropic {
	
		double sigma = 0;
		int repeat = 0;
		Image image;
 WritableImage resutl;
		public int getRepeat() {
			return repeat;
		}

		public double getSigma() {
			return sigma;
		}

		public void start(Image image, ImageView imageViewResult) {
			Stage secondaryStage = new Stage();
			Group root = new Group();
			Scene scene = new Scene(root, 250, 100, Color.WHITE);
			 
			Label numberRepeat = new Label();
			numberRepeat.setLayoutX(40);
			numberRepeat.setLayoutY(10);
			 
			numberRepeat.setText("Number Repeat");
			Button buttonAccept = new Button();
			buttonAccept.setLayoutX(100);
			buttonAccept.setLayoutY(70);
			buttonAccept.setText("Accept");

			 
			TextField fieldRepeat = new TextField();			 
			fieldRepeat.setLayoutX(40);
			fieldRepeat.setLayoutY(30);

			buttonAccept.setOnAction(new EventHandler<ActionEvent>() {
				

				@Override
				public void handle(ActionEvent event) {
					 
					repeat = Integer.parseInt(fieldRepeat.getText());
					ApplyBroadcastIsotropic isotropic= new ApplyBroadcastIsotropic();
					imageViewResult.setImage(isotropic.difussion(image, repeat));
					secondaryStage.close();
					

				}
			});

			root.getChildren().addAll(  numberRepeat, fieldRepeat, buttonAccept);
			root.autosize();

			secondaryStage.setTitle("Isotropic Stage");
			secondaryStage.setScene(scene);
			
			secondaryStage.show();

		}
	}


