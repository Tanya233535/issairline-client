package org.example.client.util;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Validator {

    public static List<String> collector() {
        return new ArrayList<>();
    }

    public static void require(TextField field, String label, List<String> errors) {
        if (field.getText() == null || field.getText().isBlank())
            errors.add(label + " — обязательное поле.");
    }

    public static void requireCombo(ComboBox<?> box, String label, List<String> errors) {
        if (box.getValue() == null)
            errors.add(label + " — выберите значение.");
    }

    public static void requireDate(DatePicker picker, String label, List<String> errors) {
        if (picker.getValue() == null)
            errors.add(label + " — выберите дату.");
    }

    public static void dateNotFuture(DatePicker picker, String label, List<String> errors) {
        if (picker.getValue() != null && picker.getValue().isAfter(LocalDate.now()))
            errors.add(label + " не может быть в будущем.");
    }

    public static void dateNotBefore(DatePicker picker, LocalDate min, String label, List<String> errors) {
        if (picker.getValue() != null && picker.getValue().isBefore(min))
            errors.add(label + " не может быть раньше " + min + ".");
    }

    public static void nonNegativeInt(TextField field, String label, List<String> errors) {
        try {
            int v = Integer.parseInt(field.getText());
            if (v < 0) errors.add(label + " не может быть отрицательным.");
        } catch (Exception e) {
            errors.add(label + " должен быть числом.");
        }
    }

    public static void nonNegativeDouble(TextField field, String label, List<String> errors) {
        if (field.getText().isBlank()) return;

        try {
            double v = Double.parseDouble(field.getText());
            if (v < 0) errors.add(label + " не может быть отрицательным.");
        } catch (Exception e) {
            errors.add(label + " должен быть числом.");
        }
    }
}
