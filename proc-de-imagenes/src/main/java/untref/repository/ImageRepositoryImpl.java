package untref.repository;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import ij.ImagePlus;
import ij.io.Opener;

public class ImageRepositoryImpl implements ImageRepository {

	@Override
	public Image findImage(File file) {
		
BufferedImage bufferedImage = null;
		
 
 if (! getFileExtension(file).equalsIgnoreCase("RAW")){
		Opener opener = new Opener();
		

		Path path = file.toPath();

		ImagePlus img = opener.openImage(path.toString());

		bufferedImage = img.getBufferedImage();
		 
		}else{ 
			ImageRaw imageRaw=new ImageRaw(100,100);
			try {
				bufferedImage=imageRaw.abrirImagen(file.toPath().toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		
		return SwingFXUtils.toFXImage(bufferedImage, null);
/*
		try {
			BufferedImage bufferedImage = ImageIO.read(file);
			return SwingFXUtils.toFXImage(bufferedImage, null);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}*/
	}

	@Override
	public void storeImage(Image image, File file) {
		if (file != null) {
			try {
				BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);

				// Remove alpha-channel from buffered image:
				BufferedImage imageRGB = new BufferedImage(toInt(image.getWidth()), toInt(image.getHeight()), BufferedImage.OPAQUE);
				Graphics2D graphics = imageRGB.createGraphics();
				graphics.drawImage(bufferedImage, 0, 0, null);
				ImageIO.write(imageRGB, "jpg", file);
				graphics.dispose();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private int toInt(double value) {
		return (int) value;
	}
	
	private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }

}