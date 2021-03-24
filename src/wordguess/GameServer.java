/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wordguess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author hubert.wolfram
 */
public class GameServer implements AppProtocol {

    String[] guessArray;
    String guessWord;
    String category;
    String receivedGuess;
    String chosenWord;
    int player1count;
    int player2count;

    public static void main(String[] args) throws IOException {

        Scanner fromPlayer1, fromPlayer2;
        PrintWriter toPlayer1, toPlayer2;
        GameServer gameServer = new GameServer();
        int gameCount = 0;

        try {
            // Create a server socket
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("\n" + new Date() + ": Server started at socket 3000\n");

            // Ready to create a session for two players
//            while (true) {
            System.out.println(new Date() + ": Waiting for players to join the game \n");

            // Connect to player 1
            Socket player1 = serverSocket.accept();

            System.out.println(new Date() + ": Player 1 joined session the game");
            System.out.println("Player 1's IP address " + player1.getInetAddress().getHostAddress() + '\n');

            //Initialise communication with Player 1
            toPlayer1 = new PrintWriter(player1.getOutputStream(), true);
            fromPlayer1 = new Scanner(player1.getInputStream());

            // Notify that the player is Player 1
            toPlayer1.println(PLAYER1);

            // Connect to player 2
            Socket player2 = serverSocket.accept();

            System.out.println(new Date() + ": Player 2 joined the game");
            System.out.println("Player 2's IP address" + player2.getInetAddress().getHostAddress() + '\n');

            //Initialise communication with Player 2
            toPlayer2 = new PrintWriter(player2.getOutputStream(), true);
            fromPlayer2 = new Scanner(player2.getInputStream());

            // Notify that the player is Player 2
            toPlayer2.println(PLAYER2);

            // Let Player 1 know to start the game
            toPlayer1.println(1);

            // Gameplay logic
            while (gameCount < 4) {

                //If gameCount is even the Player 1 chooses category
                if (gameCount % 2 == 0) {
                    
                    System.out.println("------------------------"
                            + "\nRound " + (gameCount+1) + " out of 4");

                    // Player 1 selects the category
                    String chosenCat = fromPlayer1.nextLine();
                    toPlayer2.println(chosenCat);
                    gameServer.chosenWord = gameServer.chooseWord(chosenCat);
                    System.out.println("\nThe word to be guessed is: " + gameServer.chosenWord);
                    
                    // Sending the chosen word to both clients
                    toPlayer1.println(gameServer.chosenWord);
                    toPlayer2.println(gameServer.chosenWord);
                    toPlayer1.flush();
                    toPlayer2.flush();
                    
                    gameServer.receivedGuess = fromPlayer1.nextLine();
                    System.out.println("\nPlayer 1's guess: " + gameServer.receivedGuess);
                    
                    if (gameServer.checkWord(gameServer.receivedGuess) == true) {
                        gameServer.player1count++;
                        toPlayer1.println(CORRECT);
                    } else {
                        toPlayer1.println(WRONG);
                    }
                    toPlayer1.flush();
                    gameServer.receivedGuess = fromPlayer2.nextLine();
                    System.out.println("Player 2's guess: " + gameServer.receivedGuess);
                    if (gameServer.checkWord(gameServer.receivedGuess) == true) {
                        gameServer.player2count++;
                        toPlayer2.println(CORRECT);
                    } else {
                        toPlayer2.println(WRONG);
                    }
                    toPlayer2.flush();
                    System.out.println("\nPlayer 1 points count: " + gameServer.player1count);
                    System.out.println("Player 2 points count: " + gameServer.player2count);
                    gameCount++;
                
                // If gameCount is odd the Player 2 chooses category
                } else {

                    System.out.println("------------------------"
                            + "\nRound " + (gameCount+1) + " out of 4");
                    
                    String chosenCat = fromPlayer2.nextLine();
                    toPlayer1.println(chosenCat);
                    gameServer.chosenWord = gameServer.chooseWord(chosenCat);
                    System.out.println("\nThe word to be guessed is: " + gameServer.chosenWord);
                    
                    // Sending the chosen word to both clients
                    toPlayer2.println(gameServer.chosenWord);
                    toPlayer1.println(gameServer.chosenWord);
                    toPlayer2.flush();
                    toPlayer1.flush();
                    gameServer.receivedGuess = fromPlayer2.nextLine();
                    System.out.println("Player 2's guess: " + gameServer.receivedGuess);
                    if (gameServer.checkWord(gameServer.receivedGuess) == true) {
                        gameServer.player2count++;
                        toPlayer2.println(CORRECT);
                    } else {
                        toPlayer2.println(WRONG);
                    }
                    toPlayer2.flush();
                    gameServer.receivedGuess = fromPlayer1.nextLine();
                    System.out.println("Player 1's guess: " + gameServer.receivedGuess);
                    if (gameServer.checkWord(gameServer.receivedGuess) == true) {
                        gameServer.player1count++;
                        toPlayer1.println(CORRECT);
                    } else {
                        toPlayer1.println(WRONG);
                    }
                    toPlayer1.flush();
                    System.out.println("\nPlayer 1 points count: " + gameServer.player1count);
                    System.out.println("Player 2 points count: " + gameServer.player2count);
                    gameCount++;
                }
            }
            
            // Sending both player's both results
            toPlayer1.println(gameServer.player1count);
            toPlayer1.println(gameServer.player2count);
            toPlayer2.println(gameServer.player1count);
            toPlayer2.println(gameServer.player2count);
            
            System.out.println("------------------------");

            // Determining which player won and communinicating it to both players
            if (gameServer.player1count > gameServer.player2count) {
                toPlayer1.println(PLAYER1WINS);
                toPlayer2.println(PLAYER1WINS);
                System.out.println("Player 1 wins. Game finished.\n");
            } else if (gameServer.player1count < gameServer.player2count) {
                toPlayer1.println(PLAYER2WINS);
                toPlayer2.println(PLAYER2WINS);
                System.out.println("Player 2 wins. Game finished.\n");
            } else {
                toPlayer1.println(DRAW);
                toPlayer2.println(DRAW);
                System.out.println("It's a draw. Game finished.\n");
            }

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    // Method for checking whether the guess matches the chosen Word
    public boolean checkWord(String toCheck) {
        return (toCheck == null ? guessWord == null : toCheck.equals(guessWord));
    }

    // Method to select a random guessWord to guess from a text file
    public String chooseWord(String file) {

        String sourceFile;
        this.guessArray = new String[10];

        try {
            switch (file) {
                case "1":
                    sourceFile = "Electronics.txt";
                    break;
                case "2":
                    sourceFile = "Animals.txt";
                    break;
                case "3":
                    sourceFile = "Flowers.txt";
                    break;
                default:
                    sourceFile = "Fruits.txt";
                    break;
            }

            // Creating a new File object and a Scanner for file input
            File guessFile = new File(sourceFile);
            Scanner inputFile = new Scanner(guessFile);

            // Populating an array with words from the chosen file
            for (int i = 0; i < guessArray.length; i++) {
                guessArray[i] = inputFile.nextLine();
            }

            // Selecting a random number based on array's length
            Random random = new Random();
            int randomNum = random.nextInt(guessArray.length);

            // Selecting a random guessWord to be guessed from the array
            guessWord = guessArray[randomNum];

        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
        return guessWord;
    }

}