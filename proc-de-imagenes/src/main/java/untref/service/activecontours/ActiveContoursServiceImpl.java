package untref.service.activecontours;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import untref.domain.Contour;
import untref.domain.ImagePosition;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static untref.domain.utils.ImageValuesTransformer.toInt;
import static untref.domain.utils.ImageValuesTransformer.toRGBScale;

public class ActiveContoursServiceImpl implements ActiveContoursService {

	@Override
	public Contour initializeActiveContours(Image image, ImagePosition imagePosition, ImagePosition imagePosition2) {
		WritableImage imageWithContours = replicateImage(image);
		return paintExternalContours(imageWithContours, imagePosition, imagePosition2, image);
	}

	@Override
	public Contour adjustContours(Contour contour) {
		List<ImagePosition> lOut = contour.getlOut();
		List<ImagePosition> lIn = contour.getlIn();

		for (int index = 0; index < lOut.size(); index++) {
			ImagePosition imagePosition = lOut.get(index);
			boolean isPositive = applyFd(imagePosition, contour.getOriginalImage(), contour.getObjectColorAverage());
			expandContours(isPositive, contour, imagePosition);
		}

		contour.moveInvalidLinToObject();

		for (int index = 0; index < lIn.size(); index++) {
			ImagePosition imagePosition = lIn.get(index);
			boolean isPositive = applyFd(imagePosition, contour.getOriginalImage(), contour.getObjectColorAverage());
			shortenContour(isPositive, contour, imagePosition);
		}

		contour.moveInvalidLoutToBackground();
		contour.updateImage();
		return contour;
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

	private boolean applyFd(ImagePosition imagePosition, Image originalImage, Color objectColorAverage) {
		PixelReader pixelReader = originalImage.getPixelReader();
		Color imagePositionColor = pixelReader.getColor(imagePosition.getColumn(), imagePosition.getRow());
		int difRed = toRGBScale(imagePositionColor.getRed() - objectColorAverage.getRed());
		int difGreen = toRGBScale(imagePositionColor.getGreen() - objectColorAverage.getGreen());
		int difBlue = toRGBScale(imagePositionColor.getBlue() - objectColorAverage.getBlue());
		double norma = Math.sqrt(Math.pow(difRed, 2) + Math.pow(difGreen, 2) + Math.pow(difBlue, 2));
		return !(norma >= 0.1);
	}

	private Contour paintExternalContours(WritableImage imageWithContours, ImagePosition imagePosition, ImagePosition imagePosition2, Image image) {
		int firstRow = Math.min(imagePosition.getRow(), imagePosition2.getRow());
		int secondRow = Math.max(imagePosition.getRow(), imagePosition2.getRow());
		int firstColumn = Math.min(imagePosition.getColumn(), imagePosition2.getColumn());
		int secondColumn = Math.max(imagePosition.getColumn(), imagePosition2.getColumn());
		PixelWriter pixelWriter = imageWithContours.getPixelWriter();
		List<ImagePosition> lIn = new ArrayList<>();
		List<ImagePosition> lOut = new ArrayList<>();

		for (int index = firstRow; index <= secondRow; index++) {
			pixelWriter.setColor(firstColumn, index, Color.BLUE);
			pixelWriter.setColor(secondColumn, index, Color.BLUE);
			lOut.add(new ImagePosition(index, firstColumn));
			lOut.add(new ImagePosition(index, secondColumn));
		}

		for (int index = firstColumn; index <= secondColumn; index++) {
			pixelWriter.setColor(index, firstRow, Color.BLUE);
			pixelWriter.setColor(index, secondRow, Color.BLUE);
			lOut.add(new ImagePosition(firstRow, index));
			lOut.add(new ImagePosition(secondRow, index));
		}

		for (int index = firstRow + 1; index <= secondRow - 1; index++) {
			pixelWriter.setColor(firstColumn + 1, index, Color.RED);
			pixelWriter.setColor(secondColumn - 1, index, Color.RED);
			lIn.add(new ImagePosition(index, firstColumn + 1));
			lIn.add(new ImagePosition(index, secondColumn - 1));
		}

		for (int index = firstColumn + 1; index <= secondColumn - 1; index++) {
			pixelWriter.setColor(index, firstRow + 1, Color.RED);
			pixelWriter.setColor(index, secondRow - 1, Color.RED);
			lIn.add(new ImagePosition(firstRow + 1, index));
			lIn.add(new ImagePosition(secondRow - 1, index));
		}

		return new Contour(imageWithContours, lIn, lOut, image, firstRow + 2, firstColumn + 2, secondRow - 2, secondColumn - 2);
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