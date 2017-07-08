package untref.domain.activecontours;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static untref.domain.utils.ImageValuesTransformer.toInt;

public class ContourObjectCalculator {

	public Color calculateObjectColorAverage(Image imageWithContour, int matrix[][], int object) {
		List<Color> objectColors = new ArrayList<>();
		PixelReader pixelReader = imageWithContour.getPixelReader();

		for (int row = 0; row < imageWithContour.getHeight(); row++) {
			for (int column = 0; column < imageWithContour.getWidth(); column++) {
				if (matrix[row][column] == object) {
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
}