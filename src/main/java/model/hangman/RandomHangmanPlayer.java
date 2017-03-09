package model.hangman;

import model.GamePlayer;
import model.GameState;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class RandomHangmanPlayer implements GamePlayer<HangmanMove> {

    @Override
    /**
     * Will randomly select a word from the MIT text file
     */
    public void makeMove(GameState<HangmanMove> state, MoveHandler<HangmanMove> handler) {
            try {
                ArrayList<String> words = new ArrayList<>();
               // BufferedReader reader = new BufferedReader(new FileReader("HangmanWords.txt"));
                Scanner scanner = new Scanner(new File("HangmanWords.txt"));
                while(scanner.hasNext()){
                    words.add(scanner.next()); // will add all the words to the array list
                }
                Collections.shuffle(words); // will shuffle the array list
                String randomWord = words.get(0); // will pick the first word from the random array list
            }

            catch (Exception e) {
                e.printStackTrace();
        }
    }

}
