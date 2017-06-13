package untref.edge.service.hough;

import java.awt.Color;
import java.awt.image.BufferedImage;

import java.util.Vector;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import untref.controllers.DrawingSelect;
import untref.edge.service.canny.CannyEdgeDetector;

import java.io.File;


public class HoughTransform {

	private int neighbourhoodSize ;
	private int maxTheta  ;
	private double thetaStep  ;
	private int width, height;
	private int[][] houghArray;
	private float centerX, centerY;
	private int houghHeight;
	private int doubleHeight;
	private int numPoints;
	private int hitsPoint;
	private double[] sinCache;
	private double[] cosCache;

	private BufferedImage edgeDetector(Image image) {
		CannyEdgeDetector canny = new CannyEdgeDetector();
		canny.setSourceImage(image);
		canny.setGaussianKernelRadius(2);
		canny.setHighThreshold(130);
		canny.setLowThreshold(100);
		canny.process();

		return canny.getEdgesImage();
	}

	public void setSettings() {

	}

	public BufferedImage start(Image imagefx) throws Exception {

		BufferedImage image = this.edgeDetector(imagefx);
		this.setSizeImage(image.getWidth(), image.getHeight());
		initialise();
		this.addPoints(image);

		Vector<HoughLine> lines = this.getLines(this.hitsPoint);
		//DrawingSelect a = new untref.controllers.DrawingSelect();
		//a.start(SwingFXUtils.toFXImage(getHoughArrayImage(), null));
		for (int j = 0; j < lines.size(); j++) {
			HoughLine line = lines.elementAt(j);
			line.draw(image, Color.RED.getRGB());
		}

		// ImageIO.write(image, "jpg",new
		// File("C:/Users/ignacio/Pictures/resourcesout.jpg"));
		return image;
	}

	private  void setSizeImage(int width, int height) {

		this.width = width;
		this.height = height;
	}

	private  void initialise() {
		
		thetaStep = Math.PI / maxTheta;		 
		 neighbourhoodSize = 4;
		// CALCULO EL TAMAÑO DE LA MATRIZ
		houghHeight = (int) (Math.sqrt(2) * Math.max(height, width)) / 2;

		doubleHeight = 2 * houghHeight;

		houghArray = new int[maxTheta][doubleHeight];

		centerX = width / 2;
		centerY = height / 2;

		numPoints = 0;

		sinCache = new double[maxTheta];
		cosCache = sinCache.clone();
		for (int t = 0; t < maxTheta; t++) {
			double realTheta = t * thetaStep;
			sinCache[t] = Math.sin(realTheta);
			cosCache[t] = Math.cos(realTheta);
		}
	}

	private  void addPoints(BufferedImage image) {

		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				// si no es negro el punto es decir es un discriminador :C
				if ((image.getRGB(x, y) & 0x000000ff) != 0) {
					addPoint(x, y);
				}
			}
		}
	}

	private  void addPoint(int x, int y) {

		for (int t = 0; t < maxTheta; t++) {
			int r = (int) (((x - centerX) * cosCache[t]) + ((y - centerY) * sinCache[t]));
			r += houghHeight;
			if (r < 0 || r >= doubleHeight)
				continue;

			houghArray[t][r]++;
		}

		numPoints++;
	}

	
	private  Vector<HoughLine> getLines(int threshold) {

		 
		Vector<HoughLine> lines = new Vector<HoughLine>(20);

		 
		if (numPoints == 0)
			return lines;

		 
		for (int t = 0; t < maxTheta; t++) {
			loop: for (int r = neighbourhoodSize; r < doubleHeight - neighbourhoodSize; r++) {
				if (houghArray[t][r] > threshold) {

					int peak = houghArray[t][r];

					 
					for (int dx = -neighbourhoodSize; dx <= neighbourhoodSize; dx++) {
						for (int dy = -neighbourhoodSize; dy <= neighbourhoodSize; dy++) {
							int dt = t + dx;
							int dr = r + dy;
							if (dt < 0)
								dt = dt + maxTheta;
							else if (dt >= maxTheta)
								dt = dt - maxTheta;
							if (houghArray[dt][dr] > peak) {
								 
								continue loop;
							}
						}
					}

					 
					double theta = t * thetaStep;
				 
					lines.add(new HoughLine(theta, r));

				}
			}
		}

		return lines;
	}

	 
	private  int getHighestValue() {
		int max = 0;
		for (int t = 0; t < maxTheta; t++) {
			for (int r = 0; r < doubleHeight; r++) {
				if (houghArray[t][r] > max) {
					max = houghArray[t][r];
				}
			}
		}
		return max;
	}
 
	private  BufferedImage getHoughArrayImage() {
		int max = getHighestValue();
		BufferedImage image = new BufferedImage(maxTheta, doubleHeight, BufferedImage.TYPE_INT_ARGB);
		for (int t = 0; t < maxTheta; t++) {
			for (int r = 0; r < doubleHeight; r++) {
				double value = 255 * ((double) houghArray[t][r]) / max;
				int v = 255 - (int) value;
				int c = new Color(v, v, v).getRGB();
				image.setRGB(t, r, c);
			}
		}
		return image;
	}

	public void setThetaMax(int thetaMax) {
		this.maxTheta=thetaMax*2;
		
	}

	public void setPointMin(int minimunPoint) {
		this.hitsPoint=minimunPoint;
		
	}

}
