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
	
	public int[][] crearMascaraBorde(int mascara) {
		int[][] matrizMascara = new int[mascara][mascara];
		for (int i=0; i < mascara; i++){
			for(int j =0; j < mascara; j++){
				matrizMascara[i][j]=-1;
			}
		}
		matrizMascara[mascara/2][mascara/2]=((int) Math.pow(mascara,2))-1;
		return matrizMascara;
	}
	
	
	public int[][] weightedMediaMask(int sizeMask) {
		int[][] matrizMask = new int[sizeMask][sizeMask];
		for (int i=0; i < sizeMask; i++){
			for(int j =0; j < sizeMask; j++){
				matrizMask[i][j]=1;
			}
		}
		return matrizMask;
	}
	
	public int gaussMaskSize(double detour) {
		int sizeMask=  (int) (2*Math.sqrt(2)*detour);
		if(sizeMask%2==0){
			sizeMask++;
		}
		return sizeMask;
	}

	public double[][] maskGauss(int sizeMask, double detour) {
		double[][] matrizMask = new double[sizeMask][sizeMask];
		double value=0;
		double exponencial=0;
		for (int i=0; i < sizeMask; i++){
			for(int j =0; j < sizeMask; j++){
				exponencial= Math.exp(-(Math.pow(i-sizeMask/2,2)+Math.pow(j-sizeMask/2,2))/(Math.pow(detour,2)*2));
				value =(1.0/(2.0*Math.pow(detour,2)*Math.PI))*(exponencial);
				matrizMask[i][j]= value;
			}
		}
		return matrizMask;
	}
	
	public int sizeMaskGauss(double detour){
		int mask=  (int) (2*Math.sqrt(2)*detour);
		if(mask%2==0){
			mask++;
		}
		return mask;
	}
}

