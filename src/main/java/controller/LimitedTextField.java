package controller;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TextField;

/**
 * TextField that supports limiting the length of input
 */
public class LimitedTextField extends TextField {

    @Override
    public void replaceText(int start, int end, String text) {
        if (start > end) {
            throw new IllegalArgumentException();
        }
        if ((start < 0) || (end> getLength())) {
            throw new IllegalArgumentException();
        }

        if (getMaxChars() == -1) {
            super.replaceText(start, end, text);
        }

        int oldLength = getLength();
        int lengthDifference = text.length() - (end - start);

        if (oldLength + lengthDifference > getMaxChars()) {
            //Will not fit in the allotted space
            //maxChars = (old text length) + (modified text length) - (end - start)
           int modifiedTextLength = getMaxChars() - oldLength + (end - start);
            String modifiedText = text.substring(0, modifiedTextLength);
            super.replaceText(start, end, modifiedText);
        } else {
            //Will fit in the allotted space
            super.replaceText(start, end, text);
        }
    }

    /**
     * Maximum number of characters allowed in this TextField
     */
    private IntegerProperty maxChars = new SimpleIntegerProperty(-1);

    /**
     * Returns the maxChars property
     *
     * @return the maxChars property
     */
    public IntegerProperty getMaxCharsProperty() {
        return maxChars;
    }

    /**
     * Returns the maximum number of character allowed in this TextField
     *
     * @return the maximum number of characters allowed in this TextField
     */
    public int getMaxChars() {
        return maxChars.get();
    }

    }
