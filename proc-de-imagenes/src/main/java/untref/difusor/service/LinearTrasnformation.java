package untref.difusor.service;

public class LinearTrasnformation {
	
	public int[][] aplicarTransformacionLineal(int[][] matriz) {

		float min;
		float max;

		int[][] matrixTransformed;
		
		int row = matriz.length;
		int column = matriz[0].length;
		
		matrixTransformed = new int[row][column];
		
		min = 0;
		max = 255;

		for (int f = 0; f < row; f++) {
			for (int g = 0; g < column; g++) {
		
				int value = matriz[f][g];
				
				if (min > value) {
					min = value;
				}

				if (max < value) {
					max = value;
				}

			}

		}

		float[] maxAndMin = new float[2];
		maxAndMin[0] = min;
		maxAndMin[1] = max;
		
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {

				int value = matriz[i][j];
				int valueTransformed = (int) ((((255f) / (max - min)) * value) - ((min * 255f) / (max - min)));

				matrixTransformed[i][j] = valueTransformed;
			}
		}
		
		return matrixTransformed;
	}

}
