package untref.repository;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ImageRaw {

	private Integer ancho;
	private Integer alto;
	private Integer[][] pixeles;
 
	
	public ImageRaw(Integer ancho,Integer alto){
		this.ancho=ancho;
		this.alto=alto;
		this.pixeles = new Integer[ancho][alto];
		 
	}
	
	public int getAlto() {
		return alto;
	}

	public int getAncho() {
		return ancho;
	}

	 
	
	public BufferedImage abrirImagen(String name) throws IOException {
		FileInputStream is =this.openFile(name);
		return cargarImagen(is);
	}
	
	private FileInputStream openFile(String name) throws FileNotFoundException {
		File imgFile =  new File(name);
		FileInputStream input = new FileInputStream(imgFile);
		return input;
	}
	
	private BufferedImage cargarImagen(FileInputStream br) throws IOException{
		Color color;
		BufferedImage buff = new BufferedImage(ancho, alto,1);
		for (int i=0; i < ancho; i++){
			for (int j=0; j < alto; j++){		
				pixeles[i][j] = br.read();
				color = new Color(pixeles[i][j],pixeles[i][j],pixeles[i][j]);
				 ;
				buff.setRGB(j,i, color.getRGB());
			}
		}
		br.close();
		return buff;
	}

}
