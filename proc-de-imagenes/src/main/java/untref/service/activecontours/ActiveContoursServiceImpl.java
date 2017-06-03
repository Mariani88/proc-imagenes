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

import static untref.domain.utils.ImageValuesTransformer.toInt;

public class ActiveContoursServiceImpl implements ActiveContoursService {

	@Override
	public Contour initializeActiveContours(Image image, ImagePosition imagePosition, ImagePosition imagePosition2) {
		WritableImage imageWithContours = replicateImage(image);
		return paintExternalContours(imageWithContours, imagePosition, imagePosition2, image);
	}

	@Override
	public Contour adjustContours(Contour contour) {
		List<ImagePosition> lOut = contour.getlOut();
		Image image = contour.getImage();
		List<ImagePosition> lIn = contour.getlIn();
		for (ImagePosition imagePosition : lOut) {
			ImagePosition backgroundNeighboring = contour.getBackgroundNeighboring(imagePosition);
			ImagePosition insideNeighboring = contour.getInsideNeighboring(imagePosition);
			boolean isPositive = applyFd(backgroundNeighboring, insideNeighboring, imagePosition, contour.getOriginalImage());
			expandContours(isPositive, contour, imagePosition, lOut);
		}

		return null;
	}

	private void expandContours(boolean isPositive, Contour contour) {



	}

	private boolean applyFd(ImagePosition backgroundNeighboring, ImagePosition insideNeighboring, ImagePosition lOutPosition, Image originalImage) {
		PixelReader pixelReader = originalImage.getPixelReader();
		Color backgroundNeighboringColor = pixelReader.getColor(backgroundNeighboring.getColumn(), backgroundNeighboring.getRow());
		Color lOutPositionColor = pixelReader.getColor(lOutPosition.getColumn(), lOutPosition.getRow());
		Color insideNeighboringColor = pixelReader.getColor(insideNeighboring.getColumn(), insideNeighboring.getRow());
		double fdForRed = applyFdForChannel(backgroundNeighboringColor.getRed(), lOutPositionColor.getRed(), insideNeighboringColor.getRed());
		double fdForGreen = applyFdForChannel(backgroundNeighboringColor.getGreen(), lOutPositionColor.getGreen(), insideNeighboringColor.getGreen());
		double fdForBlue = applyFdForChannel(backgroundNeighboringColor.getBlue(), lOutPositionColor.getBlue(), insideNeighboringColor.getBlue());
		return fdForRed < 0 && fdForGreen < 0 && fdForBlue < 0;
	}

	private double applyFdForChannel(double backgroundNeighboringGray, double lOutPositionGray, double insideNeighboringGray) {
		return Math.log((backgroundNeighboringGray - lOutPositionGray)/(insideNeighboringGray - lOutPositionGray));

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

		return new Contour(imageWithContours, lIn, lOut, image);
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