package untref.controllers;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class PixelPaneController {

	private static final String EMPTY_VALUE = "";
	private final TextField yValue;
	private final VBox pane;
	private final TextField xValue;

	public PixelPaneController(String paneName) {
		Label title = new Label(paneName);
		Label x = new Label("x");
		xValue = new TextField(EMPTY_VALUE);
		xValue.setEditable(false);
		xValue.setMaxWidth(40);
		Label y = new Label("y");
		this.yValue = new TextField(EMPTY_VALUE);
		this.yValue.setEditable(false);
		this.yValue.setMaxWidth(40);
		this.pane = new VBox();
		this.pane.getChildren().addAll(title, x, xValue, y, this.yValue);
	}

	public VBox getPane() {
		return this.pane;
	}

	public boolean setedValues() {
		return !EMPTY_VALUE.equals(xValue.getText()) && !EMPTY_VALUE.equals(yValue.getText());
	}

	public void setValues(int x, int y) {
		xValue.setText(String.valueOf(x));
		yValue.setText(String.valueOf(y));
	}

	public void clearValues() {
		xValue.setText(EMPTY_VALUE);
		yValue.setText(EMPTY_VALUE);
	}
}