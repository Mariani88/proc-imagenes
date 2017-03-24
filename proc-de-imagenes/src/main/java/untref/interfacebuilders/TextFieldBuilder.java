package untref.interfacebuilders;

import javafx.scene.control.TextField;

public class TextFieldBuilder {

	private final TextField textField;

	public TextFieldBuilder() {
		textField = new TextField();
	}

	public TextFieldBuilder withEditable(boolean editable) {
		textField.setEditable(editable);
		return this;
	}

	public TextFieldBuilder withAutosize() {
		textField.autosize();
		return this;
	}

	public TextField build(){
		return textField;
	}
}