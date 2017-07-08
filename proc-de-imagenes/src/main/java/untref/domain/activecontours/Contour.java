package untref.domain.activecontours;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import untref.domain.ImagePosition;
import untref.utils.ImageValidator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import static untref.domain.utils.ImageValuesTransformer.toInt;

public class Contour {

	private static int BACKGROUND = 3;
	private static int L_OUT = 1;
	private static int L_IN = -1;
	private static int OBJECT = -3;
	private Color objectColorAverage;
	private Image imageWithContour;
	private int matrix[][];
	private CopyOnWriteArrayList<ImagePosition> lIn;
	private CopyOnWriteArrayList<ImagePosition> lOut;
	private Image originalImage;

	public Contour(Image imageWithContour, List<ImagePosition> lIn, List<ImagePosition> lOut, Image originalImage, int fromRowObject,
			int fromColumnObject, int toRowObject, int toColumnObject) {
		this.imageWithContour = imageWithContour;
		this.lIn = new CopyOnWriteArrayList<>(lIn);
		this.lOut = new CopyOnWriteArrayList<>(lOut);
		this.originalImage = originalImage;
		initializeMatrix(fromRowObject, fromColumnObject, toRowObject, toColumnObject);
		objectColorAverage = new ContourObjectCalculator().calculateObjectColorAverage(imageWithContour, matrix, OBJECT);
	}

	public void setObjectColorAverage(Color objectColorAverage) {
		this.objectColorAverage = objectColorAverage;
	}

	public Color getObjectColorAverage() {
		return objectColorAverage;
	}

	public Contour applyToNewImage(Image image) {
		originalImage = image;
		updateImage();
		return this;
	}

	public void updateImage() {
		imageWithContour = new ContourImageUpdater().updateImage(originalImage, L_IN, L_OUT, matrix);
	}

	public List<ImagePosition> getlIn() {
		return lIn;
	}

	public List<ImagePosition> getlOut() {
		return lOut;
	}

	public Image getImageWithContour() {
		return imageWithContour;
	}

	public Image getOriginalImage() {
		return originalImage;
	}

	public void removeFromLout(ImagePosition imagePosition) {
		lOut.remove(imagePosition);
	}

	public void addToLIn(ImagePosition imagePosition) {
		lIn.add(imagePosition);
		matrix[imagePosition.getRow()][imagePosition.getColumn()] = L_IN;
	}

	public void moveInvalidLinToObject() {
		List<ImagePosition> invalidLinPositions = lIn.stream().filter(this::hasAllNeighboringWithValueLowerThanZero).collect(Collectors.toList());
		lIn.removeAll(invalidLinPositions);
		invalidLinPositions.forEach(imagePosition -> matrix[imagePosition.getRow()][imagePosition.getColumn()] = OBJECT);
	}

	private boolean hasAllNeighboringWithValueLowerThanZero(ImagePosition imagePosition) {
		int row = imagePosition.getRow();
		int column = imagePosition.getColumn();
		return hasValueLowerThanZero(matrix, row - 1, column) && hasValueLowerThanZero(matrix, row + 1, column) && hasValueLowerThanZero(matrix, row,
				column - 1) && hasValueLowerThanZero(matrix, row, column + 1);
	}

	private boolean hasValueLowerThanZero(int[][] matrix, int row, int column) {
		boolean has = true;

		if (ImageValidator.existPosition(originalImage, row, column)) {
			has = matrix[row][column] < 0;
		}
		return has;
	}

	public void addToLout(Set<ImagePosition> backgroundNeighborings) {
		backgroundNeighborings.forEach(imagePosition -> {
			lOut.add(imagePosition);
			matrix[imagePosition.getRow()][imagePosition.getColumn()] = L_OUT;
		});
	}

	public Set<ImagePosition> getAllObjectNeighboring(ImagePosition imagePosition) {
		return getNeighborings(imagePosition, OBJECT);
	}

