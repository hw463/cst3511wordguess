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
public class ClientTest implements AppProtocol{
    
    private void connectToServer(){
        
    Scanner fromServer;
    PrintWriter toServer;
    String host = "localhost";
    String guess;
    
    while(true) {
        try {
			// Create a socket to connect to the server
			Socket socket = new Socket(host, 3000);
			
			// Create a Scanner to receive data from the server
			fromServer = new Scanner(socket.getInputStream());
			
			// Create a PrintWriter to send data to the server
			toServer = new PrintWriter(socket.getOutputStream(), true);

            // Receive info from Server which player is it
            int player = fromServer.nextInt();
            fromServer.nextLine(); // Skip to the next line
            
            if (player == PLAYER1){
                Scanner keyboardInput = new Scanner(System.in);
                
               
                System.out.println("Welcome to the WordGuess game. You are player " + player );
        
                System.out.println("\nChoose your category:\nEnter 1. for Electronics"
                                    + "\nEnter 2. for Animals\nEnter 3. for Flowers\nEnter 4. for Fruits\n");
                
                String chosenCat = keyboardInput.nextLine();
                toServer.println(chosenCat);
                toServer.flush();
                String randomWord = fromServer.nextLine();
                
                System.out.println("\nThis word has " + randomWord.length() + " letters. The word is " + randomWord);
                System.out.print("\nEnter a guess: ");
                guess = keyboardInput.nextLine();
                toServer.println(guess);
                toServer.flush();
                boolean ifGuessed = fromServer.nextBoolean();
                
                if (ifGuessed == true) {
                    System.out.println("\nCorrect, well done!");
                } else {
                    System.out.println("\nSorry, not correct...");
                }
                
                
            } else if (player == PLAYER2){
                while(true){
                System.out.println("Wait for Player 1 to make a guess");
                String test = fromServer.nextLine();
                }
            }

		} catch (IOException ex) {
			System.out.println(ex);
		}
    }
    }
    
    
    public static void main(String[] args){
        ClientTest clientTest = new ClientTest();
        clientTest.connectToServer();
    }
}
