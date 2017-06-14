package untref.edge.service.hough;

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

public class StageInitHough {

	int thetaMin = 0;
	int thetaMax = 0;
	int minimunPoint = 0;
	Image image;
	WritableImage resutl;

	public void start(Image image, ImageView imageViewResult) {
		Stage secondaryStage = new Stage();
		Group root = new Group();
		Scene scene = new Scene(root, 250, 300, Color.WHITE);

		Label thetaLabel = new Label();
		thetaLabel.setLayoutX(40);
		thetaLabel.setLayoutY(10);
		thetaLabel.setText("Theta Min");

		Label labelThetaMax = new Label();
		labelThetaMax.setLayoutX(40);
		labelThetaMax.setLayoutY(60);
		labelThetaMax.setText("Theta Max");

		Label labelPointMinimun = new Label();
		labelPointMinimun.setLayoutX(40);
		labelPointMinimun.setLayoutY(110);
		labelPointMinimun.setText("Point minimun");
		
		Label labelThetaDiscre = new Label();
		labelThetaDiscre.setLayoutX(40);
		labelThetaDiscre.setLayoutY(160);
		labelThetaDiscre.setText("Discretization Theta");
		
		Label labelRodiscrt = new Label();
		labelRodiscrt.setLayoutX(40);
		labelRodiscrt.setLayoutY(210);
		labelRodiscrt.setText("Discretization Ro");

		Button buttonAccept = new Button();
		buttonAccept.setLayoutX(100);
		buttonAccept.setLayoutY(260);
		buttonAccept.setText("Accept");

		TextField fieldtheta = new TextField();
		TextField fieldThetaMax = new TextField();
		TextField fieldPointMinimun = new TextField();
		
		TextField fieldThetaDiscre = new TextField();
		TextField fieldReDiscr = new TextField();

		fieldtheta.resize(5, 5);
		fieldtheta.setLayoutX(40);
		fieldtheta.setLayoutY(30);
		fieldThetaMax.setLayoutX(40);
		fieldThetaMax.setLayoutY(80);
		fieldPointMinimun.setLayoutX(40);
		fieldPointMinimun.setLayoutY(130);

		fieldThetaDiscre.setLayoutX(40);
		fieldThetaDiscre.setLayoutY(180);
		
		fieldReDiscr.setLayoutX(40);
		fieldReDiscr.setLayoutY(230);
		
		buttonAccept.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				//thetaMin = Integer.parseInt(fieldtheta.getText());
				thetaMax = Integer.parseInt(fieldThetaMax.getText());
				minimunPoint = Integer.parseInt(fieldPointMinimun.getText());
				HoughTransform houghRecta =new HoughTransform();
				houghRecta.setThetaMax(thetaMax);
				houghRecta.setPointMin(minimunPoint);
				 
				Image imageOut;
				try {
					imageOut = SwingFXUtils.toFXImage( houghRecta.start(image), null);
					imageViewResult.setImage(imageOut);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				
				
			/*	if ((image.getWidth() < 500)) {
					imageViewResult.setFitWidth(image.getWidth());
					imageViewResult.setFitHeight(image.getHeight());
				}/*/
				secondaryStage.close();

			}

		});

		root.getChildren().addAll( labelThetaMax, fieldThetaMax, labelPointMinimun,
				fieldPointMinimun, buttonAccept);
		root.autosize();

		secondaryStage.setTitle("Hough - Stage");
		secondaryStage.setScene(scene);

		secondaryStage.show();

	}

}
