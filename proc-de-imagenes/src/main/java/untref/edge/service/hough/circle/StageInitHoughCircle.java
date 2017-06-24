package untref.edge.service.hough.circle;

import javafx.embed.swing.SwingFXUtils;
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

public class StageInitHoughCircle {

	int thetaMin = 0;
	int thetaMax = 0;
	int minimunPoint = 0;
	Image image;
	WritableImage resutl;

	public void start(Image image, ImageView imageViewResult) {
		Stage secondaryStage = new Stage();
		Group root = new Group();
		Scene scene = new Scene(root, 250, 300, Color.WHITE);

		Label sigmaLabel = new Label();
		sigmaLabel.setLayoutX(40);
		sigmaLabel.setLayoutY(10);
		sigmaLabel.setText("Sigma - Canny");

		Label labelThresholdLow = new Label();
		labelThresholdLow.setLayoutX(40);
		labelThresholdLow.setLayoutY(60);
		labelThresholdLow.setText("Threshold Low - Canny");

		Label labelThresholdHigh = new Label();
		labelThresholdHigh.setLayoutX(40);
		labelThresholdHigh.setLayoutY(110);
		labelThresholdHigh.setText("Threshold High - Canny");

		Label labelRadius = new Label();
		labelRadius.setLayoutX(40);
		labelRadius.setLayoutY(160);
		labelRadius.setText("Radius");

		Label labelPointMinimun = new Label();
		labelPointMinimun.setLayoutX(40);
		labelPointMinimun.setLayoutY(210);
		labelPointMinimun.setText("Point minimun");

		Button buttonAccept = new Button();
		buttonAccept.setLayoutX(100);
		buttonAccept.setLayoutY(260);
		buttonAccept.setText("Accept");

		TextField fieldSigma = new TextField();
		TextField fieldThresholdLow = new TextField();
		TextField fieldThresholdHigh = new TextField();
		TextField fieldRadius = new TextField();
		TextField fieldPointMinimun = new TextField();

		fieldSigma.resize(5, 5);
		fieldSigma.setLayoutX(40);
		fieldSigma.setLayoutY(30);
		fieldThresholdLow.setLayoutX(40);
		fieldThresholdLow.setLayoutY(80);
		fieldThresholdHigh.setLayoutX(40);
		fieldThresholdHigh.setLayoutY(130);

		fieldRadius.resize(5, 5);
		fieldRadius.setLayoutX(40);
		fieldRadius.setLayoutY(180);

		fieldPointMinimun.setLayoutX(40);
		fieldPointMinimun.setLayoutY(230);
		
		
		
		fieldSigma.setText("2");
		fieldThresholdLow.setText("100");
	
		fieldThresholdHigh.setText("130");
	

		fieldRadius.setText("20");
		
	

		fieldPointMinimun.setText("30");
		
		

		buttonAccept.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				

				HoughCircleDetector houghCircle = new HoughCircleDetector();
				houghCircle.setConfigHoughCircle(Integer.parseInt(fieldRadius.getText()),
						Float.parseFloat(fieldPointMinimun.getText()));
				houghCircle.setEdgeCanny(image, Integer.parseInt(fieldThresholdLow.getText()),
						Integer.parseInt(fieldThresholdHigh.getText()), Float.parseFloat(fieldSigma.getText()));

				Image imageOut;
				try {
					imageOut = SwingFXUtils.toFXImage(houghCircle.detectCircles(image), null);
					imageViewResult.setImage(imageOut);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				imageViewResult.setFitWidth(image.getWidth());
				imageViewResult.setFitHeight(image.getHeight());

				secondaryStage.close();

			}

		});

		root.getChildren().addAll(sigmaLabel, labelThresholdLow, labelThresholdHigh, fieldSigma, fieldThresholdLow,
				fieldThresholdHigh, labelPointMinimun, labelRadius, fieldRadius, fieldPointMinimun,
				buttonAccept);
		root.autosize();

		secondaryStage.setTitle("Hough - Stage");
		secondaryStage.setScene(scene);

		secondaryStage.show();

	}

}
