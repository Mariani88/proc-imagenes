package untref.edge.service.canny;

public class SuppressionNotMaximum {
	
	public int[][] sumprimirNoMaximos(int[][] magnitud, int[][] angulos, int ancho, int alto) {
		return supresion(magnitud, angulos, ancho, alto);
	}

	private int[][] supresion(int[][] magnitud, int[][] angulos, int ancho, int alto) {
		int[][] magnitudDeBorde = new int[ancho][alto];
		for (int i = 1; i < ancho - 1; i++) {
			for (int j = 1; j < alto - 1; j++) {
				if (angulos[i][j] == 0) {
					magnitudDeBorde[i][j] = verArribaYAbajo(magnitud, i, j);
				}
				if (angulos[i][j] == 45) {
					magnitudDeBorde[i][j] = verDiagonalPositiva(magnitud, i, j);
				}
				if (angulos[i][j] == 90) {
					magnitudDeBorde[i][j] = verDerechaEIzquierda(magnitud, i, j);
				}
				if (angulos[i][j] == 135) {
					magnitudDeBorde[i][j] = verDiagonalNegativa(magnitud, i, j);
				}
			}
		}
		return magnitudDeBorde;

}

	
	private int verDiagonalNegativa(int[][] magnitud, int i, int j) {
		int valor = 0;
		if ((magnitud[i - 1][j - 1] <= magnitud[i][j]) && (magnitud[i + 1][j + 1] <= magnitud[i][j])) {
			valor = magnitud[i][j];
		}
		return valor;
	}

	private int verDerechaEIzquierda(int[][] magnitud, int i, int j) {
		int valor = 0;
		if ((magnitud[i - 1][j] <= magnitud[i][j]) && (magnitud[i + 1][j] <= magnitud[i][j])) {
			valor = magnitud[i][j];
		}
		return valor;
	}

	private int verDiagonalPositiva(int[][] magnitud, int i, int j) {
		int valor = 0;
		if ((magnitud[i + 1][j - 1] <= magnitud[i][j]) && (magnitud[i - 1][j + 1] <= magnitud[i][j])) {
			valor = magnitud[i][j];
		}
		return valor;
	}

	private int verArribaYAbajo(int[][] magnitud, int i, int j) {
		int valor = 0;
		if ((magnitud[i][j - 1] <= magnitud[i][j]) && (magnitud[i][j + 1] <= magnitud[i][j])) {
			valor = magnitud[i][j];
		}
		return valor;
	}
}