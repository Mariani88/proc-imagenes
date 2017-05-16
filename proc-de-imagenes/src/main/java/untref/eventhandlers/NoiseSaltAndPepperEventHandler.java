package untref.eventhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import untref.controllers.nodeutils.ImageSetter;
import untref.controllers.nodeutils.ParametersWindowsFactory;
import untref.service.NoiseService;

import java.util.Arrays;

public class NoiseSaltAndPepperEventHandler implements EventHandler<ActionEvent> {

	private ImageView imageView;
	private final ImageView imageViewResult;
	private final NoiseService noiseService;
	private ParametersWindowsFactory parametersWindowsFactory;

	public NoiseSaltAndPepperEventHandler(ImageView imageView, ImageView imageViewResult, NoiseService noiseService) {
		this.imageView = imageView;
		this.imageViewResult = imageViewResult;
		this.noiseService = noiseService;
		parametersWindowsFactory = new ParametersWindowsFactory();
	}

	@Override
	public void handle(ActionEvent event) {
		Label blackPixelProbability = new Label("black pixel probability");
		TextField blackPixelProbabilityValue = new TextField();
		parametersWindowsFactory.create(Arrays.asList(blackPixelProbability, blackPixelProbabilityValue), event1 -> {
			Image imageWithNoise = noiseService
					.addSaltAndPepperNoiseToImage(imageView.getImage(), Double.valueOf(blackPixelProbabilityValue.getText()));
			ImageSetter.set(imageViewResult, imageWithNoise);
		});
	}
}