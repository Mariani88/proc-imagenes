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

public class StageDiffusionAnisotropic {
	
	
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
		Scene scene = new Scene(root, 250, 150, Color.WHITE);
		Label sigmaLabel = new Label();
		sigmaLabel.setLayoutX(40);
		sigmaLabel.setLayoutY(10);
		Label numberRepeat = new Label();
		numberRepeat.setLayoutX(40);
		numberRepeat.setLayoutY(60);
		sigmaLabel.setText("Sigma");
		numberRepeat.setText("Number Repeat");
		Button buttonAccept = new Button();
		buttonAccept.setLayoutX(100);
		buttonAccept.setLayoutY(110);
		buttonAccept.setText("Accept");

		TextField fieldSigma = new TextField();
		TextField fieldRepeat = new TextField();
		
		
		fieldSigma.resize(5, 5);
		fieldSigma.setLayoutX(40);
		fieldSigma.setLayoutY(30);
		fieldRepeat.setLayoutX(40);
		fieldRepeat.setLayoutY(80);

		buttonAccept.setOnAction(new EventHandler<ActionEvent>() {
			

			@Override
			public void handle(ActionEvent event) {
				sigma = Double.parseDouble(fieldSigma.getText());
				repeat = Integer.parseInt(fieldRepeat.getText());
				ApplyBroadcastAnisotropic isotropic= new ApplyBroadcastAnisotropic();
				imageViewResult.setImage(isotropic.difussion(image, repeat, sigma));
				secondaryStage.close();
				

			}
		});

		root.getChildren().addAll(sigmaLabel, fieldSigma, numberRepeat, fieldRepeat, buttonAccept);
		root.autosize();

		secondaryStage.setTitle("Value Mask");
		secondaryStage.setScene(scene);
		
		secondaryStage.show();

	}

}
