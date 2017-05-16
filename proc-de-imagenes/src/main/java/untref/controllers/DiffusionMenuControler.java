package untref.controllers;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import untref.eventhandlers.AnisotropicEventHandler;
import untref.eventhandlers.IsotropicEventHandler;

public class DiffusionMenuControler {

	public Menu createDiffusionMenu(ImageView imageView, ImageView imageResultView) {
		Menu diffusionMenu = new Menu("Diffusion");
		MenuItem anisotropic = new MenuItem("anisotropic");
		MenuItem isotropic = new MenuItem("isotropic");
		anisotropic.setOnAction(
				new AnisotropicEventHandler(imageView, imageResultView));
		isotropic.setOnAction(new IsotropicEventHandler(imageView,imageResultView));
		diffusionMenu.getItems().addAll(anisotropic, isotropic);
		return diffusionMenu;
	}

}
