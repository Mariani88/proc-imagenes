package untref.service.activecontours;

import untref.domain.ImagePosition;
import untref.domain.activecontours.ActiveContourCurves;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static untref.domain.utils.ImageValuesTransformer.toDouble;

public class ActiveContourCurveDetectorServiceImpl implements ActiveContourCurveDetectorService {

	@Override
	public ActiveContourCurves calculateCurves(List<ImagePosition> lIn) {
		List<ImagePosition> pixels = new LinkedList<>(lIn);
		int n = pixels.size();
		int initialPixel = 0;
		Map<ImagePosition, Integer> curveByPosition = new HashMap<>();
		int closedCurve = 1;
		int amountPixels = 1;

		while (initialPixel < n) {
			System.out.println("primer while");
			int actualPixel = initialPixel;
			curveByPosition.put(pixels.get(actualPixel), closedCurve);
			//int amountPixels = 1;
			boolean foundFourNeighboring = true;
			boolean foundEightNeighboring = true;

			while (foundFourNeighboring
					|| foundEightNeighboring/*foundFourNeighboring(pixels.get(actualPixel)) || foundEightNeighboring(pixels.get(actualPixel))*/) {
				System.out.println("segundo while");
				int v4 = -1;
				int v8 = -1;

				for (int i = 0; i < n; i++) {
					if (curveByPosition.get(pixels.get(i)) == null) {
						if (isFourNeighboring(pixels.get(i), pixels.get(actualPixel))) {
							v4 = i;
							break;
						} else if (isEightNeighboring(pixels.get(i), pixels.get(actualPixel))) {
							v8 = i;
							break;  //esto no aparece,sacar si causa problemas
						}
					}
				}

				if (v4 != -1) {
					curveByPosition.put(pixels.get(v4), closedCurve);
					amountPixels++;
					actualPixel = v4;
				} else if (v8 != -1) {
					curveByPosition.put(pixels.get(v8), closedCurve);
					amountPixels++;
					actualPixel = v8;
				} else {
					foundEightNeighboring = false;
					foundFourNeighboring = false;
				}
			}

			if (isFourNeighboring(pixels.get(actualPixel), pixels.get(initialPixel)) || isEightNeighboring(pixels.get(actualPixel),
					pixels.get(initialPixel))) {
				closedCurve++;
			} else {
				unmark(curveByPosition, closedCurve);
				initialPixel++;
			}

			while (initialPixel < n) {
				if (curveByPosition.get(pixels.get(initialPixel)) != null) {
					initialPixel++;
				}else{
					break;
				}
				System.out.println("tercer while");
			}
		}

		List<List<ImagePosition>> curves = mapToCurves(curveByPosition); //error aca
		return new ActiveContourCurves(curves, toDouble(amountPixels) / toDouble(n));
	}

	private List<List<ImagePosition>> mapToCurves(Map<ImagePosition, Integer> curveByPosition) {
		Map<Integer, List<ImagePosition>> pixelsByCurve = new HashMap<>();
		curveByPosition.forEach(new BiConsumer<ImagePosition, Integer>() {
			@Override
			public void accept(ImagePosition key, Integer value) {
				pixelsByCurve.computeIfAbsent(value, new Function<Integer, List<ImagePosition>>() {
					@Override
					public List<ImagePosition> apply(Integer integer) {
						return new ArrayList<>();
					}
				}).add(key);
			}
		});
		return pixelsByCurve.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
	}

	private void unmark(Map<ImagePosition, Integer> curveByPosition, int closedCurve) {
		List<ImagePosition> pixelsToRemove = curveByPosition.entrySet().stream()
				.filter(imagePositionIntegerEntry -> imagePositionIntegerEntry.getValue().equals(closedCurve)).map(Map.Entry::getKey)
				.collect(Collectors.toList());
		pixelsToRemove.forEach(curveByPosition::remove);
	}

	private boolean isFourNeighboring(ImagePosition candidateNeighboring, ImagePosition positionToEvaluate) {
		boolean rightNeighboring = candidateNeighboring.equals(new ImagePosition(positionToEvaluate.getRow(), positionToEvaluate.getColumn() + 1));
		boolean leftNeighboring = candidateNeighboring.equals(new ImagePosition(positionToEvaluate.getRow(), positionToEvaluate.getColumn() - 1));
		boolean bottomNeighboring = candidateNeighboring.equals(new ImagePosition(positionToEvaluate.getRow() + 1, positionToEvaluate.getColumn()));
		boolean topNeighboring = candidateNeighboring.equals(new ImagePosition(positionToEvaluate.getRow() - 1, positionToEvaluate.getColumn()));
		return rightNeighboring || leftNeighboring || bottomNeighboring || topNeighboring;
	}

	private boolean isEightNeighboring(ImagePosition candidateNeighboring, ImagePosition positionToEvaluate) {
		boolean rightBottomNeighboring = candidateNeighboring
				.equals(new ImagePosition(positionToEvaluate.getRow() + 1, positionToEvaluate.getColumn() + 1));
		boolean topRightNeighboring = candidateNeighboring
				.equals(new ImagePosition(positionToEvaluate.getRow() - 1, positionToEvaluate.getColumn() + 1));
		boolean bottomLeftNeighboring = candidateNeighboring
				.equals(new ImagePosition(positionToEvaluate.getRow() + 1, positionToEvaluate.getColumn() - 1));
		boolean topLeftNeighboring = candidateNeighboring
				.equals(new ImagePosition(positionToEvaluate.getRow() - 1, positionToEvaluate.getColumn() - 1));

		return isFourNeighboring(candidateNeighboring, positionToEvaluate) || rightBottomNeighboring || topLeftNeighboring || topRightNeighboring
				|| bottomLeftNeighboring;
	}

}