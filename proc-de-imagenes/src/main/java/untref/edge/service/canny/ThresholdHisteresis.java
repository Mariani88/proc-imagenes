package untref.edge.service.canny;

public class ThresholdHisteresis {
	public int[][] threshold(int[][] bordesMaximos, int ancho, int alto, int lowThresholdValue,
			int highThresholdValue) {
		int[][] bordes = new int[ancho][alto];
		for (int i = 0; i < ancho; i++) {
			bordes[i][0] = 0;
			bordes[i][alto - 1] = 0;
		}
		for (int j = 0; j < alto; j++) {
			bordes[0][j] = 0;
			bordes[ancho - 1][j] = 0;
		}
		for (int i = 1; i < ancho - 1; i++) {
			for (int j = 1; j < alto - 1; j++) {
				if (bordesMaximos[i][j] < lowThresholdValue) {
					bordes[i][j] = 0;
				} else {
					if (bordesMaximos[i][j] > highThresholdValue) {
						bordes[i][j] = 1;
					} else {
						bordes[i][j] = 2;
					}
				}
			}
		}
		for (int i = 1; i < ancho - 1; i++) {
			for (int j = 1; j < alto - 1; j++) {
				if (bordes[i][j] == 2) {
					bordes[i][j] = verVecinos(i, j, bordes);
				}
			}
		}
		return bordes;
	}
	
	private int verVecinos(int i, int j, int[][] bordesMaximos) {
		int valor = 0;
		if ((bordesMaximos[i - 1][j - 1] == 1) || (bordesMaximos[i][j - 1] == 1) || (bordesMaximos[i + 1][j - 1] == 1)
				|| (bordesMaximos[i + 1][j] == 1) || (bordesMaximos[i + 1][j + 1] == 1)
				|| (bordesMaximos[i][j + 1] == 1) || (bordesMaximos[i - 1][j + 1] == 1)
				|| (bordesMaximos[i - 1][j] == 1)) {
			valor = 1;
		}
		return valor;
	}
}
