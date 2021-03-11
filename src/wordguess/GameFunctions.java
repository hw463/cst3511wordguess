/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wordguess;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author hubert.wolfram
 */
public final class GameFunctions {
    
    String [] sArray;
    String word;
    
    public GameFunctions() throws IOException {
        
    }
    
    public boolean checkWord(String toCheck){
        return (toCheck == null ? word == null : toCheck.equals(word));
}
    
    public void playGame(String chosenWord){
   
        Scanner keyboardInput = new Scanner(System.in);
        
//        String guess = "";
//        int guessCount = 0;
//        int guessLimit = 3;
//        boolean outOfGuesses = false;
//        
//        while(!guess.equals(chosenWord) && !outOfGuesses){
//             if(guessCount < guessLimit){
//                 System.out.println("\nYou have " + (3-guessCount) + " guesses remaining.");
//                 System.out.println("\nThis word has " + chosenWord.length() + " letters. The word is " + chosenWord);
//                  System.out.print("\nEnter a guess: ");
//                  guess = keyboardInput.nextLine();
//                  guessCount++;
//                    
//             } else {
//                  outOfGuesses = true;
//             }
//        }
//
//        if(outOfGuesses){
//             System.out.println("\nYou Lose! The word was: " + chosenWord);
//        } else {
//             System.out.println("\nYou Won!\n");
//        }
	 
    }
    
        public String chooseWord(String file){
            String sourceFile;
            this.sArray = new String[10];
            
            try{
            switch(file)
		{

		case "1": sourceFile = "Electronics.txt";
			break;
		case "2": sourceFile = "Animals.txt";
			break;
                case "3": sourceFile = "Flowers.txt";
                        break;
		default: sourceFile = "Fruits.txt";
			break;
		}
            
            File nFile = new File(sourceFile);				//file object
            Scanner inputFile = new Scanner(nFile);
            
            
            for(int i=0; i<sArray.length; i++)
		{
			sArray[i]=inputFile.nextLine();	//populating array with random words from file.
		}
            
                Random random = new Random();		//creating random word object
		int randomNum =random.nextInt(sArray.length); //getting a random number based on length of array

		word = sArray[randomNum]; //getting a random word from array and storing in varaible word
            
            }catch(Exception ex){
                System.out.println(ex);
            }
            return word;
        }
    
}
