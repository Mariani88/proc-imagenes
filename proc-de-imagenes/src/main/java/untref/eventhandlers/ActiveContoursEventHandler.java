package untref.eventhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import untref.controllers.OpenMenuController;
import untref.controllers.PixelPaneController;
import untref.controllers.nodeutils.ImageSetter;
import untref.controllers.nodeutils.settertype.FullSetter;
import untref.interfacebuilders.ImageViewBuilder;
import untref.service.activecontours.ActiveContoursService;
import untref.service.activecontours.ActiveContoursServiceImpl;

import static untref.domain.utils.ImageValuesTransformer.toInt;

public class ActiveContoursEventHandler implements EventHandler<ActionEvent> {

	private static final String FIRST_PIXEL = "first pixel   ";
	private static final String SECOND_PIXEL = "second pixel";
	private final OpenMenuController openMenuController;
	private final PixelPaneController firstPixelPaneController;
	private final PixelPaneController secondPixelPaneController;
	private final ActiveContoursService activeContoursService;

	public ActiveContoursEventHandler(OpenMenuController openMenuController) {
		this.openMenuController = openMenuController;
		firstPixelPaneController = new PixelPaneController(FIRST_PIXEL);
		secondPixelPaneController = new PixelPaneController(SECOND_PIXEL);
		activeContoursService = new ActiveContoursServiceImpl();
	}

	@Override
	public void handle(ActionEvent event) {
		generateActiveContoursWindows();
	}

	private void generateActiveContoursWindows() {
		Stage stage = new Stage();
		stage.setTitle("active contours for one image");
		VBox pane = new VBox();
		pane.setMinWidth(700);
		pane.setMinHeight(800);

		ImageView imageView = new ImageViewBuilder("default.jpg").withPreserveRatio(true).withAutosize().withVisible(true).withImageSize().build();

		imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				int x = toInt(event.getX());
				int y = toInt(event.getY());

				if (!firstPixelPaneController.setedValues()) {
					firstPixelPaneController.setValues(x, y);
				} else if (!secondPixelPaneController.setedValues()) {
					secondPixelPaneController.setValues(x, y);
					Image imageWithContours = activeContoursService
							.initializeActiveContours(imageView.getImage(), firstPixelPaneController.getPosition(),
									secondPixelPaneController.getPosition());
					ImageSetter.setWithImageSize(imageView, imageWithContours);
				} else {
					firstPixelPaneController.clearValues();
					secondPixelPaneController.clearValues();
				}
			}
		});

		Menu openImage = new Menu("open");
		openImage.getItems().addAll(openMenuController.createOpenMenuItem(imageView, new FullSetter()));
		MenuBar menuBar = new MenuBar(openImage);

		HBox cordinates = new HBox();

		VBox firstPixelPane = firstPixelPaneController.getPane();
		VBox secondPixelPane = secondPixelPaneController.getPane();
		Button applyContours = new Button(" apply contours");
		cordinates.getChildren().addAll(firstPixelPane, secondPixelPane);
		pane.getChildren().addAll(menuBar, imageView, cordinates, applyContours);
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();
	}
}