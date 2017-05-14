package untref.service;

import javafx.scene.image.Image;
import untref.service.figuresAndGrafic.BarChartDraw;

import java.util.NavigableMap;
import java.util.TreeMap;

public class HistogramServiceImpl implements HistogramService {
	double arrayHistogram[];
	Image image;
	int totalPixels;
	ImageGetColorRGB serviceImageRgb;

	public HistogramServiceImpl(Image image) {
		this.arrayHistogram = new double[257];
		this.totalPixels = 0;
		this.image = image;
	}

	public HistogramServiceImpl() {
	}

	@Override
	public double[] getArrayHistogram() {
		this.serviceImageRgb = new ImageGetColorRGBImpl(this.image);
		this.totalPixels = this.serviceImageRgb.getTotalPixel();
		for (int i = 0; i < this.image.getWidth(); i++)
			for (int j = 0; j < this.image.getHeight(); j++) {
				arrayHistogram[this.serviceImageRgb.getGrayAverage(i, j)] += 1;
			}

		for (int i = 0; i < 256; i++) {
			arrayHistogram[i] = arrayHistogram[i] / totalPixels;
		}

		return arrayHistogram;

	}

	@Override
	public void BarChartDraw() {
		untref.service.figuresAndGrafic.BarChartDraw barChart = new untref.service.figuresAndGrafic.BarChartDraw("GrayScale", "Pixel Quantity");
		this.getArrayHistogram();
		for (int j = 0; j < 256; j++) {
			barChart.setData(String.valueOf(j), arrayHistogram[j]);
		}
		barChart.drawBarChart();
	}

	@Override
	public void sampleHistogramDraw(int[] sample) {
		BarChartDraw barChartDraw = new BarChartDraw("x", "probability density");
		int maxKey = calculateMax(sample) / 10;
		int minKey = calculateMin(sample) / 10;
		NavigableMap<Integer, Integer> groupedSample = obtainInitializedGroupedSample(maxKey, minKey);
		groupedSample = groupSample(sample, groupedSample);
		setDataToHistogram(sample, barChartDraw, groupedSample);
		barChartDraw.drawBarChart();
	}

	private int calculateMin(int[] sample) {
		int min = Integer.MAX_VALUE;

		for (int index = 0; index < sample.length; index++) {
			min = Math.min(min, sample[index]);
		}
		return min;
	}

	private void setDataToHistogram(int[] sample, BarChartDraw barChartDraw, NavigableMap<Integer, Integer> groupedSample) {
		for (int index = groupedSample.firstKey(); index < groupedSample.lastKey(); index++) {
			barChartDraw.setData(String.valueOf(index), calculateEventProportion(sample.length, groupedSample, index));
		}
	}

	private NavigableMap<Integer, Integer> groupSample(int[] sample, NavigableMap<Integer, Integer> groupedSample) {
		for (int index = 0; index < sample.length; index++) {
			Integer groupKey = groupedSample.floorKey(sample[index]);
			groupedSample.put(groupKey, groupedSample.get(groupKey) + 1);
		}

		return groupedSample;
	}

	private double calculateEventProportion(int sampleSize, NavigableMap<Integer, Integer> groupedSample, int index) {
		return (double) groupedSample.floorEntry(index).getValue() / (double) sampleSize;
	}

	private NavigableMap<Integer, Integer> obtainInitializedGroupedSample(int maxKey, int minKey) {
		minKey = minKey * 10 - 10;
		maxKey = maxKey * 10;
		NavigableMap<Integer, Integer> groupedSample = new TreeMap<>();

		for (int group = Math.min(minKey, 0); group < maxKey + 1; group += 10) {
			groupedSample.put(group, 0);
		}

		return groupedSample;
	}

	private int calculateMax(int[] sample) {
		int max = Integer.MIN_VALUE;

		for (int index = 0; index < sample.length; index++) {
			max = Math.max(max, sample[index]);
		}

		return max;
	}
}