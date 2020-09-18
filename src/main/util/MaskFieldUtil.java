package main.util;

import javafx.application.Platform;
import javafx.scene.control.TextField;

public abstract class MaskFieldUtil {

    
    public static void cpfField(TextField textField) {
        MaskFieldUtil.maxField(textField, 14);
        textField.lengthProperty().addListener((observableValue, number, number2) -> {
            String value = textField.getText();
            value = value.replaceAll("[^0-9]", "");
            value = value.replaceFirst("(\\d{3})(\\d)", "$1.$2");
            value = value.replaceFirst("(\\d{3})(\\d)", "$1.$2");
            value = value.replaceFirst("(\\d{3})(\\d)", "$1-$2");
            try {
	            textField.setText(value);
	            MaskFieldUtil.positionCaret(textField);
            }catch(Exception ex){
            }
        });
     }

    private static void positionCaret(TextField textField) {
        Platform.runLater(() -> {
            if (textField.getText().length() != 0) {
                textField.positionCaret(textField.getText().length());
            }
        });
    }

    public static void maxField(TextField textField, Integer length) {
        textField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue == null || newValue.length() > length) {
                textField.setText(oldValue);
            }
        });
    }

    public static String onlyDigitsValue(TextField field) {
        String result = field.getText();
        if (result == null) {
            return null;
        }
        return result.replaceAll("[^0-9]", "");
    }

    public static String onlyAlfaNumericValue(TextField field) {
        String result = field.getText();
        if (result == null) {
            return null;
        }
        return result.replaceAll("[^0-9]", "");
    }


}
