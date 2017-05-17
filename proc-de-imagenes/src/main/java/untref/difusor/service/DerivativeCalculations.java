package untref.difusor.service;


public class DerivativeCalculations {

	public int calcularDerivadaEste(int[][] imageMatrix, int width, int height) {

		int coordinate = 0;
		int colorCurrent = 0;
		int colorNeighbour = 0;

		coordinate = width + 1;
		colorCurrent = (int) imageMatrix[width][height];

		if (coordinate < width  && coordinate >= 0) {
			colorNeighbour = imageMatrix[coordinate][height];

		} else {
			colorNeighbour = colorCurrent;
		}

		return colorNeighbour - colorCurrent;
	}

	public int calcularDerivadaOeste(int[][] imageMatrix, int width, int height) {

		int coordinate = 0;
		int colorCurrent = 0;
		int colorNeighbour = 0;

		coordinate = width - 1;
		colorCurrent = (int) imageMatrix[width][height];

		if (coordinate <  width  && coordinate >= 0) {
			colorNeighbour = imageMatrix[coordinate][height];

		} else {
			colorNeighbour = colorCurrent;
		}

		return colorNeighbour - colorCurrent;
	}

	public int calcularDerivadaNorte(int[][] imageMatrix, int width, int height) {

		int coordinate = 0;
		int colorCurrent = 0;
		int colorNeighbour = 0;

		coordinate = height - 1;
		colorCurrent = (int) imageMatrix[width][height];

		if (coordinate < height && coordinate >= 0) {
			colorNeighbour = imageMatrix[width][coordinate];

		} else {
			colorNeighbour = colorCurrent;
		}

		return colorNeighbour - colorCurrent;
	}

	public int calcularDerivadaSur(int[][] imageMatrix, int width, int height) {

		int coordinate = 0;
		int colorCurrent = 0;
		int colorNeighbour = 0;

		coordinate = height + 1;
		colorCurrent = (int) imageMatrix[width][height];

		if (coordinate <  height  && coordinate >= 0) {
			colorNeighbour = imageMatrix[width][coordinate];

		} else {
			colorNeighbour = colorCurrent;
		}

		return colorNeighbour - colorCurrent;
	}

}
