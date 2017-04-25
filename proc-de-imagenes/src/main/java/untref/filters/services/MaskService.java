package untref.filters.services;

public class MaskService {
	
	public int[][] mediaMask(int sizeMask) {
		int[][] matrizMask = new int[sizeMask][sizeMask];
		for (int i=0; i < sizeMask; i++){
			for(int j =0; j < sizeMask; j++){
				matrizMask[i][j]=1;
			}
		}
		return matrizMask;
	}

}

