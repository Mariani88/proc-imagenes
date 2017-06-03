package untref.repository;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.imageio.ImageIO;



public class ImageRaw {
	private Archivo archivoActual;
	private static Imagen imagenActual;
	private static Imagen imagenOriginal;
	
	
	public Imagen cargarUnaImagenRawDesdeArchivo(Integer alto, Integer ancho, String name) {

		Imagen imagenADevolver = null;

		
				File imgFile =  new File(name);
				archivoActual = new Archivo(imgFile);
				FormatoDeImagen formatoDeLaImagen = FormatoDeImagen
						.getFormato(archivoActual.getExtension());

				BufferedImage bufferedImage;
				bufferedImage = leerUnaImagenRAW3(archivoActual, alto, ancho);

				Imagen imagen = new Imagen(bufferedImage, formatoDeLaImagen,
						archivoActual.getNombre());

				int[][] matrizCanal = MatricesManager.calcularMatrizDeLaImagen(
						bufferedImage, Canal.ROJO);

				imagen.setMatriz(matrizCanal, Canal.ROJO);
				imagen.setMatriz(matrizCanal, Canal.VERDE);
				imagen.setMatriz(matrizCanal, Canal.AZUL);

				imagenActual = new Imagen(imagen.getBufferedImage(),
						imagen.getFormato(), imagen.getNombre(),
						imagen.getMatriz(Canal.ROJO),
						imagen.getMatriz(Canal.VERDE),
						imagen.getMatriz(Canal.AZUL));
				imagenOriginal = new Imagen(imagen.getBufferedImage(),
						imagen.getFormato(), imagen.getNombre(),
						imagen.getMatriz(Canal.ROJO),
						imagen.getMatriz(Canal.VERDE),
						imagen.getMatriz(Canal.AZUL));
				imagenADevolver = new Imagen(imagen.getBufferedImage(),
						imagen.getFormato(), imagen.getNombre(),
						imagen.getMatriz(Canal.ROJO),
						imagen.getMatriz(Canal.VERDE),
						imagen.getMatriz(Canal.AZUL));

			

		return imagenADevolver;
	}

	
	
	private BufferedImage leerUnaImagenRAW3(Archivo archivoActual, int width,
			int height) {

		BufferedImage imagen = null;
		byte[] bytes;
		try {
			bytes = Files.readAllBytes(archivoActual.getFile().toPath());
			imagen = new BufferedImage(width, height,
					BufferedImage.TYPE_3BYTE_BGR);
			double[][] matrizDeImagen = new double[width][height];
			int contador = 0;
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {

					matrizDeImagen[j][i] = bytes[contador];

					int argb = 0;
					argb += -16777216; // 255 alpha
					int blue = ((int) bytes[contador] & 0xff);
					int green = ((int) bytes[contador] & 0xff) << 8;
					int red = ((int) bytes[contador] & 0xff) << 16;
					int color = argb + red + green + blue;
					imagen.setRGB(j, i, color);
					contador++;
				}
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
		return imagen;
	}

	private BufferedImage leerUnaImagen() throws IOException {
		BufferedImage bufferedImage = ImageIO.read(archivoActual.getFile());
		return bufferedImage;
	}

	
}
