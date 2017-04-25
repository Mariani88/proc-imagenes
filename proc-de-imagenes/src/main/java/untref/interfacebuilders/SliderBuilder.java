package untref.interfacebuilders;

import javafx.scene.control.Slider;
import javafx.geometry.Orientation;

public class SliderBuilder {
	Slider slider;
	int maxScale, minScale, positionY, positionX;
	double interval;
	public SliderBuilder(int maxScale, int minScale, double interval) {
		this.maxScale = maxScale;
		this.minScale = minScale;
		this.interval = interval;
		
	}

	public Slider getSlider() {
		 //this.slider = new Slider(minScale, maxScale, interval);
		
		
	//	this.slider.setLayoutY(positionY);
		/*slider.setShowTickMarks(true);
		//slider.setShowTickLabels(true);
		slider.setMajorTickUnit(0.25f);
		slider.setBlockIncrement(0.1f);*/
		this.slider = new Slider(0, 255, 1);
		this.slider.setLayoutX(20);
	 	slider.setOrientation(Orientation.VERTICAL);
		slider.setPrefHeight(150);
		slider.setShowTickMarks(true);
		slider.setMajorTickUnit(10);
		slider.setMinorTickCount(0);
		slider.setShowTickLabels(false);

		return slider;
	}

}
