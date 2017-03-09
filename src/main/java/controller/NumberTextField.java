package controller;

import javafx.scene.control.TextField;

/**
 * Created by dylanbrisco on 3/5/17.
 */
public class NumberTextField extends TextField {

    @Override
    public void replaceText(int start, int end, String text) {
        if(validate(text)) {
            replaceSelection(text);
        }
    }

    @Override
    public void replaceSelection(String text) {
        if (validate(text)) {
            super.replaceSelection(text);
        }
    }

    /**
     * Uses regex to determine if text is a number
     * @param text the text in the textfield
     * @return boolean indicating if text is a number
     */
    private boolean validate(String text) {
        return text.matches("[1-9]*");
    }
}