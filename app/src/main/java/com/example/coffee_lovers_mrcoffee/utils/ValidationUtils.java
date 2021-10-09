package com.example.coffee_lovers_mrcoffee.utils;

import android.widget.EditText;

import java.util.Map;

public class ValidationUtils {

    /**
     * If text of given textbox is empty, the required message is displayed and returns false
     */
    public boolean isEmpty(EditText textBox, String errorMsg) {
        if (textBox.getText().length() == 0) {
            textBox.setError(errorMsg);
            return true;
        } else {
            return false;
        }
    }


    /**
     * Do is empty for all given textboxes
     */
    public boolean isAnyEmpty(Map<EditText, String> textBoxes) {
        for (EditText textBox : textBoxes.keySet()) {
            if(isEmpty(textBox, textBoxes.get(textBox))) {
                return true;
            }
        }

        return false;
    }

}
