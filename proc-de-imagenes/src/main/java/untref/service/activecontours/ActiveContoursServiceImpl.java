package untref.service.activecontours;

import javafx.scene.image.Image;
import untref.domain.ImagePosition;
import untref.domain.activecontours.ActiveContourCurves;
import untref.domain.activecontours.Contour;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static untref.domain.utils.ImageValuesTransformer.toInt;

public class ActiveContoursServiceImpl implements ActiveContoursService {

	private final ContourDomainService contourDomainService;
	private final FdFunction fdFunction;
	private double processedImages = 0;
	private double totalLin = 0;
	private double average;
	private ImagePosition initialImagePosition;
	private ImagePosition initialImagePosition2;
	private ImagePosition oldCentroid;

	public ActiveContoursServiceImpl() {
		contourDomainService = new ContourDomainServiceImpl();
		fdFunction = new FdFunction();
	}

	@Override
	public Contour initializeActiveContours(Image image, ImagePosition imagePosition, ImagePosition imagePosition2) {
		this.initialImagePosition = imagePosition;
		this.initialImagePosition2 = imagePosition2;
		return contourDomainService.createContour(imagePosition, imagePosition2, image);
	}

	@Override
	public Contour adjustContours(Contour contour, Double colorDelta) {
		List<ImagePosition> lOut = contour.getlOut();
		List<ImagePosition> lIn = contour.getlIn();

		for (int index = 0; index < lOut.size(); index++) {
			ImagePosition imagePosition = lOut.get(index);
			boolean isPositive = fdFunction.apply(imagePosition, contour.getOriginalImage(), contour.getObjectColorAverage(), colorDelta);
			expandContours(isPositive, contour, imagePosition);
		}

		contour.moveInvalidLinToObject();

		for (int index = 0; index < lIn.size(); index++) {
			ImagePosition imagePosition = lIn.get(index);
			boolean isPositive = fdFunction.apply(imagePosition, contour.getOriginalImage(), contour.getObjectColorAverage(), colorDelta);
			shortenContour(isPositive, contour, imagePosition);
		}

		contour.moveInvalidLoutToBackground();
		contour.updateImage();
		return contour;
	}

	@Override
	public Contour applyContourToNewImage(Contour contour, Image image) {
		return contour.applyToNewImage(image);
	}

	@Override
	public Contour adjustContoursAutomatically(Contour contour, Double colorDelta, Double reductionTolerance, int expandSize) {
		int iterations = 20;
		oldCentroid = calculateCentroid(contour.getlIn());
		for (int index = 0; index < iterations; index++) {
			contour = adjustContoursForVideo(contour, colorDelta);
		}
		updateLinAverage(contour.getlIn());
		contour = evaluateOclusion(contour.getlIn(), reductionTolerance, contour, expandSize);
		ActiveContourCurves activeContourCurves = evaluateOclusionByEqualsColorObject(contour);
		System.out.println("curves: " + activeContourCurves.getCurves().size());
		System.out.println("detection factor:" + activeContourCurves.getDetectionFactor());
		return contour;
	}

	private ActiveContourCurves evaluateOclusionByEqualsColorObject(Contour contour) {
		ActiveContourCurves activeContourCurves = new ActiveContourCurveDetectorServiceImpl().calculateCurves(contour.getlIn());
		List<List<ImagePosition>> curves = activeContourCurves.getCurves();
		int candidateCurve = -1;

		if (curves.size() == 1) {
			oldCentroid = calculateCentroid(activeContourCurves.getCurves().get(0));
		} else {
			List<ImagePosition> centroids = curves.stream().map(curve -> calculateCentroid(curve)).collect(Collectors.toList());
			double distance = Double.MAX_VALUE;
			for (int centroidIndex = 0; centroidIndex < centroids.size(); centroidIndex++) {
				double distanceToOldCentroid = calculateDistance(oldCentroid, centroids.get(centroidIndex));
				System.out.println("curve:" + centroidIndex + " distance:" + distanceToOldCentroid + " pixels:" + curves.get(centroidIndex).size());
				if (distance > distanceToOldCentroid) {
					distance = distanceToOldCentroid;
					candidateCurve = centroidIndex;
				}
			}

			if (candidateCurve != -1 && 0.2 < activeContourCurves.getDetectionFactor()) {
				List<List<ImagePosition>> curvesToDelete = new ArrayList<>(curves);
				curvesToDelete.remove(candidateCurve);
				contour.deleteCurves(curvesToDelete);
				contour.updateImage();
			}
		}

		return activeContourCurves;
	}

