package untref.service.activecontours;

import untref.domain.ImagePosition;
import untref.domain.activecontours.ActiveContourCurves;

import java.util.List;

public interface ActiveContourCurveDetectorService {
	ActiveContourCurves calculateCurves(List<ImagePosition> lIn);
}