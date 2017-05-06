package untref.controllers;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import untref.domain.edgedetectionoperators.PrewittOperator;
import untref.domain.edgedetectionoperators.RobertOperator;
import untref.domain.edgedetectionoperators.SobelOperator;
import untref.eventhandlers.CreateHighPassEdgeHandler;
import untref.eventhandlers.EdgeDetectorWithFirstDerivateEventHandler;
import untref.service.EdgeDetectionService;

public class EdgeMenuController {

	private final EdgeDetectionService edgeDetectionService;

	public EdgeMenuController(EdgeDetectionService edgeDetectionService) {
		this.edgeDetectionService = edgeDetectionService;
	}

	public Menu createEdgeMenu(ImageView imageView, ImageView imageResultView) {
		Menu edgeMenu = new Menu("Edge");
		MenuItem highPass = new MenuItem("high Pass");
		highPass.setOnAction(new CreateHighPassEdgeHandler(imageView, imageResultView));
		Menu edgeDetection = new Menu("edges detection");
		MenuItem byRobertOperator = new MenuItem("by Robert operator");
		byRobertOperator
				.setOnAction(new EdgeDetectorWithFirstDerivateEventHandler(imageView, imageResultView, edgeDetectionService, new RobertOperator()));
		MenuItem byPrewittOperator = new MenuItem(" by Prewitt operator");
		byPrewittOperator
				.setOnAction(new EdgeDetectorWithFirstDerivateEventHandler(imageView, imageResultView, edgeDetectionService, new PrewittOperator()));
		MenuItem bySobelOperator = new MenuItem(" by Sobel operator");
		bySobelOperator
				.setOnAction(new EdgeDetectorWithFirstDerivateEventHandler(imageView, imageResultView, edgeDetectionService, new SobelOperator()));
		edgeDetection.getItems().addAll(byRobertOperator, byPrewittOperator, bySobelOperator);
		edgeMenu.getItems().addAll(highPass, edgeDetection);
		return edgeMenu;
	}
}