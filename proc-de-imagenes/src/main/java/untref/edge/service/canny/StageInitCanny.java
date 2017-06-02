package untref.edge.service.canny;

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

public class StageInitCanny {
	
	
	double sigma = 0;
	int thresholdLow = 0;
	int thresholdHigh = 0;
	Image image;
WritableImage resutl;
	 

	public void start(Image image, ImageView imageViewResult) {
		Stage secondaryStage = new Stage();
		Group root = new Group();
		Scene scene = new Scene(root, 250, 200, Color.WHITE);
		
		Label sigmaLabel = new Label();
		sigmaLabel.setLayoutX(40);
		sigmaLabel.setLayoutY(10);
		sigmaLabel.setText("Sigma");
		
		Label labelThresholdLow = new Label();
		labelThresholdLow.setLayoutX(40);
		labelThresholdLow.setLayoutY(60);
		labelThresholdLow.setText("Threshold Low");

		Label labelThresholdHigh = new Label();
		labelThresholdHigh.setLayoutX(40);
		labelThresholdHigh.setLayoutY(110);
		labelThresholdHigh.setText("Threshold High");

		
		Button buttonAccept = new Button();
		buttonAccept.setLayoutX(100);
		buttonAccept.setLayoutY(160);
		buttonAccept.setText("Accept");

		TextField fieldSigma = new TextField();
		TextField fieldThresholdLow = new TextField();
		TextField fieldThresholdHigh = new TextField();
		
		fieldSigma.resize(5, 5);
		fieldSigma.setLayoutX(40);
		fieldSigma.setLayoutY(30);
		fieldThresholdLow.setLayoutX(40);
		fieldThresholdLow.setLayoutY(80);
		fieldThresholdHigh.setLayoutX(40);
		fieldThresholdHigh.setLayoutY(130);
		
		buttonAccept.setOnAction(new EventHandler<ActionEvent>() {
			

			@Override
			public void handle(ActionEvent event) {
				sigma = Double.parseDouble(fieldSigma.getText());
				thresholdLow = Integer.parseInt(fieldThresholdLow.getText());
				thresholdHigh = Integer.parseInt(fieldThresholdHigh.getText());
				
				Canny canny=new Canny();
				imageViewResult.setImage(canny.startCanny(image, (int) sigma,thresholdLow,thresholdHigh));
				secondaryStage.close();
				

			}
		});

		root.getChildren().addAll(sigmaLabel, fieldSigma, labelThresholdLow, fieldThresholdLow, labelThresholdHigh, fieldThresholdHigh, buttonAccept);
		root.autosize();

		secondaryStage.setTitle("Value Mask");
		secondaryStage.setScene(scene);
		
		secondaryStage.show();

	}

}