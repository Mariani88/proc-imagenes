package untref.controllers;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import untref.eventHandlers.ClickImagePixelValues;
import untref.interfacebuilders.ImageViewBuilder;
import untref.interfacebuilders.MenuBarBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrincipalGraphicInterfaceController {

	private AnchorPane principalPane;
	private MenuBar menuBar;
	private ImageView imageView;
	private ImageView imageResultView;
	private VBox imageData;
	private List<Node> principalPaneChildrens;
	private ImageDataController imageDataController;

	public PrincipalGraphicInterfaceController() {
		imageDataController = new ImageDataController();
	}

	public AnchorPane initInterfaceElements() {
		principalPane = new AnchorPane();
		imageView = createImageView(principalPane);
		imageResultView = createImageResultView(imageView);
		menuBar = new MenuBarBuilder().build(imageView);
		imageData = imageDataController.build(imageView, imageResultView);
		principalPaneChildrens = new ArrayList<Node>();
		principalPaneChildrens.addAll(Arrays.asList(menuBar, imageView, imageResultView, imageData));
		principalPane.getChildren().addAll(principalPaneChildrens);
		imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new ClickImagePixelValues(imageView, imageDataController));
		imageResultView.addEventHandler(MouseEvent.MOUSE_CLICKED, new ClickImagePixelValues(imageResultView, imageDataController));
		return principalPane;
	}

	private ImageView createImageView(AnchorPane principalPane) {
		return new ImageViewBuilder("default.jpg").withPreserveRatio(true).withFitWidth(500).withFitHeight(500).withVisible(true)
				.withX(principalPane.getLayoutX() + 50).withY(principalPane.getLayoutY() + 150).withAutosize().build();
	}

	private ImageView createImageResultView(ImageView imageView) {
		return new ImageViewBuilder("default.jpg").withPreserveRatio(true).withFitWidth(500).withFitHeight(500).withVisible(true)
				.withX(imageView.getLayoutX() + imageView.getFitWidth() + 20).withY(imageView.getLayoutY()).withAutosize().build();
	}
}