package untref.domain;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

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
	private final Color objectColorAverage;
	private Image image;
	private int matrix[][];
	private CopyOnWriteArrayList<ImagePosition> lIn;
	private CopyOnWriteArrayList<ImagePosition> lOut;
	private final Image originalImage;

	public Contour(Image image, List<ImagePosition> lIn, List<ImagePosition> lOut, Image originalImage, int fromRowObject, int fromColumnObject,
			int toRowObject, int toColumnObject) {
		this.image = image;
		this.lIn = new CopyOnWriteArrayList<>(lIn);
		this.lOut = new CopyOnWriteArrayList<>(lOut);
		this.originalImage = originalImage;
		initializeMatrix(fromRowObject, fromColumnObject, toRowObject, toColumnObject);
		objectColorAverage = calculateObjectColorAverage();
	}

	public Color getObjectColorAverage() {
		return objectColorAverage;
	}

	private Color calculateObjectColorAverage() {
		List<Color> objectColors = new ArrayList<>();
		PixelReader pixelReader = image.getPixelReader();

		for (int row = 0; row < image.getHeight(); row++) {
			for (int column = 0; column < image.getWidth(); column++) {
				if (matrix[row][column] == OBJECT) {
					objectColors.add(pixelReader.getColor(column, row));
				}
			}
		}

		double redAverage = calculateAverage(objectColors.stream().map(Color::getRed).collect(Collectors.toList()));
		double greenAverage = calculateAverage(objectColors.stream().map(Color::getGreen).collect(Collectors.toList()));
		double blueAverage = calculateAverage(objectColors.stream().map(Color::getBlue).collect(Collectors.toList()));
		return Color.rgb(toInt(redAverage), toInt(greenAverage), toInt(blueAverage));
	}

	private double calculateAverage(List<Double> grays) {
		double gray = 0;

		for (Double grayValue : grays) {
			gray += grayValue;
		}

		return (double) 255 * gray / (double) grays.size();
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
		boolean hasPositiveNeighboring =
				matrix[row - 1][column] < 0 && matrix[row + 1][column] < 0 && matrix[row][column - 1] < 0 && matrix[row][column + 1] < 0;

		if (!hasPositiveNeighboring) {

			System.out.println("row = " + row + " column = " + column);
		}

		return hasPositiveNeighboring;
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
		return getNeighborings(imagePosition, BACKGROUND);
	}

	public void updateImage() {
		int width = toInt(originalImage.getWidth());
		int height = toInt(originalImage.getHeight());
		WritableImage writableImage = new WritableImage(width, height);
		PixelWriter pixelWriter = writableImage.getPixelWriter();
		PixelReader pixelReader = originalImage.getPixelReader();

		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				if (matrix[row][column] == L_OUT) {
					pixelWriter.setColor(column, row, Color.BLUE);
				} else if (matrix[row][column] == L_IN) {
					pixelWriter.setColor(column, row, Color.RED);
				} else {
					pixelWriter.setColor(column, row, pixelReader.getColor(column, row));
				}
			}
		}

		image = writableImage;
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
		return matrix[row - 1][column] > 0 && matrix[row + 1][column] > 0 && matrix[row][column - 1] > 0 && matrix[row][column + 1] > 0;
	}

	private Set<ImagePosition> getNeighborings(ImagePosition imagePosition, int element) {
		Set<ImagePosition> elementNeighborings = new HashSet<>();
		int row = imagePosition.getRow();
		int column = imagePosition.getColumn();

		if (matrix[row - 1][column] == element) {
			elementNeighborings.add(new ImagePosition(row - 1, column));
		}

		if (matrix[row + 1][column] == element) {
			elementNeighborings.add(new ImagePosition(row + 1, column));
		}

		if (matrix[row][column - 1] == element) {
			elementNeighborings.add(new ImagePosition(row, column - 1));
		}

		if (matrix[row][column + 1] == element) {
			elementNeighborings.add(new ImagePosition(row, column + 1));
		}

		return elementNeighborings;
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