package model.hangman;

import model.Move;


public class HangmanMove implements Move {
    private char character; // the character the player chooses

    public HangmanMove(char character) {
        this.character = Character.toUpperCase(character); // makes all letters uppercase to prevent confusion
    }

    /**
     * Returns a character
     * @return character
     */
    public char getCharacter() {
        return character;
    }

    /**
     * @param o an object (preferably a character)
     * @return false if o isn't a character or if o is null
     * @return true if HangmanMove.getCharacter is the character
     */
    @Override
    public boolean equals(Object o) {
       if (!(o instanceof HangmanMove)) {
           return false;
       }
        return Character.compare(Character.toUpperCase(((HangmanMove)o).character),
                Character.toUpperCase(character)) == 0;
    }

    @Override
    public String toString() {
        return character + "";
    }
}
