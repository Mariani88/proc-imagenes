package untref.service.activecontours;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import untref.domain.ImagePosition;
import untref.domain.activecontours.Contour;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static untref.domain.utils.ImageValuesTransformer.toInt;
import static untref.domain.utils.ImageValuesTransformer.toRGBScale;

public class ActiveContoursServiceImpl implements ActiveContoursService {

	private double processedImages = 0;
	private double totalLin = 0;
	private double average;
	private ImagePosition imagePosition;
	private ImagePosition imagePosition2;

	@Override
	public Contour initializeActiveContours(Image image, ImagePosition imagePosition, ImagePosition imagePosition2) {
		this.imagePosition = imagePosition;
		this.imagePosition2 = imagePosition2;
		WritableImage imageWithContours = replicateImage(image);
		return paintExternalContours(imageWithContours, imagePosition, imagePosition2, image);
	}

	@Override
	public Contour adjustContours(Contour contour, Double colorDelta) {
		List<ImagePosition> lOut = contour.getlOut();
		List<ImagePosition> lIn = contour.getlIn();

		for (int index = 0; index < lOut.size(); index++) {
			ImagePosition imagePosition = lOut.get(index);
			boolean isPositive = applyFd(imagePosition, contour.getOriginalImage(), contour.getObjectColorAverage(), colorDelta);
			expandContours(isPositive, contour, imagePosition);
		}

		contour.moveInvalidLinToObject();

		for (int index = 0; index < lIn.size(); index++) {
			ImagePosition imagePosition = lIn.get(index);
			boolean isPositive = applyFd(imagePosition, contour.getOriginalImage(), contour.getObjectColorAverage(), colorDelta);
			shortenContour(isPositive, contour, imagePosition);
		}

		contour.moveInvalidLoutToBackground();
		contour.updateImage();
		return contour;
	}

	@Override
	public Contour applyContourToNewImage(Contour contour, Image image) {
		return contour.applyToNewImage(image);
	}

	@Override
	public Contour adjustContoursAutomatically(Contour contour, Double colorDelta, Double reductionTolerance) {
		int iterations = 20;

		for (int index = 0; index < iterations; index++) {
			contour = adjustContoursForVideo(contour, colorDelta, reductionTolerance);
		}
		updateLinAverage(contour.getlIn());
		return contour;
	}

	private Contour adjustContoursForVideo(Contour contour, Double colorDelta, Double reductionTolerance) {
		List<ImagePosition> lOut = contour.getlOut();
		List<ImagePosition> lIn = contour.getlIn();

		for (int index = 0; index < lOut.size(); index++) {
			ImagePosition imagePosition = lOut.get(index);
			boolean isPositive = applyFd(imagePosition, contour.getOriginalImage(), contour.getObjectColorAverage(), colorDelta);
			expandContours(isPositive, contour, imagePosition);
		}

		contour.moveInvalidLinToObject();

		for (int index = 0; index < lIn.size(); index++) {
			ImagePosition imagePosition = lIn.get(index);
			boolean isPositive = applyFd(imagePosition, contour.getOriginalImage(), contour.getObjectColorAverage(), colorDelta);
			shortenContour(isPositive, contour, imagePosition);
		}

		contour.moveInvalidLoutToBackground();
		contour.updateImage();
		contour = evaluateOclusion(lIn, reductionTolerance, contour);
		return contour;
	}

	private Contour evaluateOclusion(List<ImagePosition> lIn, Double reductionTolerance, Contour contour) {
		System.out.println("lIn: " + lIn.size() + " tolerance: " + reductionTolerance * average);
		if (lIn.size() < reductionTolerance * average) {
			//ImagePosition minimumPosition = searchMinimumPosition(lIn);
			//ImagePosition maximumPosition = searchMaximumPosition(lIn);
			//System.out.println("minimum row: " + minimumPosition.getRow() + " minimum column: "+ minimumPosition.getColumn());
			//System.out.println("maximum row: " + maximumPosition.getRow() + " maximum column: "+ maximumPosition.getColumn());

			ImagePosition imagePosition = new ImagePosition(Math.max(this.imagePosition.getRow() - 60, 0),
					Math.max(this.imagePosition.getColumn() - 60, 0));
			ImagePosition imagePosition2 = new ImagePosition(
					Math.min(this.imagePosition2.getRow() + 60, toInt(contour.getOriginalImage().getHeight() - 1)),
					Math.min(this.imagePosition2.getColumn() + 60, toInt(contour.getOriginalImage().getWidth() - 1)));
			return resetActiveContours(contour.getOriginalImage(), imagePosition, imagePosition2, contour.getObjectColorAverage());
		} else {
			return contour;
		}
	}

	private Contour resetActiveContours(Image image, ImagePosition imagePosition, ImagePosition imagePosition2, Color objectColorAverage) {
		WritableImage imageWithContours = replicateImage(image);
		Contour contour = paintExternalContours(imageWithContours, imagePosition, imagePosition2, image);
		contour.setObjectColorAverage(objectColorAverage);
		processedImages = 1;
		totalLin = contour.getlIn().size();
		average = totalLin/processedImages;
		return contour;
	}

