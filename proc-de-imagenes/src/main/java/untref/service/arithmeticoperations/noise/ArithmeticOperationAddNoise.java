package untref.service.arithmeticoperations.noise;

import javafx.scene.paint.Color;
import untref.domain.TemporalColor;

public interface ArithmeticOperationAddNoise {
	TemporalColor add(Color color, TemporalColor noise);
}