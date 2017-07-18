package untref.domain.activecontours;

import untref.domain.ImagePosition;

import java.util.List;

public class ActiveContourCurves {

	private final List<List<ImagePosition>> curves;
	private final double detectionFactor;

	public ActiveContourCurves(List<List<ImagePosition>> curves, double detectionFactor) {
		this.curves = curves;
		this.detectionFactor = detectionFactor;
	}

	public List<List<ImagePosition>> getCurves() {
		return curves;
	}

	public double getDetectionFactor() {
		return detectionFactor;
	}
}