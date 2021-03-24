/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wordguess;

/**
 *
 * @author hubert.wolfram
 */
public interface AppProtocol {

    public static int PLAYER1 = 1;
    public static int PLAYER2 = 2;
    public static int PLAYER1WINS = 1;
    public static int PLAYER2WINS = 2;
    public static int DRAW = 3;
    public static int PORT = 3000;
    public static String CORRECT = "Y";
    public static String WRONG = "N";
    public static String HOST = "localhost";
    public static String ANSI_RESET = "\u001B[0m";
    public static String ANSI_RED = "\u001B[31m";
    public static String ANSI_GREEN = "\u001B[32m";
    public static String ANSI_YELLOW = "\u001B[33m";
    
}
