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
		int grey=0;
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
						grey = grey + (colorService.getGrayAverage(i+k, j+m))*matrizMascara[k][m];
					}
				}
				matrizAux[i+ sizeMask/2][j+ sizeMask/2]=grey/divisor;
				grey=0;	
			}
		}
		min=searchMin(matrizAux,(int)image.getWidth(),(int)image.getHeight());
		max=searchMax(matrizAux,(int)image.getWidth(),(int)image.getHeight());
		for (int i=0; i < image.getWidth(); i++){
			for(int j =0; j < image.getHeight(); j++){
				grey= (int) linearTransformation(matrizAux[i][j], max, min);
				colorRGB=Color.rgb(grey,grey,grey);
				pixelWriter.setColor(i,j, colorRGB);
			}
		}
		return imageOut;
	}

	public int searchMin(Integer [][] matriz,int width,int height) {
		int min = matriz[0][0];
		for (int i=0; i < width; i++){
			for(int j =0; j < height; j++){
				if (min > matriz[i][j]) {
					min = matriz[i][j];
				}
			}
		}
		return min;
	}
	
	public int searchMax(Integer [][] matriz,int width,int height) {
		int max = matriz[0][0];
		for (int i=0; i < width; i++){
			for(int j =0; j < height; j++){
				if (max < matriz[i][j]) {
					max = matriz[i][j];
				}
			}
		}
		return max;
	}
	
	private double linearTransformation(double add, double max, double min) {
		double salida = add*(255/(max-min))+(255-((255*max)/(max-min)));
		return salida;
	}
	
	
}
