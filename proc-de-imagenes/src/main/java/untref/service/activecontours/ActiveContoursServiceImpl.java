package untref.service.activecontours;

import javafx.scene.image.Image;
import untref.domain.ImagePosition;
import untref.domain.activecontours.Contour;

import java.util.List;
import java.util.Set;

import static untref.domain.utils.ImageValuesTransformer.toInt;

public class ActiveContoursServiceImpl implements ActiveContoursService {

	private final ContourDomainService contourDomainService;
	private final FdFunction fdFunction;
	private double processedImages = 0;
	private double totalLin = 0;
	private double average;
	private ImagePosition initialImagePosition;
	private ImagePosition initialImagePosition2;

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
	public Contour adjustContoursAutomatically(Contour contour, Double colorDelta, Double reductionTolerance) {
		int iterations = 20;

		for (int index = 0; index < iterations; index++) {
			contour = adjustContoursForVideo(contour, colorDelta);
		}
		updateLinAverage(contour.getlIn());
		contour = evaluateOclusion(contour.getlIn(), reductionTolerance, contour);
		return contour;
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

	private Contour evaluateOclusion(List<ImagePosition> lIn, Double reductionTolerance, Contour contour) {
		System.out.println("lIn: " + lIn.size() + " tolerance: " + reductionTolerance * average);
		if (lIn.size() < reductionTolerance * average) {
			ImagePosition imagePosition = new ImagePosition(Math.max(this.initialImagePosition.getRow() - 60, 0),
					Math.max(this.initialImagePosition.getColumn() - 60, 0));
			ImagePosition imagePosition2 = new ImagePosition(
					Math.min(this.initialImagePosition2.getRow() + 60, toInt(contour.getOriginalImage().getHeight() - 1)),
					Math.min(this.initialImagePosition2.getColumn() + 60, toInt(contour.getOriginalImage().getWidth() - 1)));
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