	private ImagePosition searchMaximumPosition(List<ImagePosition> lIn) {
		int maximumColumn = Integer.MIN_VALUE;
		int maximumRow = Integer.MIN_VALUE;

		for (ImagePosition imagePosition : lIn) {
			maximumColumn = Math.max(maximumColumn, imagePosition.getColumn());
			maximumRow = Math.max(maximumRow, imagePosition.getRow());
		}

		return new ImagePosition(maximumRow, maximumColumn);
	}

	private ImagePosition searchMinimumPosition(List<ImagePosition> lIn) {
		int minimumColumn = Integer.MAX_VALUE;
		int minimumRow = Integer.MAX_VALUE;

		for (ImagePosition imagePosition : lIn) {
			minimumColumn = Math.min(minimumColumn, imagePosition.getColumn());
			minimumRow = Math.min(minimumRow, imagePosition.getRow());
		}

		return new ImagePosition(minimumRow, minimumColumn);
	}

	private void updateLinAverage(List<ImagePosition> lIn) {
		processedImages++;
		totalLin += lIn.size();
		System.out.println("processed images:" + processedImages);
		average = ((double) totalLin) / processedImages;
		System.out.println("average:" + average);
	}

	private void shortenContour(boolean isPositive, Contour contour, ImagePosition imagePosition) {
		if (!isPositive) {
			contour.moveFromLinToLout(imagePosition);
			Set<ImagePosition> objectNeighborings = contour.getAllObjectNeighboring(imagePosition);
			contour.addToLin(objectNeighborings);
		}
	}

	private void expandContours(boolean isPositive, Contour contour, ImagePosition imagePosition) {
		if (isPositive) {
			contour.removeFromLout(imagePosition);
			contour.addToLIn(imagePosition);
			Set<ImagePosition> backgroundNeighborings = contour.getAllBackgroundNeighboring(imagePosition);
			contour.addToLout(backgroundNeighborings);
		}
	}

	private boolean applyFd(ImagePosition imagePosition, Image originalImage, Color objectColorAverage, Double colorDelta) {
		PixelReader pixelReader = originalImage.getPixelReader();
		Color imagePositionColor = pixelReader.getColor(imagePosition.getColumn(), imagePosition.getRow());
		int difRed = toRGBScale(imagePositionColor.getRed() - objectColorAverage.getRed());
		int difGreen = toRGBScale(imagePositionColor.getGreen() - objectColorAverage.getGreen());
		int difBlue = toRGBScale(imagePositionColor.getBlue() - objectColorAverage.getBlue());
		double module = Math.sqrt(Math.pow(difRed, 2) + Math.pow(difGreen, 2) + Math.pow(difBlue, 2));
		return !(module >= colorDelta);
	}

	private Contour paintExternalContours(WritableImage imageWithContours, ImagePosition imagePosition, ImagePosition imagePosition2, Image image) {
		int firstRow = Math.min(imagePosition.getRow(), imagePosition2.getRow());
		int secondRow = Math.max(imagePosition.getRow(), imagePosition2.getRow());
		int firstColumn = Math.min(imagePosition.getColumn(), imagePosition2.getColumn());
		int secondColumn = Math.max(imagePosition.getColumn(), imagePosition2.getColumn());
		PixelWriter pixelWriter = imageWithContours.getPixelWriter();
		List<ImagePosition> lIn = new ArrayList<>();
		List<ImagePosition> lOut = new ArrayList<>();
		paintContourColumns(lOut, firstRow, secondRow, firstColumn, secondColumn, Color.BLUE, pixelWriter);
		paintContourColumns(lIn, firstRow + 1, secondRow - 1, firstColumn + 1, secondColumn - 1, Color.RED, pixelWriter);
		paintContourRows(lOut, firstColumn, secondColumn, firstRow, secondRow, pixelWriter, Color.BLUE);
		paintContourRows(lIn, firstColumn + 1, secondColumn - 1, firstRow + 1, secondRow - 1, pixelWriter, Color.RED);
		return new Contour(imageWithContours, lIn, lOut, image, firstRow + 2, firstColumn + 2, secondRow - 2, secondColumn - 2);
	}

	private void paintContourRows(List<ImagePosition> contourEdgePositions, int fromIndex, int toIndex, int firstRow, int secondRow,
			PixelWriter pixelWriter, Color color) {
		for (int index = fromIndex; index <= toIndex; index++) {
			pixelWriter.setColor(index, firstRow, color);
			pixelWriter.setColor(index, secondRow, color);
			contourEdgePositions.add(new ImagePosition(firstRow, index));
			contourEdgePositions.add(new ImagePosition(secondRow, index));
		}
	}

	private void paintContourColumns(List<ImagePosition> contourEdgePositions, int fromIndex, int toIndex, int firstColumn, int secondColumn,
			Color color, PixelWriter pixelWriter) {
		for (int index = fromIndex; index <= toIndex; index++) {
			pixelWriter.setColor(firstColumn, index, color);
			pixelWriter.setColor(secondColumn, index, color);
			contourEdgePositions.add(new ImagePosition(index, firstColumn));
			contourEdgePositions.add(new ImagePosition(index, secondColumn));
		}
	}

	private WritableImage replicateImage(Image image) {
		int width = toInt(image.getWidth());
		int height = toInt(image.getHeight());
		WritableImage writableImage = new WritableImage(width, height);
		PixelReader pixelReader = image.getPixelReader();
		PixelWriter pixelWriter = writableImage.getPixelWriter();

		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				pixelWriter.setColor(column, row, pixelReader.getColor(column, row));
			}
		}

		return writableImage;
	}
}