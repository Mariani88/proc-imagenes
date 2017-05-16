package untref.filters.services;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import untref.controllers.DrawingSelect;
import untref.edge.service.EdgeHighPass;


public class StageMaskService {
	int sizeMask = 0;

	public int getSizeMask() {
		return sizeMask;
	}

	public void startMedia(boolean mediana, Image image, ImageView imageResultView, boolean isMedianWeighted, boolean isHighPass, boolean isGaussian) {
		Stage secondaryStage = new Stage();
		Group root = new Group();
		Scene scene = new Scene(root, 100, 100, Color.WHITE);

		Button buttonAccept = new Button();
		buttonAccept.setLayoutX(30);
		buttonAccept.setLayoutY(50);
		buttonAccept.setText("Accept");

		TextField field = new TextField() {
			@Override
			public void replaceText(int start, int end, String text) {
				if (!text.matches("[a-z]")) {
					super.replaceText(start, end, text);
				}
			}

			@Override
			public void replaceSelection(String text) {
				if (!text.matches("[a-z]")) {
					super.replaceSelection(text);
				}
			}
		};
field.resize(5, 5);
		buttonAccept.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				sizeMask = Integer.parseInt(field.getText());
				secondaryStage.close();

				
				if(isGaussian){
					
					GaussFilterService gaussianFilter=new GaussFilterService();
					
					imageResultView.setImage(gaussianFilter.getImageFilterGussian(image, sizeMask));
				}
				else
				
				if (isHighPass){
					
					EdgeHighPass highPass=new EdgeHighPass();
					imageResultView.setImage(highPass.startBorde(image, sizeMask));
				}
				else
				
				if (isMedianWeighted){
					 
					
					WeightedMedian medianaFilter= new WeightedMedian();
					imageResultView.setImage(medianaFilter.getImageMediana(image, sizeMask));
					
					}
					
				else
				if (mediana){
					 MedianFilterService medianaFilter = new MedianFilterService();

					imageResultView.setImage(medianaFilter.getImageMediana(image, sizeMask));
					
					}else
					{
						MediaFilterService mediaFilter = new MediaFilterService();
				imageResultView.setImage(mediaFilter.getImageMedia(image, sizeMask));
					}
				

			}
		});
		root.getChildren().addAll(field, buttonAccept);
		root.autosize();

		secondaryStage.setTitle("Value Mask");
		secondaryStage.setScene(scene);
		secondaryStage.sizeToScene();
		secondaryStage.show();
	}
}
