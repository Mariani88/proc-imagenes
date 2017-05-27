package untref.controllers;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import untref.controllers.nodeutils.ParametersWindowsFactory;
import untref.domain.edge.detectors.AllEdges;
import untref.domain.edge.detectors.HorizontalEdge;
import untref.domain.edge.detectors.VerticalEdge;
import untref.domain.edge.edgedetectionoperators.firstderivative.PrewittOperator;
import untref.domain.edge.edgedetectionoperators.firstderivative.RobertOperator;
import untref.domain.edge.edgedetectionoperators.firstderivative.SobelOperator;
import untref.domain.edge.edgedetectionoperators.secondderivative.detectors.CrossByZeroDetector;
import untref.domain.susan.SusanCorner;
import untref.domain.susan.SusanEdge;
import untref.eventhandlers.*;
import untref.service.EdgeDetectionService;
import untref.service.boundary.BoundaryDetectionBySusanService;
import untref.service.boundary.BoundaryDetectionBySusanServiceImpl;

public class EdgeMenuController {

	private final EdgeDetectionService edgeDetectionService;
	private final BoundaryDetectionBySusanService boundaryDetectionBySusanService;

	public EdgeMenuController(EdgeDetectionService edgeDetectionService) {
		this.edgeDetectionService = edgeDetectionService;
		this.boundaryDetectionBySusanService = new BoundaryDetectionBySusanServiceImpl();
	}

	public Menu createEdgeMenu(ImageView imageView, ImageView imageResultView) {
		Menu edgeMenu = new Menu("Edge");
		MenuItem highPass = new MenuItem("high Pass");
		highPass.setOnAction(new CreateHighPassEdgeHandler(imageView, imageResultView));
		Menu edgeDetection = new Menu("edges detection");
		Menu byFirstDerivative = createByFirstDerivativeMenu(imageView, imageResultView);
		Menu bySecondDerivative = createBySecondDerivativeMenu(imageView, imageResultView);
		Menu susanDetector = createSusanMenu(imageView, imageResultView);
		edgeDetection.getItems().addAll(byFirstDerivative, bySecondDerivative, susanDetector);
		edgeMenu.getItems().addAll(highPass, edgeDetection);
		return edgeMenu;
	}

	private Menu createSusanMenu(ImageView imageView, ImageView imageResultView) {
		Menu susanDetector = new Menu("SUSAN detector");
		MenuItem detectEdges = new MenuItem("detect edges");
		MenuItem detectCorners = new MenuItem("detect corners");
		detectEdges.setOnAction(new SusanEventHandler(imageView, imageResultView, boundaryDetectionBySusanService, new SusanEdge()));
		detectCorners.setOnAction(new SusanEventHandler(imageView, imageResultView, boundaryDetectionBySusanService, new SusanCorner()));
		susanDetector.getItems().addAll(detectEdges,detectCorners);
		return susanDetector;
	}

	private Menu createBySecondDerivativeMenu(ImageView imageView, ImageView imageResultView) {
		Menu bySecondDerivative = new Menu("by second derivative");
		MenuItem laplacianMethod = new MenuItem("laplacian method with cross by zero");
		MenuItem laplacianMethodWithVarianceSlope = new MenuItem("laplacian method with slope evaluation");
		MenuItem marrHildrethMethod = new MenuItem("Gaussian laplacian method (Marr Hildreth) with cross by zero");
		MenuItem marrHildrethMethodWithVarianceSlope = new MenuItem("Marr Hildreth with variance slope");
		laplacianMethod.setOnAction(new EditionImageEventHandler(imageResultView,
				() -> edgeDetectionService.detectEdgeWithLaplacian(imageView.getImage(), new CrossByZeroDetector())));
		laplacianMethodWithVarianceSlope
				.setOnAction(new EdgeDetectionWithLaplacianSlopeEvaluationEventHandler(imageView, imageResultView, edgeDetectionService));
		marrHildrethMethod.setOnAction(new EdgeDetectionWithMarrHildrethCrossByZeroEventHandler(imageView, imageResultView, edgeDetectionService));
		marrHildrethMethodWithVarianceSlope
				.setOnAction(new EdgeDetectionWithMarrHildrethSlopeEvaluationEventHandler(imageView, imageResultView, edgeDetectionService));
		bySecondDerivative.getItems()
				.addAll(laplacianMethod, laplacianMethodWithVarianceSlope, marrHildrethMethod, marrHildrethMethodWithVarianceSlope);
		return bySecondDerivative;
	}

	private Menu createByFirstDerivativeMenu(ImageView imageView, ImageView imageResultView) {
		Menu byFirstDerivative = new Menu("by first derivative");
		MenuItem byRobertOperator = new MenuItem("by Robert operator");
		byRobertOperator.setOnAction(
				new EdgeDetectorWithFirstDerivateEventHandler(imageView, imageResultView, new AllEdges(edgeDetectionService, new RobertOperator())));
		MenuItem byPrewittOperator = new MenuItem(" by Prewitt operator");
		byPrewittOperator.setOnAction(
				new EdgeDetectorWithFirstDerivateEventHandler(imageView, imageResultView, new AllEdges(edgeDetectionService, new PrewittOperator()
				)));
		MenuItem bySobelOperator = new MenuItem(" by Sobel operator");
		bySobelOperator.setOnAction(
				new EdgeDetectorWithFirstDerivateEventHandler(imageView, imageResultView, new AllEdges(edgeDetectionService, new SobelOperator())));
		Menu uniqueEdge = new Menu("Unique edge");
		MenuItem horizontalEdge = new MenuItem("horizontal edge");
		horizontalEdge.setOnAction(new EdgeDetectorWithFirstDerivateEventHandler(imageView, imageResultView,
				new HorizontalEdge(edgeDetectionService, new PrewittOperator())));
		MenuItem verticalEdge = new MenuItem("vertical edge");
		verticalEdge.setOnAction(new EdgeDetectorWithFirstDerivateEventHandler(imageView, imageResultView,
				new VerticalEdge(edgeDetectionService, new PrewittOperator())));
		uniqueEdge.getItems().addAll(horizontalEdge, verticalEdge);
		byFirstDerivative.getItems().addAll(byRobertOperator, byPrewittOperator, bySobelOperator, uniqueEdge);
		return byFirstDerivative;
	}
}