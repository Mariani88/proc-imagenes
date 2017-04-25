package untref.interfacebuilders;

import javafx.scene.control.Slider;

public class SliderBuilder {
	Slider slider;

	public SliderBuilder(int maxScale, int minScale, int interval) {

		this.slider = new Slider(minScale, maxScale, interval);

	}

	public Slider getSlider() {

		slider.setShowTickMarks(true);
		slider.setShowTickLabels(true);
		slider.setMajorTickUnit(0.25f);
		slider.setBlockIncrement(0.1f);

		return slider;
	}

}
