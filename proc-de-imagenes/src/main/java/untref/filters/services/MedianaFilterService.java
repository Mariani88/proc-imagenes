package untref.filters.services;

import java.awt.Color;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class MedianaFilterService {
	
	public Image getImageMediana(Image image  , int sizeMask) {
		
		int[][] matrizMask = null;
		
		MaskService maskMedia = new MaskService();
		matrizMask = maskMedia.mediaMask(sizeMask);
		WritableImage imageOut = new WritableImage((int) image.getWidth(), (int) image.getHeight());
		
		int valueColor=0;
		int[] matrizResult=new int[(int) Math.pow(sizeMask, 2)];
		for (int i=0; i <= image.getWidth()-sizeMask; i++){
			for(int j =0; j <= image.getHeight()-sizeMask; j++){
				int indice=0;
				for (int k=0; k < sizeMask; k++){
					for(int m =0; m < sizeMask; m++){
						matrizResult[indice]=image.getPixelReader().getArgb(i+k, j+m)*matrizMask[k][m];
						indice++;
					}
				}
			valueColor=middleValue(matrizResult, sizeMask);
			PixelWriter pixelWriter = imageOut.getPixelWriter();

			pixelWriter.setArgb(i + sizeMask / 2, j + sizeMask / 2,valueColor );
			
			
			}
		}
		return imageOut;
	}

	private int middleValue(int[] Result, int Mask) {
		int value = 0;
		int p = (int) Math.pow(Mask, 2);
		for(int i=0;i<p -1 ;i++) {
            for(int j=0;j<p-1-i;j++) {
                if (Result[j]>Result[j+1]) {
                    int aux;
                    aux=Result[j];
                    Result[j]=Result[j+1];
                    Result[j+1]=aux;
                }
            }
        }
		if(Mask%2!=0){
			value=Result[p /2];
		}else{
			int red=0;
			int green=0;
			int blue=0;
			Color color1 =new Color(Result[(p +1)/2]);
			Color color2 =new Color(Result[((p+1)/2)-1]);
			red = (color1.getRed() + color2.getRed())/2;
			green = (color1.getGreen() + color2.getGreen())/2;
			blue = (color1.getBlue() + color2.getBlue())/2;
			value=(new Color(red,green,blue)).getRGB() ;
		}
		return value;
	}

}
