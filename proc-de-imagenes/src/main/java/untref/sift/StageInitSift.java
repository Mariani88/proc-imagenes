package untref.sift;

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

public class StageInitSift {

	int thetaMin = 0;
	int thetaMax = 0;
	int minimunPoint = 0;
	Image image;
	WritableImage resutl;

	public void start(Image image, Image imageResult) {
		Stage secondaryStage = new Stage();
		Group root = new Group();
		Scene scene = new Scene(root, 250, 300, Color.WHITE);

		Label labelQuantityIterations = new Label();
		labelQuantityIterations.setLayoutX(40);
		labelQuantityIterations.setLayoutY(10);
		labelQuantityIterations.setText("Quantity Iterations");

		Label labelPercentageStop = new Label();
		labelPercentageStop.setLayoutX(40);
		labelPercentageStop.setLayoutY(60);
		labelPercentageStop.setText("Percentage Stop");

		Label labelLimitEstimator = new Label();
		labelLimitEstimator.setLayoutX(40);
		labelLimitEstimator.setLayoutY(110);
		labelLimitEstimator.setText("Limit Estimator");

		Label labelLimitComparator = new Label();
		labelLimitComparator.setLayoutX(40);
		labelLimitComparator.setLayoutY(160);
		labelLimitComparator.setText("Limit Comparator");

		Button buttonAccept = new Button();
		buttonAccept.setLayoutX(100);
		buttonAccept.setLayoutY(260);
		buttonAccept.setText("Accept");

		TextField fieldIterations = new TextField();
		TextField fieldStop = new TextField();
		TextField fieldComparations = new TextField();
		TextField fieldEstimator = new TextField();

		fieldIterations.resize(5, 5);
		fieldIterations.setLayoutX(40);
		fieldIterations.setLayoutY(30);
		fieldStop.setLayoutX(40);
		fieldStop.setLayoutY(80);
		fieldComparations.setLayoutX(40);
		fieldComparations.setLayoutY(130);

		fieldEstimator.resize(5, 5);
		fieldEstimator.setLayoutX(40);
		fieldEstimator.setLayoutY(180);

		fieldIterations.setText("1500");
		fieldStop.setText("0.5");

		fieldComparations.setText("8");

		fieldEstimator.setText("0.5");

		buttonAccept.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				Sift sift = new Sift();
				sift.ingresarValores(Integer.parseInt(fieldIterations.getText()),
						Double.parseDouble(fieldEstimator.getText()), Double.parseDouble(fieldStop.getText()),
						Integer.parseInt(fieldComparations.getText()));

				try {
					sift.aplicar(SwingFXUtils.fromFXImage(image, null), SwingFXUtils.fromFXImage(imageResult, null));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});

		root.getChildren().addAll(labelQuantityIterations, labelPercentageStop, labelLimitEstimator, fieldIterations,
				fieldStop, fieldComparations, labelLimitComparator, fieldEstimator, buttonAccept);
		root.autosize();

		secondaryStage.setTitle("Sift - Stage");
		secondaryStage.setScene(scene);

		secondaryStage.show();

	}

}
