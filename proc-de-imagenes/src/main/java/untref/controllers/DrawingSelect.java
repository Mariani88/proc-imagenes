package untref.controllers;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import untref.service.DrawRectangleSelection;
import untref.service.DrawRectangleSelectionImpl;
import untref.service.ImageGetColorRGB;
import untref.service.ImageGetColorRGBImpl;

public class DrawingSelect {

	DrawRectangleSelection seccionSelection;
	ImageView imageView;

	Stage secondaryStage;
	HBox textBox;

	Text textBandR;
	Text textBandG;
	Text textBandB;
	Text textAverageGrey;
	Text textTotalPixel;

	public void start(Image image) {

		this.secondaryStage = new Stage();

		secondaryStage.setTitle("Image Selection");

		BorderPane root = new BorderPane();

		TitledPane scrollPane = new TitledPane();

		final Group imageLayer = new Group();

		this.imageView = new ImageView(image);
		scrollPane.autosize();

		imageLayer.getChildren().add(imageView);
		imageLayer.autosize();

		scrollPane.setContent(imageLayer);
		scrollPane.autosize();

		root.setCenter(scrollPane);
		root.autosize();

		root.setTop(createHboxAverangeColorInformation());
		seccionSelection = new DrawRectangleSelectionImpl(imageLayer);

		final ContextMenu contextMenu = new ContextMenu();

		//MenuItem cropFileMenuItem = new MenuItem("Copy in new file");
		MenuItem cropWindowsMenuItem = new MenuItem("Copy in new windows");
		MenuItem averangeColorMenuItem = new MenuItem("Averange RGB and Gray");

	/*	cropFileMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {

				Bounds selectionBounds = seccionSelection.getBounds();
				cropSaveImage(selectionBounds);

			}
		});*/

		cropWindowsMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {

				Bounds selectionBounds = seccionSelection.getBounds();
				cropSaveWindowsImage(selectionBounds);

			}
		});

		averangeColorMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {

				Bounds selectionBounds = seccionSelection.getBounds();
				calculateAverange(selectionBounds);

			}
		});

		contextMenu.getItems().addAll(/*cropFileMenuItem,*/ cropWindowsMenuItem, averangeColorMenuItem);

		imageLayer.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				if (event.isSecondaryButtonDown()) {
					contextMenu.show(imageLayer, event.getScreenX(), event.getScreenY());
				}
			}
		});

		Scene scene = new Scene(root);

		secondaryStage.setScene(scene);
		secondaryStage.sizeToScene();
		secondaryStage.show();
	}

	private HBox createHboxAverangeColorInformation() {
		Label labelBandR = new Label("Averange Band R:");
		labelBandR.setTextFill(Color.RED);
		Label labelBandG = new Label("Averange Band G:");
		labelBandG.setTextFill(Color.GREEN);
		Label labelBandB = new Label("Averange Band B:");
		labelBandB.setTextFill(Color.BLUE);
		Label labelGray = new Label("Averange Gray:");
		labelGray.setTextFill(Color.GREY);
		Label labelTotalPixel = new Label("Total pixel:");
		textBandR = new Text("0");
		textBandG = new Text("0");
		textBandB = new Text("0");
		textAverageGrey = new Text("0");
		textTotalPixel = new Text("0");

		 textBox = new HBox(5, labelBandR, textBandR, labelBandG, textBandG, labelBandB, textBandB, labelGray,
				textAverageGrey, labelTotalPixel, textTotalPixel);
		 textBox.setVisible(false);

		return textBox;

	}

	private void setHboxInformation(Color averangeColor, double averangeGray, int totalPixel)

	{
		DecimalFormat df = new DecimalFormat("##.##");
		df.setRoundingMode(RoundingMode.DOWN);

		this.textBandR.setText(String.valueOf(df.format(averangeColor.getRed() * 255)));
		this.textBandG.setText(String.valueOf(df.format(averangeColor.getGreen() * 255)));
		this.textBandB.setText(String.valueOf(df.format(averangeColor.getBlue() * 255)));
		this.textAverageGrey.setText(String.valueOf(df.format(averangeGray * 255)));
		this.textTotalPixel.setText(String.valueOf(totalPixel));
		 textBox.setVisible(true);
		secondaryStage.sizeToScene();

	}

	private void calculateAverange(Bounds bounds) {

		ImageGetColorRGB a = new ImageGetColorRGBImpl(this.getImageGenerate(bounds));

		setHboxInformation(a.getAverageChannelsRGB(), a.getAverageGrey(), a.getTotalPixel());
	}

	
	private void cropSaveWindowsImage(Bounds bounds) {

		DrawingSelect newStage = new DrawingSelect();
		newStage.start(this.getImageGenerate(bounds));

	}

	private Image getImageGenerate(Bounds bounds) {
		int width = (int) bounds.getWidth();
		int height = (int) bounds.getHeight();

		SnapshotParameters parameters = new SnapshotParameters();
		parameters.setFill(Color.TRANSPARENT);
		parameters.setViewport(new Rectangle2D(bounds.getMinX(), bounds.getMinY(), width, height));

		WritableImage image = new WritableImage(width, height);
		imageView.snapshot(parameters, image);

		return image;
	}

	/*private void cropSaveImage(Bounds bounds) {

	/*	FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Image");

		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"));

		File file = fileChooser.showSaveDialog(secondaryStage);
		if (file == null)
			return;

		int width = (int) bounds.getWidth();
		int height = (int) bounds.getHeight();

		SnapshotParameters parameters = new SnapshotParameters();
		parameters.setFill(Color.TRANSPARENT);
		parameters.setViewport(new Rectangle2D(bounds.getMinX(), bounds.getMinY(), width, height));

		WritableImage image = new WritableImage(width, height);
		imageView.snapshot(parameters, image);
		
		 FileChooser fileChooser;
	
	 ImageIOService imageIOService=new ImageIOServiceImpl();
	 imageIOService.saveImage(fileChooser,image);
		
	//	ImageRepository saveImage=new ImageRepositoryImpl();
		//saveImage.storeImage(image, file);

		BufferedImage bufImageARGB = SwingFXUtils.fromFXImage(image, null);
		BufferedImage bufImageRGB = new BufferedImage(bufImageARGB.getWidth(), bufImageARGB.getHeight(),
				BufferedImage.OPAQUE);

	/*	Graphics2D graphics = bufImageRGB.createGraphics();
		graphics.drawImage(bufImageARGB, 0, 0, null);

		try {

			String extension = "";

			int i = file.getName().lastIndexOf('.');
			int p = Math.max(file.getName().lastIndexOf('/'), file.getName().lastIndexOf('\\'));

			if (i > p) {
				extension = file.getName().substring(i + 1);
			}

			ImageIO.write(bufImageRGB, extension, file);

			System.out.println("Imagen guardada en ojeteeee " + file.getAbsolutePath());

		} catch (IOException e) {
			e.printStackTrace();
		}

		graphics.dispose();

	}
*/

}