	public Set<ImagePosition> getAllBackgroundNeighboring(ImagePosition imagePosition) {
		Set<ImagePosition> neighborings = getNeighborings(imagePosition, BACKGROUND);
		return neighborings.stream().filter(imagePosition1 -> ImageValidator.existPosition(originalImage, imagePosition1))
				.collect(Collectors.toSet());
	}

	public void moveFromLinToLout(ImagePosition imagePosition) {
		lIn.remove(imagePosition);
		lOut.add(imagePosition);
		matrix[imagePosition.getRow()][imagePosition.getColumn()] = L_OUT;
	}

	public void addToLin(Set<ImagePosition> backgroundNeighborings) {
		backgroundNeighborings.forEach(imagePosition -> {
			lIn.add(imagePosition);
			matrix[imagePosition.getRow()][imagePosition.getColumn()] = L_IN;
		});
	}

	public void moveInvalidLoutToBackground() {
		List<ImagePosition> invalidLout = lOut.stream().filter(this::hasAllNeighboringWithValueHigherThanZero).collect(Collectors.toList());
		invalidLout.forEach(imagePosition -> {
			lOut.remove(imagePosition);
			matrix[imagePosition.getRow()][imagePosition.getColumn()] = BACKGROUND;
		});
	}

	private boolean hasAllNeighboringWithValueHigherThanZero(ImagePosition imagePosition) {
		int row = imagePosition.getRow();
		int column = imagePosition.getColumn();
		return hasValueHigherThanZero(matrix, row - 1, column) && hasValueHigherThanZero(matrix, row + 1, column) && hasValueHigherThanZero(matrix,
				row, column - 1) && hasValueHigherThanZero(matrix, row, column + 1);
	}

	private boolean hasValueHigherThanZero(int[][] matrix, int row, int column) {
		boolean has = true;

		if (ImageValidator.existPosition(originalImage, row, column)) {
			has = matrix[row][column] > 0;
		}
		return has;
	}

	private Set<ImagePosition> getNeighborings(ImagePosition imagePosition, int element) {
		Set<ImagePosition> elementNeighborings = new HashSet<>();
		int row = imagePosition.getRow();
		int column = imagePosition.getColumn();
		addPositionToSetIfContainsElement(row - 1, column, element, elementNeighborings);
		addPositionToSetIfContainsElement(row + 1, column, element, elementNeighborings);
		addPositionToSetIfContainsElement(row, column - 1, element, elementNeighborings);
		addPositionToSetIfContainsElement(row, column + 1, element, elementNeighborings);
		return elementNeighborings;
	}

	private void addPositionToSetIfContainsElement(int row, int column, int element, Set<ImagePosition> elementNeighborings) {
		if (ImageValidator.existPosition(originalImage, row, column) && matrix[row][column] == element) {
			elementNeighborings.add(new ImagePosition(row, column));
		}
	}

	private void initializeMatrix(int fromRowObject, int fromColumnObject, int toRowObject, int toColumnObject) {
		int height = toInt(originalImage.getHeight());
		int width = toInt(originalImage.getWidth());
		matrix = new int[height][width];

		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				matrix[row][column] = BACKGROUND;
			}
		}

		setWithEdges();
		setWithObject(fromRowObject, fromColumnObject, toRowObject, toColumnObject);
	}

	private void setWithObject(int fromRowObject, int fromColumnObject, int toRowObject, int toColumnObject) {
		for (int row = fromRowObject; row <= toRowObject; row++) {
			for (int column = fromColumnObject; column <= toColumnObject; column++) {
				matrix[row][column] = OBJECT;
			}
		}
	}

	private void setWithEdges() {
		lIn.forEach(imagePosition -> matrix[imagePosition.getRow()][imagePosition.getColumn()] = L_IN);
		lOut.forEach(imagePosition -> matrix[imagePosition.getRow()][imagePosition.getColumn()] = L_OUT);
	}
}