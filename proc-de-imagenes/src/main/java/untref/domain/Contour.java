package untref.domain;

import javafx.scene.image.Image;

import java.util.List;

import static untref.domain.utils.ImageValuesTransformer.toInt;

public class Contour {

	private static int BACKGROUND = 3;
	private static int L_OUT = 1;
	private static int L_IN = -1;
	private static int OBJECT = -3;

	private Image image;
	private int matrix[][];
	private List<ImagePosition> lIn;
	private List<ImagePosition> lOut;
	private final Image originalImage;

	public Contour(Image image, List<ImagePosition> lIn, List<ImagePosition> lOut, Image originalImage) {
		this.image = image;
		this.lIn = lIn;
		this.lOut = lOut;
		this.originalImage = originalImage;
		initializeMatrix();
	}

	public List<ImagePosition> getlIn() {
		return lIn;
	}

	public List<ImagePosition> getlOut() {
		return lOut;
	}

	public Image getImage() {
		return image;
	}

	public Image getOriginalImage() {
		return originalImage;
	}

	public ImagePosition getBackgroundNeighboring(ImagePosition imagePosition) {
		return calculatePosition(imagePosition, BACKGROUND);
	}

	public ImagePosition getInsideNeighboring(ImagePosition imagePosition) {
		return calculatePosition(imagePosition, OBJECT);
	}

	private ImagePosition calculatePosition(ImagePosition imagePosition, int imageElement) {
		int row = imagePosition.getRow();
		int column = imagePosition.getColumn();
		ImagePosition searchedPosition = null;
		searchedPosition = evaluatePosition(row - 1, column, searchedPosition, imageElement);
		searchedPosition = evaluatePosition(row, column - 1, searchedPosition, imageElement);
		searchedPosition = evaluatePosition(row + 1, column, searchedPosition, imageElement);
		searchedPosition = evaluatePosition(row , column +1, searchedPosition, imageElement);
		return searchedPosition;
	}

	private ImagePosition evaluatePosition(int row, int column, ImagePosition backgroundNeighboring, int imageElement) {
		if(matrix[row][column] == imageElement){
			return new ImagePosition(row, column);
		}else{
			return backgroundNeighboring;
		}
	}

	private void initializeMatrix() {
		int height = toInt(image.getHeight());
		int width = toInt(image.getWidth());
		matrix = new int[height][width];

		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				matrix[row][column] = 3;
			}
		}

		setWithEdges();
	}

	private void setWithEdges() {
		lIn.forEach(imagePosition -> matrix[imagePosition.getRow()][imagePosition.getColumn()] = -1);
		lOut.forEach(imagePosition -> matrix[imagePosition.getRow()][imagePosition.getColumn()] = 1);
	}
}