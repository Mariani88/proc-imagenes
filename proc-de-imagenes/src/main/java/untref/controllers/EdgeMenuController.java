package untref.controllers;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import untref.domain.edgedetectionoperators.firstderivative.PrewittOperator;
import untref.domain.edgedetectionoperators.firstderivative.RobertOperator;
import untref.domain.edgedetectionoperators.firstderivative.SobelOperator;
import untref.domain.edgedetectionoperators.secondderivative.LaplacianOperator;
import untref.domain.edgedetectionoperators.secondderivative.LaplacianWithVarianceEvaluationOperator;
import untref.domain.edgedetectionoperators.secondderivative.MarrHildrethOperator;
import untref.eventhandlers.CreateHighPassEdgeHandler;
import untref.eventhandlers.EdgeDetectorWithFirstDerivateEventHandler;
import untref.eventhandlers.EditionImageEventHandler;
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
		Menu byFirstDerivative = createByFirstDerivativeMenu(imageView, imageResultView);
		Menu bySecondDerivative = createBySecondDerivativeMenu(imageView, imageResultView);
		edgeDetection.getItems().addAll(byFirstDerivative, bySecondDerivative);
		edgeMenu.getItems().addAll(highPass, edgeDetection);
		return edgeMenu;
	}

	private Menu createBySecondDerivativeMenu(ImageView imageView, ImageView imageResultView) {
		Menu bySecondDerivative = new Menu("by second derivative");
		MenuItem laplacianMethod = new MenuItem("laplacian method");
		MenuItem laplacianMethodWithVarianceEvaluation = new MenuItem("laplacian method with vaciance evaluation");
		MenuItem marrHildrethMethod = new MenuItem("Gaussian laplacian method (Marr Hildreth)");
		laplacianMethod.setOnAction(
				new EditionImageEventHandler(imageResultView, () -> edgeDetectionService.detectEdge(imageView.getImage(), new LaplacianOperator())));
		laplacianMethodWithVarianceEvaluation.setOnAction(new EditionImageEventHandler(imageResultView,
				() -> edgeDetectionService.detectEdge(imageView.getImage(), new LaplacianWithVarianceEvaluationOperator())));
		marrHildrethMethod.setOnAction(new EditionImageEventHandler(imageResultView,
				() -> edgeDetectionService.detectEdge(imageView.getImage(), new MarrHildrethOperator())));
		bySecondDerivative.getItems().addAll(laplacianMethod, laplacianMethodWithVarianceEvaluation, marrHildrethMethod);
		return bySecondDerivative;
	}

	private Menu createByFirstDerivativeMenu(ImageView imageView, ImageView imageResultView) {
		Menu byFirstDerivative = new Menu("by first derivative");
		MenuItem byRobertOperator = new MenuItem("by Robert operator");
		byRobertOperator
				.setOnAction(new EdgeDetectorWithFirstDerivateEventHandler(imageView, imageResultView, edgeDetectionService, new RobertOperator()));
		MenuItem byPrewittOperator = new MenuItem(" by Prewitt operator");
		byPrewittOperator
				.setOnAction(new EdgeDetectorWithFirstDerivateEventHandler(imageView, imageResultView, edgeDetectionService, new PrewittOperator()));
		MenuItem bySobelOperator = new MenuItem(" by Sobel operator");
		bySobelOperator
				.setOnAction(new EdgeDetectorWithFirstDerivateEventHandler(imageView, imageResultView, edgeDetectionService, new SobelOperator()));
		byFirstDerivative.getItems().addAll(byRobertOperator, byPrewittOperator, bySobelOperator);
		return byFirstDerivative;
	}
}