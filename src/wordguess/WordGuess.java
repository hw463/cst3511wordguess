/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wordguess;

import java.util.Scanner;

/**
 *
 * @author hubert.wolfram
 */
public class WordGuess {

    static GameFunctions game;    
    
    public static void main(String[] args) {
        
        int gameCount = 0;
        
        while(gameCount < 4 ){
            
            try{
            
                game = new GameFunctions();
    
                Scanner keyboardInput = new Scanner(System.in);
        
                System.out.println("Welcome to the WordGuess game. This is round " + (gameCount+1) + " out of 4");

        
                System.out.println("\nChoose your category:\nEnter 1. for Electronics"
                        + "\nEnter 2. for Animals\nEnter 3. for Flowers\nEnter 4. for Fruits\n");
        
                String chosenWord = game.chooseWord(keyboardInput.nextLine());

                String guess = "";
                int guessCount = 0;
                int guessLimit = 3;
                boolean outOfGuesses = false;

                while(!game.checkWord(guess) && !outOfGuesses){
                     if(guessCount < guessLimit){
                         System.out.println("\nYou have " + (3-guessCount) + " guesses remaining.");
                         System.out.println("\nThis word has " + chosenWord.length() + " letters. The word is " + chosenWord);
                          System.out.print("\nEnter a guess: ");
                          guess = keyboardInput.nextLine();
                          guessCount++;

                     } else {
                          outOfGuesses = true;
                     }
                }

                if(outOfGuesses){
                     System.out.println("\nYou Lose! The word was: " + chosenWord);
                } else {
                     System.out.println("\nYou Won!\n");
                }

                gameCount++;

                }catch(Exception ex){
                    System.out.println(ex);
                }
	
        }
    }
    
}
