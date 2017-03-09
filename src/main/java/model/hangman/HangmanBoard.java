package model.hangman;

import model.GameState;

import java.util.*;
import java.util.stream.Collectors;


public class HangmanBoard extends GameState<HangmanMove> {
    private Set<Character> incorrectPickedCharacters;
    private Set<Character> correctPickedCharacters;
    private Set<Character> pickedCharacters;
    private String word;
    private int minMoves;
    private Set<Character> remainingCharacters;
    private Set<Character> allCharacters;

    public HangmanBoard(String word) {
        incorrectPickedCharacters = new HashSet<>(); // the user's incorrect guesses
        correctPickedCharacters = new HashSet<>(); // the user's correct guesses
        pickedCharacters = new HashSet<>();
        this.word = word;

        allCharacters = new HashSet<>();
        for (char c : word.toUpperCase().toCharArray()) {
            allCharacters.add(c); // sets only allow unique elements to be added
        }
        minMoves = allCharacters.size(); // number of unique characters

        remainingCharacters = new HashSet<>();
        for (int i = 0; i < 26; i++) {
            remainingCharacters.add((char) ('A' + i)); // characters user hasn't yet chosen
        }
    }

    @Override
    public boolean gameOver() {
        return (incorrectPickedCharacters.size() >= 6 || correctPickedCharacters.size() == minMoves);
        // too many incorrect guesses or they get the word
    }

    @Override
    public Map<Integer, Integer> places() {
        Map<Integer, Integer> m = new HashMap<>();
        if (incorrectPickedCharacters.size() >= 6) {
            m.put(1, 2); // 1 is guesser (loses)
            m.put(0, 1); // 0 is computer/person making word (wins)
        } else if (correctPickedCharacters.size() == minMoves) {
            m.put(1, 1); // word maker loses
            m.put(0, 2); // guesser wins
        }
        return m;
    }

    @Override
    public void makeMove(HangmanMove move) throws IllegalArgumentException {
        Character c = move.getCharacter();

        if (pickedCharacters.contains(c)) { // if the user repeats a guessed letter
            throw new IllegalArgumentException("Character previously guessed");
        }


        pickedCharacters.add(c);
        remainingCharacters.remove(c);

        if (allCharacters.contains(c)) {
            correctPickedCharacters.add(c); // if the user guessed a letter correctly
        } else {
            incorrectPickedCharacters.add(c); // if the user incorrectly guessed a letter
        }
    }

    @Override
    public Set<HangmanMove> getValidMoves() {
        return remainingCharacters.stream().map(HangmanMove::new).collect(Collectors.toSet());
    }

    /**
     * Returns whether the given move is a correct move
     *
     * @param m Move to check
     * @return True if the given move is correct
     */
    public boolean isCorrectMove(HangmanMove m) {
        return allCharacters.contains(m.getCharacter());
    }

    /**
     * Returns a Set of all indexes in which the
     * given move appears in this board's word
     *
     * @param m Move to check
     * @return A Set of all indexes in which the
     * given move appears in this board's word
     */
    public Set<Integer> getAllPositions(HangmanMove m) {
        Set<Integer> s = new HashSet<>();
        for (int i = 0; i < word.length(); i++) {
            if (Character.compare(word.charAt(i), m.getCharacter()) == 0) {
                s.add(i);
            }
        }
        return s;
    }

    /**
     * Returns the number of incorrect moves that have been made so far
     *
     * @return he number of incorrect moves that have been made so far
     */
    public int getNumMistakes() {
        return incorrectPickedCharacters.size();
    }
}