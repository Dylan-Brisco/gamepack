package controller;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TextField;

import java.util.HashSet;
import java.util.Set;

/**
 * TextField of length one that only allows certain character to be typed
 */
public class LimitedCharacterField extends TextField {

    @Override
    public void replaceText(int start, int end, String text) {
        if (start > end) {
            throw new IllegalArgumentException();
        }
        if ((start < 0) || (end > getLength())) {
            throw new IllegalArgumentException();
        }

        text = text.toUpperCase();
        if (text.length() == 0) {
            super.replaceText(start, end, text);
            return;
        }

        if (!getCharactersPropertyValue().contains(text.charAt(0))) {
            return;
        }

        String firstChar = text.substring(0, 1);

        if (getLength() == 0) {
            super.replaceText(start, end, firstChar);
        } else {
            int newLength = end - start + 1 + getLength();
            if (newLength <= 1) {
                super.replaceText(start, end, firstChar);
            }
        }
    }

    /**
     * Returns the character in this TextField, or null if there is none
     *
     * @return The character in this TextField
     */
    public Character getCharacter() {
        if (getLength() == 0) {
            return null;
        }
        return getText().charAt(0);
    }

    /**
     * Characters allowed in this TextField
     */
    private Property<Set<Character>> characters = new SimpleObjectProperty<>(new HashSet<>());

    /**
     * Returns the characters property
     *
     * @return the characters property
     */
    public Property<Set<Character>> getCharactersProperty() {
        return characters;
    }

    /**
     * Returns the characters allowed in this TextField
     *
     * @return the characters allowed in this TextField
     */
    public Set<Character> getCharactersPropertyValue() {
        return characters.getValue();
    }

    /**
     * Sets the characters property's value
     *
     * @param characters Characters allowed in this TextField
     */
    public void setCharacters(Set<Character> characters) {
        if (characters != null) {
            this.characters.setValue(characters);
        }
    }
}
