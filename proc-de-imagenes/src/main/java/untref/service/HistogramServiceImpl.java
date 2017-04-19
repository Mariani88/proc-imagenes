package untref.service;

import javafx.scene.image.Image;

public class HistogramServiceImpl implements HistogramService {
	int arrayHistogram[];
	Image image;
	int totalPixels;
	ImageGetColorRGB serviceImageRgb;

	public HistogramServiceImpl(Image image) {
		this.arrayHistogram = new int[256];
		this.totalPixels = 0;
		this.image = image;
	}

	@Override
	public int[] getArrayHistogram() {
		this.serviceImageRgb = new ImageGetColorRGBImpl(this.image);
		this.totalPixels = this.serviceImageRgb.getTotalPixel();
		for (int i = 0; i < this.image.getWidth(); i++)
			for (int j = 0; j < this.image.getHeight(); j++){
					
			
				arrayHistogram[this.serviceImageRgb.getValueRgb(i, j)] += 1;
			}
		
		return arrayHistogram;
	}

	@Override
	public void BarChartDraw() {
		untref.service.figuresAndGrafic.BarChartDraw barChart = new untref.service.figuresAndGrafic.BarChartDraw(
				"GrayScale", "Pixel Quantity");
		this.getArrayHistogram();
		for (int j = 0; j < 256; j++)

			barChart.setData(String.valueOf(j), arrayHistogram[j]);
		barChart.drawBarChart();
	}
}