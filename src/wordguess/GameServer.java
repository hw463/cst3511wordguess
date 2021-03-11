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
    
    String [] sArray;
    String word;
    String receivedGuess;
        
    public static void main(String[] args) throws IOException {
        
        int sessionNo = 1; // Number of a session
        Scanner fromPlayer1, fromPlayer2;
        PrintWriter toPlayer1, toPlayer2;
        GameServer gameServer = new GameServer();
        
        
                try {
                    // Create a server socket
                    ServerSocket serverSocket = new ServerSocket(3000);
                    System.out.println(new Date() + ": Server started at socket 3000\n");
                    
                    // Ready to create a session for every two players
                    while (true) {
                        System.out.println(new Date() + ": Waiting for players to join the game \n");
                        
                        // Connect to player 1
                        Socket player1 = serverSocket.accept();
                        
                        System.out.println(new Date() + ": Player 1 joined session the game\n");
                        System.out.println("Player 1's IP address "+ player1.getInetAddress().getHostAddress() + '\n');
                        
                        //Initialise communication with Player 1
                        toPlayer1 = new PrintWriter(player1.getOutputStream(), true);
                        fromPlayer1 = new Scanner(player1.getInputStream());
                        
                        // Notify that the player is Player 1
                        toPlayer1.println(PLAYER1);
                        
                        // Connect to player 2
                        Socket player2 = serverSocket.accept();
                        
                        System.out.println(new Date() + ": Player 2 joined the game\n");
                        System.out.println("Player 2's IP address" + player2.getInetAddress().getHostAddress() + '\n');
                        
                        //Initialise communication with Player 2
                        toPlayer2 = new PrintWriter(player2.getOutputStream(), true);
                        fromPlayer2 = new Scanner(player2.getInputStream());
                        
                        // Notify that the player is Player 2
                        toPlayer2.println(PLAYER2);
                        
//                        toPlayer1.println(1);
                        
                        String chosenCat = fromPlayer1.nextLine();
                        String player1Word = gameServer.chooseWord(chosenCat);
                        System.out.println("The word is: " + player1Word);
                        toPlayer1.println(player1Word);
                        toPlayer1.flush();
                        gameServer.receivedGuess = fromPlayer1.nextLine();
                        System.out.println(gameServer.receivedGuess);
                        toPlayer1.println(gameServer.checkWord(gameServer.receivedGuess));
                        toPlayer1.flush();
                    }
                } catch (IOException ex) {
                    System.out.println(ex);
                }
    }
    
    private boolean isWon(int player){
        
        return true;
    }
    
    public boolean checkWord(String toCheck){
        return (toCheck == null ? word == null : toCheck.equals(word));
    }
        
        public String chooseWord(String file){
            
            String sourceFile;
            this.sArray = new String[10];
            
            try {
                switch(file) {
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
            
                for(int i=0; i<sArray.length; i++) {
                    sArray[i]=inputFile.nextLine();	//populating array with random words from file.
                }
            
                Random random = new Random();		//creating random word object
                int randomNum =random.nextInt(sArray.length); //getting a random number based on length of array

                word = sArray[randomNum]; //getting a random word from array and storing in varaible word
            
            } catch(FileNotFoundException ex) {
                System.out.println(ex);
            }
            return word;
        }
    
    
}
