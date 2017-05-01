package untref.eventhandlers.noiseventHandlers;

import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import untref.controllers.nodeutils.ParametersWindowsFactory;
import untref.domain.aleatorygenerator.AleatoryNumberExponential;
import untref.domain.aleatorygenerator.AleatoryNumberGenerator;
import untref.domain.aleatorygenerator.AleatoryNumberNormalGauss;
import untref.domain.aleatorygenerator.AleatoryNumberRayleigh;

import java.util.Arrays;
import java.util.Random;

public class NoiseTypeController {

	private static final String EXPONENTIAL_DISTRIBUTION = "Exponential distribution";
	private static final String RAYLEIGH_DISTRIBUTION = "Rayleigh distribution";
	private static final String NORMAL_GAUSS_DISTRIBUTION = "Normal Gauss distribution";
	private AleatoryNumberGenerator[] aleatoryNumberGenerator;

	public NoiseTypeController(AleatoryNumberGenerator aleatoryNumberGenerator[]) {
		this.aleatoryNumberGenerator = aleatoryNumberGenerator;
	}

	public MenuButton create() {
		Label distributionParam1 = new Label();
		TextField distributionParam1Value = new TextField();
		Label distributionParam2 = new Label();
		TextField distributionParam2Value = new TextField();
		Random random = new Random();

		MenuButton noiseDistributionsButton = new MenuButton("Noise distributions");
		MenuItem exponentialDistributionItem = new MenuItem(EXPONENTIAL_DISTRIBUTION);
		MenuItem rayleighDistributionItem = new MenuItem(RAYLEIGH_DISTRIBUTION);
		MenuItem normalGaussDistributionItem = new MenuItem(NORMAL_GAUSS_DISTRIBUTION);

		ParametersWindowsFactory parametersWindowsFactory = new ParametersWindowsFactory();
		exponentialDistributionItem.setOnAction(event16 -> {
			distributionParam1.setText("lambda");
			noiseDistributionsButton.setText(EXPONENTIAL_DISTRIBUTION);
			parametersWindowsFactory.create(Arrays.asList(distributionParam1, distributionParam1Value),
					mouseEvent -> aleatoryNumberGenerator[0] = new AleatoryNumberExponential(random, toDouble(distributionParam1Value)));
		});

		rayleighDistributionItem.setOnAction(event15 -> {
			distributionParam1.setText("epsilon");
			noiseDistributionsButton.setText(RAYLEIGH_DISTRIBUTION);
			parametersWindowsFactory.create(Arrays.asList(distributionParam1, distributionParam1Value),
					mouseEvent -> aleatoryNumberGenerator[0] = new AleatoryNumberRayleigh(random, toDouble(distributionParam1Value)));
		});

		normalGaussDistributionItem.setOnAction(event14 -> {
			distributionParam1.setText("mu");
			distributionParam2.setText("sigma");
			noiseDistributionsButton.setText(NORMAL_GAUSS_DISTRIBUTION);
			parametersWindowsFactory.create(Arrays.asList(distributionParam1, distributionParam1Value, distributionParam2, distributionParam2Value),
					mouseEvent -> aleatoryNumberGenerator[0] = new AleatoryNumberNormalGauss(random, toDouble(distributionParam1Value),
							toDouble(distributionParam2Value)));
		});

		noiseDistributionsButton.getItems().addAll(exponentialDistributionItem, rayleighDistributionItem, normalGaussDistributionItem);
		return noiseDistributionsButton;
	}

	private Double toDouble(TextField distributionParam1Value) {
		return Double.valueOf(distributionParam1Value.getText());
	}

}