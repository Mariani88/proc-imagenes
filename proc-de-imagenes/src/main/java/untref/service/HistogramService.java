package untref.service;

public interface HistogramService {

	double[] getArrayHistogram();

	void BarChartDraw();

	void sampleHistogramDraw(int[] sample);
}
