/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wordguess;

import java.net.Socket;
import java.io.*;
import java.util.Scanner;

/**
 *
 * @author hubert.wolfram
 */
public class GameClient implements AppProtocol {

    Scanner fromServer;
    PrintWriter toServer;
    String host = "localhost";
    String guess;
    int gameCount = 0;
    int guessCount = 0;
    int guessLimit = 3;
    int player;
    String category;
    String chosenCat;
    boolean outOfGuesses = false;

    private void connectToServer() {

        try {
            // Create a socket to connect to the server
            Socket socket = new Socket(host, 3000);

            // Create a Scanner to receive data from the server
            fromServer = new Scanner(socket.getInputStream());

            // Create a PrintWriter to send data to the server
            toServer = new PrintWriter(socket.getOutputStream(), true);

        } catch (IOException ex) {
            System.out.println(ex);
        }

        GamePlay();
    }

    private void GamePlay() {

        Scanner keyboardInput = new Scanner(System.in);

        // Receive info from Server which player is it
        player = fromServer.nextInt();
        fromServer.nextLine(); // Skip to the next line
        
        if (player == PLAYER1) {

            System.out.println(ANSI_GREEN + "\nWelcome to the WordGuess game. You are Player " + player + ANSI_RESET
                    + "\n\nWaiting for Player 2 to join...");

            // Receive an notification from the server to start the game
            fromServer.nextInt();
            fromServer.nextLine(); // Skip to the next line

            // The other player has joined
            System.out.println("\nPlayer 2 has joined. You start first");

        } else if (player == PLAYER2) {
            System.out.println(ANSI_GREEN + "\nWelcome to the WordGuess game. You are Player " + player + ANSI_RESET);
        }

        while (gameCount < 4) {
            
            if (player == PLAYER1 && gameCount % 2 == 0 || player == PLAYER2 && gameCount % 2 != 0) {

                System.out.println("\n------------------------");
                System.out.println("\nThis is round " + (gameCount + 1) + " out of 4");

                System.out.println("\nChoose your category:\nEnter 1. for Electronics"
                        + "\nEnter 2. for Animals\nEnter 3. for Flowers\nEnter 4. for Fruits\n");

                chosenCat = keyboardInput.nextLine();

                switch (chosenCat) {
                    case "1":
                        category = "Electronics";
                        break;
                    case "2":
                        category = "Animals";
                        break;
                    case "3":
                        category = "Flowers";
                        break;
                    default:
                        category = "Fruits";
                        break;
                }
                System.out.println("\nThe category is: " + category);

                String randomWord = getWord(chosenCat);
                
                System.out.println("\nThis word has " + randomWord.length() + " letters.");
                
                // Console output for output check and game troubleshooting
                // System.out.println("The word is " + randomWord);
                
                System.out.print("\nEnter a guess: ");
                guess = keyboardInput.nextLine();
                toServer.println(guess);
                toServer.flush();
                
                String ifGuessed = fromServer.nextLine();

                if (ifGuessed.equals(CORRECT)) {
                    System.out.println(ANSI_GREEN + "\nCorrect, well done!" + ANSI_RESET);
                } else {
                    System.out.println(ANSI_RED + "\nWrong answer..." + ANSI_RESET);
                    System.out.println("\nThe answer was: " + ANSI_YELLOW + randomWord + ANSI_RESET);
                }
                gameCount++;

            } else if (player == PLAYER2 && gameCount % 2 == 0 || player == PLAYER1 && gameCount % 2 != 0) {

                System.out.println("\n------------------------");
                System.out.println("\nThis is round " + (gameCount + 1) + " out of 4");

                System.out.println("\nWaiting for the other player to choose category.");

                chosenCat = fromServer.nextLine();
                switch (chosenCat) {
                    case "1":
                        category = "Electronics";
                        break;
                    case "2":
                        category = "Animals";
                        break;
                    case "3":
                        category = "Flowers";
                        break;
                    default:
                        category = "Fruits";
                        break;
                }
                System.out.println("\nThe category is: " + category);

                String randomWord = fromServer.nextLine();

                System.out.println("\nThis word has " + randomWord.length() + " letters.");
                
                // Console output for output check and game troubleshooting
//                System.out.println("The word is " + randomWord);
                
                System.out.print("\nEnter a guess: ");
                guess = keyboardInput.nextLine();
                toServer.println(guess);
                toServer.flush();
                
                String ifGuessed = fromServer.nextLine();

                if (ifGuessed.equals(CORRECT)) {
                    System.out.println(ANSI_GREEN + "\nCorrect, well done!" + ANSI_RESET);
                } else {
                    System.out.println(ANSI_RED + "\nWrong answer..." + ANSI_RESET);
                    System.out.println("\nThe answer was: " + ANSI_YELLOW + randomWord + ANSI_RESET);
                }
                gameCount++;
            }
        }

        int player1count = fromServer.nextInt();
        fromServer.nextLine();
        int player2count = fromServer.nextInt();
        fromServer.nextLine();

        System.out.println("\n------------------------");
        
        if (player == PLAYER1) {
            System.out.println("\nYour points: " + player1count
                + "\nPlayer 2's points: " + player2count);

        } else {
            System.out.println("\nYour points: " + player2count
                + "\nPlayer 1's points: " + player1count);
        }
        
        int winner = fromServer.nextInt();
        fromServer.nextLine();

        if (winner == PLAYER1WINS && player == PLAYER1 || winner == PLAYER2WINS && player == PLAYER2) {
            System.out.println("\n" + ANSI_GREEN +"Congratulations, you won!\n" + ANSI_RESET);
        } else if (winner == PLAYER2WINS && player == PLAYER1 || winner == PLAYER1WINS && player == PLAYER2) {
            System.out.println("\n" + ANSI_RED + "The other player has won. Good luck next time!\n" + ANSI_RESET);
        } else if (winner == DRAW) {
            System.out.println("\n" + ANSI_YELLOW + "It's a draw!\n" + ANSI_RESET);
        }
        
        System.out.println(ANSI_RED + "Game finished.\n" + ANSI_RESET);

    }

    private String getWord(String chosenCat) {
        toServer.println(chosenCat);
        toServer.flush();
        String randomWord = fromServer.nextLine();
        return randomWord;
    }

    public static void main(String[] args) {
        GameClient clientTest = new GameClient();
        clientTest.connectToServer();
    }
}