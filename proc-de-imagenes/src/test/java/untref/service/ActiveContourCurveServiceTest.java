package untref.service;

import untref.domain.ImagePosition;
import untref.domain.activecontours.ActiveContourCurves;
import untref.service.activecontours.ActiveContourCurveDetectorService;
import untref.service.activecontours.ActiveContourCurveDetectorServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ActiveContourCurveServiceTest {

	private ActiveContourCurveDetectorService activeContourCurveService;

	@Before
	public void setUp() throws Exception {
		activeContourCurveService = new ActiveContourCurveDetectorServiceImpl();
	}

	@Test
	public void name() throws Exception {
		List<ImagePosition> lIn = new ArrayList<>();
		lIn.add(new ImagePosition(0, 1));
		lIn.add(new ImagePosition(1, 0));
		lIn.add(new ImagePosition(1, 2));
		lIn.add(new ImagePosition(2, 1));
		ActiveContourCurves activeContourCurves = activeContourCurveService.calculateCurves(lIn);
		Assert.assertEquals(1, activeContourCurves.getDetectionFactor(), 0.01);
		Assert.assertEquals(1, activeContourCurves.getCurves().size());
		assertCurvePixels(lIn, activeContourCurves.getCurves());
	}

	private void assertCurvePixels(List<ImagePosition> expectedPixels, List<List<ImagePosition>> curves) {
		Assert.assertTrue(curves.get(0).containsAll(expectedPixels));
	}
}
