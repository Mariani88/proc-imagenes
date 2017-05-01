package untref.eventhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import untref.controllers.nodeutils.ImageSetter;
import untref.controllers.nodeutils.ParametersWindowsFactory;
import untref.domain.aleatorygenerator.AleatoryNumberGenerator;
import untref.domain.noisetypes.AdditiveNoise;
import untref.domain.noisetypes.MultiplicativeNoise;
import untref.domain.noisetypes.NoiseType;
import untref.eventhandlers.noiseventHandlers.NoiseTypeController;
import untref.eventhandlers.noiseventHandlers.NoiseTypeSelectionEventHandler;
import untref.service.NoiseService;
import untref.service.NoiseServiceImpl;
import untref.service.arithmeticoperations.noise.AdditiveNoiseAdder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NoiseParametersEventHandler implements EventHandler<ActionEvent> {

	private ImageView imageView;
	private ImageView imageViewResult;
	private NoiseService noiseService;
	private ParametersWindowsFactory parametersWindowsFactory;

	public NoiseParametersEventHandler(ImageView imageView, ImageView imageViewResult, NoiseService noiseService) {
		this.imageView = imageView;
		this.imageViewResult = imageViewResult;
		this.noiseService = noiseService;
		parametersWindowsFactory = new ParametersWindowsFactory();
	}

	@Override
	public void handle(ActionEvent event) {
		AleatoryNumberGenerator aleatoryNumberGenerator[] = new AleatoryNumberGenerator[1];
		final NoiseType[] noiseType = new NoiseType[1];
		MenuButton noiseDistributionsButton = new NoiseTypeController(aleatoryNumberGenerator).create();
		RadioButton additiveButton = new RadioButton("additive");
		RadioButton multiplicativeButton = new RadioButton("multiplicative");
		additiveButton
				.setOnMouseClicked(new NoiseTypeSelectionEventHandler(multiplicativeButton, noiseType, new AdditiveNoise(new AdditiveNoiseAdder())));
		multiplicativeButton.setOnMouseClicked(new NoiseTypeSelectionEventHandler(additiveButton, noiseType, new MultiplicativeNoise()));
		Label contaminationPercentLabel = new Label("contamination percent");
		TextField contaminationPercentValue = new TextField();
		List<Node> parametersNodes = new ArrayList<>();
		parametersNodes.add(noiseDistributionsButton);
		parametersNodes.addAll(Arrays.asList(additiveButton, multiplicativeButton, contaminationPercentLabel, contaminationPercentValue));
		parametersWindowsFactory.create(parametersNodes, event1 -> {
			Image imageWithNoise = noiseService
					.addNoiseToImage(imageView.getImage(), toDouble(contaminationPercentValue), aleatoryNumberGenerator[0], noiseType[0]);
			ImageSetter.set(imageViewResult, imageWithNoise);
		});
	}

	private Double toDouble(TextField distributionParam1Value) {
		return Double.valueOf(distributionParam1Value.getText());
	}
}