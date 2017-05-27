package untref.domain.susan;

import javafx.scene.paint.Color;

public class SusanEdge implements ImageElementSusan {

	@Override
	public Color calculateElement(double elementDetectionParameter, Double accumulateDelta, Color imageColor) {

		if (0.5 - accumulateDelta <= elementDetectionParameter && elementDetectionParameter <= 0.5 + accumulateDelta) {
			return Color.rgb(255, 255, 255);
		}

		return Color.BLACK;
	}
}