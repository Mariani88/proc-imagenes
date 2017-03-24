package untref;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import untref.interfacebuilders.ImageViewBuilder;
import untref.interfacebuilders.TextFieldBuilder;
import untref.interfacebuilders.MenuBarBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrincipalGraphicInterfaceController {

	public AnchorPane initInterfaceElements() {

		AnchorPane principalPane = new AnchorPane();

		final ImageView imageView = createImageView(principalPane);
		final ImageView imageResultView = createImageResultView(imageView);
		MenuBarBuilder menuBar = new MenuBarBuilder(imageView,imageResultView);
		/*
		 * imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new
		 * EventHandler<MouseEvent>() { public void handle(MouseEvent event) {
		 * double x = event.getX();
		 * 
		 * System.out.println(x); double y = event.getY();
		 * System.out.println(y); Color color =
		 * imageView.getImage().getPixelReader().getColor((int) x, (int) y);
		 * 
		 * } });
		 */

		VBox imageData = new VBox();
		imageData.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		imageData.setLayoutX(imageResultView.getLayoutX() + imageResultView.getFitWidth() + 15);
		imageData.setLayoutY(imageResultView.getLayoutY());

		Label imageDataTitle = new Label("IMAGE DATA");
		Label label1 = new Label("text field1");
		Label label2 = new Label("text field2");
		TextField textField1 = new TextFieldBuilder().withEditable(false).withAutosize().build();
		TextField textField2 = new TextFieldBuilder().withEditable(false).withAutosize().build();
		imageData.getChildren().addAll(Arrays.asList(imageDataTitle, label1, textField1, label2, textField2));
		List<Node> principalPaneChildrens = new ArrayList<Node>();
		principalPaneChildrens.addAll(Arrays.asList(menuBar.getMenuBar(), imageView, imageResultView, imageData));
		principalPane.getChildren().addAll(principalPaneChildrens);
		return principalPane;
	}

	private ImageView createImageView(AnchorPane principalPane) {
		return new ImageViewBuilder("default.jpg").withPreserveRatio(true).withFitWidth(500).withFitHeight(500)
				.withVisible(true).withX(principalPane.getLayoutX() + 50).withY(principalPane.getLayoutY() + 150)
				.withAutosize().build();
	}

	private ImageView createImageResultView(ImageView imageView) {
		return new ImageViewBuilder("default.jpg").withPreserveRatio(true).withFitWidth(500).withFitHeight(500)
				.withVisible(true).withX(imageView.getLayoutX() + imageView.getFitWidth() + 20)
				.withY(imageView.getLayoutY()).withAutosize().build();
	}

}