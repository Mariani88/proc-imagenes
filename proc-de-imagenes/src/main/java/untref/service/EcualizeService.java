package untref.service;



import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;



public class EcualizeService {
	
	
	public Image ecualizerHistogram(double [] histograma,Image image){
		
		double imageWidth=image.getWidth();
		double imageHeigth=image.getHeight();
		
		ImageGetColorRGB imageService= new ImageGetColorRGBImpl(image);
		
		int Summation=0;
		
		WritableImage imageOut = new WritableImage((int)imageWidth,(int)imageHeigth);
		
		for(int i=0;i<image.getWidth();i++ ){
			for(int j=0;j<image.getHeight();j++) {
				Summation=  (int) ((255)*(addPixel(histograma, (imageService.getValueRgb(i, j)))));
			Color colorRGB=Color.rgb(Summation,Summation,Summation);
				
			PixelWriter pixelWriter = imageOut.getPixelWriter();

			
					pixelWriter.setColor(i,j,colorRGB);
			
			
			
			
				
			}
		}
		return imageOut;	

}
	
	private double addPixel(double[] histogram , int limit){
		double add = 0;
		for(int i=0;i<=limit;i++){	
			add = add + histogram[i]
;	
		}
		return add/2;
	}	
	
}