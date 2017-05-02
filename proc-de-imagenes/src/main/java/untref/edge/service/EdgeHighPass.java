package untref.edge.service;





import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import untref.filters.services.MaskService;
import untref.service.ImageGetColorRGB;
import untref.service.ImageGetColorRGBImpl;
 

public class EdgeHighPass {
	
	public WritableImage  startBorde(Image image,  int sizeMask ) {
		int gris=0;
		int min;
		int max;
		int divisor=(int)Math.pow(sizeMask, 2);
		MaskService maskService=new MaskService();
		int[][] matrizMascara=maskService.crearMascaraBorde(sizeMask);
		WritableImage imageOut = new WritableImage((int) image.getWidth(), (int) image.getHeight());
		PixelWriter pixelWriter = imageOut.getPixelWriter();
		ImageGetColorRGB colorService=new ImageGetColorRGBImpl(image);
		Color colorRGB=Color.rgb(0,0,0);
		Integer[][] matrizAux=new Integer[(int) image.getWidth()][(int) image.getHeight()];
		for (int i=0; i < image.getWidth(); i++){
			for(int j =0; j < image.getHeight(); j++){
				matrizAux[i][j]=0;
			}
		}
		for (int i=0; i <= image.getWidth()-sizeMask; i++){
			for(int j =0; j <= image.getHeight()-sizeMask; j++){
				for (int k=0; k < sizeMask; k++){
					for(int m =0; m < sizeMask; m++){
						gris = gris + (colorService.getValueRgb(i+k, j+m))*matrizMascara[k][m];  
					}
				}
				matrizAux[i+ sizeMask/2][j+ sizeMask/2]=gris/divisor;
				gris=0;	
			}
		}
		min=buscarMinimo(matrizAux,(int)image.getWidth(),(int)image.getHeight());
		max=buscarMaximo(matrizAux,(int)image.getWidth(),(int)image.getHeight());
		for (int i=0; i < image.getWidth(); i++){
			for(int j =0; j < image.getHeight(); j++){
				gris= (int) transformacionLineal(matrizAux[i][j], max, min);
				colorRGB=Color.rgb(gris,gris,gris);
				pixelWriter.setColor(i,j, colorRGB);
			}
		}
		return imageOut;
	}

	public int buscarMinimo(Integer [][] matriz,int ancho,int alto) {
		int min = matriz[0][0];
		for (int i=0; i < ancho; i++){
			for(int j =0; j < alto; j++){
				if (min > matriz[i][j]) {
					min = matriz[i][j];
				}
			}
		}
		return min;
	}
	
	public int buscarMaximo(Integer [][] matriz,int ancho,int alto) {
		int max = matriz[0][0];
		for (int i=0; i < ancho; i++){
			for(int j =0; j < alto; j++){
				if (max < matriz[i][j]) {
					max = matriz[i][j];
				}
			}
		}
		return max;
	}
	
	private double transformacionLineal(double suma, double max, double min) {
		double salida = suma*(255/(max-min))+(255-((255*max)/(max-min)));
		return salida;
	}
	
	
}
