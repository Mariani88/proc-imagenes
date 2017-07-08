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

import static untref.domain.utils.ImageValuesTransformer.toInt;

public class ContourDomainServiceImpl implements ContourDomainService {

	@Override
	public Contour createContour(ImagePosition imagePosition, ImagePosition imagePosition2, Image image) {
		int firstRow = Math.min(imagePosition.getRow(), imagePosition2.getRow());
		int secondRow = Math.max(imagePosition.getRow(), imagePosition2.getRow());
		int firstColumn = Math.min(imagePosition.getColumn(), imagePosition2.getColumn());
		int secondColumn = Math.max(imagePosition.getColumn(), imagePosition2.getColumn());
		List<ImagePosition> lIn = new ArrayList<>();
		List<ImagePosition> lOut = new ArrayList<>();
		WritableImage imageWithContours = replicateImage(image);
		PixelWriter pixelWriter = imageWithContours.getPixelWriter();
		paintContourColumns(lOut, firstRow, secondRow, firstColumn, secondColumn, Color.BLUE, pixelWriter);
		paintContourColumns(lIn, firstRow + 1, secondRow - 1, firstColumn + 1, secondColumn - 1, Color.RED, pixelWriter);
		paintContourRows(lOut, firstColumn, secondColumn, firstRow, secondRow, pixelWriter, Color.BLUE);
		paintContourRows(lIn, firstColumn + 1, secondColumn - 1, firstRow + 1, secondRow - 1, pixelWriter, Color.RED);
		return new Contour(imageWithContours, lIn, lOut, image, firstRow + 2, firstColumn + 2, secondRow - 2, secondColumn - 2);
	}

	@Override
	public Contour createContourWithColorAverage(ImagePosition imagePosition, ImagePosition imagePosition2, Image image, Color objectColorAverage) {
		Contour contour = createContour(imagePosition, imagePosition2, image);
		contour.setObjectColorAverage(objectColorAverage);
		return contour;
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