	private double calculateDistance(ImagePosition oldCentroid, ImagePosition centroid) {
		int columnDifference = oldCentroid.getColumn() - centroid.getColumn();
		int rowDifference = oldCentroid.getRow() - centroid.getRow();
		return Math.sqrt(Math.pow(columnDifference, 2) + Math.pow(rowDifference, 2));
	}

	private ImagePosition calculateCentroid(List<ImagePosition> curve) {
		int totalRow = 0;
		int totalColumn = 0;

		for (ImagePosition imagePosition : curve) {
			totalRow += imagePosition.getRow();
			totalColumn += imagePosition.getColumn();
		}

		return new ImagePosition(totalRow / curve.size(), totalColumn / curve.size());
	}

	private Contour adjustContoursForVideo(Contour contour, Double colorDelta) {
		List<ImagePosition> lOut = contour.getlOut();
		List<ImagePosition> lIn = contour.getlIn();

		for (int index = 0; index < lOut.size(); index++) {
			ImagePosition imagePosition = lOut.get(index);
			boolean isPositive = fdFunction.apply(imagePosition, contour.getOriginalImage(), contour.getObjectColorAverage(), colorDelta);
			expandContours(isPositive, contour, imagePosition);
		}

		contour.moveInvalidLinToObject();

		for (int index = 0; index < lIn.size(); index++) {
			ImagePosition imagePosition = lIn.get(index);
			boolean isPositive = fdFunction.apply(imagePosition, contour.getOriginalImage(), contour.getObjectColorAverage(), colorDelta);
			shortenContour(isPositive, contour, imagePosition);
		}

		contour.moveInvalidLoutToBackground();
		contour.updateImage();
		return contour;
	}

	private Contour evaluateOclusion(List<ImagePosition> lIn, Double reductionTolerance, Contour contour, int expandSize) {
		System.out.println("lIn: " + lIn.size() + " tolerance: " + reductionTolerance * average);
		if (lIn.size() < reductionTolerance * average) {
			ImagePosition imagePosition = new ImagePosition(Math.max(this.initialImagePosition.getRow() - expandSize, 0),
					Math.max(this.initialImagePosition.getColumn() - expandSize, 0));
			ImagePosition imagePosition2 = new ImagePosition(
					Math.min(this.initialImagePosition2.getRow() + expandSize, toInt(contour.getOriginalImage().getHeight() - 1)),
					Math.min(this.initialImagePosition2.getColumn() + expandSize, toInt(contour.getOriginalImage().getWidth() - 1)));
			Contour newContour = contourDomainService
					.createContourWithColorAverage(imagePosition, imagePosition2, contour.getOriginalImage(), contour.getObjectColorAverage());
			resetVideoAttributes(newContour);
			return newContour;
		} else {
			return contour;
		}
	}

	private void resetVideoAttributes(Contour contour) {
		processedImages = 1;
		totalLin = contour.getlIn().size();
		average = totalLin / processedImages;
	}

	private void updateLinAverage(List<ImagePosition> lIn) {
		processedImages++;
		totalLin += lIn.size();
		System.out.println("processed images:" + processedImages);
		average = totalLin / processedImages;
		System.out.println("average:" + average);
	}

	private void shortenContour(boolean isPositive, Contour contour, ImagePosition imagePosition) {
		if (!isPositive) {
			contour.moveFromLinToLout(imagePosition);
			Set<ImagePosition> objectNeighborings = contour.getAllObjectNeighboring(imagePosition);
			contour.addToLin(objectNeighborings);
		}
	}

	private void expandContours(boolean isPositive, Contour contour, ImagePosition imagePosition) {
		if (isPositive) {
			contour.removeFromLout(imagePosition);
			contour.addToLIn(imagePosition);
			Set<ImagePosition> backgroundNeighborings = contour.getAllBackgroundNeighboring(imagePosition);
			contour.addToLout(backgroundNeighborings);
		}
	}
}