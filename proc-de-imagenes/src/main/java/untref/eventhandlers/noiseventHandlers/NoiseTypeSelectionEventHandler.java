package untref.eventhandlers.noiseventHandlers;

import javafx.event.EventHandler;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import untref.domain.noisetypes.NoiseType;

public class NoiseTypeSelectionEventHandler implements EventHandler<MouseEvent> {

	private RadioButton buttonToUnselect;
	private NoiseType[] noiseType;
	private NoiseType noiseTypeImplementation;

	public NoiseTypeSelectionEventHandler(RadioButton buttonToUnselect, NoiseType[] noiseType, NoiseType noiseTypeImplementation) {
		this.buttonToUnselect = buttonToUnselect;
		this.noiseType = noiseType;
		this.noiseTypeImplementation = noiseTypeImplementation;
	}

	@Override
	public void handle(MouseEvent event13) {
		buttonToUnselect.setSelected(false);
		noiseType[0] = noiseTypeImplementation;
	}
}