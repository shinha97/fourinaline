import java.awt.*;
import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        //=========Set variables Area==========
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the amount of time (in sec) allowed for generating a move: ");

        int time = input.nextInt();
        System.out.print("Choose starting player (0 for Player, 1 for Opponent): ");
        int startPlayer = input.nextInt();
        //========End set variables Area==========

        runGame(startPlayer);




    }

    /**======================
     *    HELPER FUNCTIONS
     * ======================*/

    public static void runGame(int startPlayer){
        Scanner input = new Scanner(System.in);
        Board b = new Board();
        b.printBoard(startPlayer);


        while(!b.getTerminalState()){
            char c;
            if (startPlayer == 0){
                System.out.print("\nPlayer's move is: ");
                c = 'X';
            }
            else {
                System.out.print("\nOpponent's move is: ");
                c = 'O';
            }


            String move = input.next().toLowerCase();
            if (b.tryMove(move, c)){
                //Point validMove = new Point(move.charAt(0),move.charAt(1));
                b.printBoard(startPlayer);
                //TODO: Find response move, within the given time and plot on the board
                //System.out.println("Valid move!");
                if (startPlayer == 0){
                    startPlayer =1;
                    c = 'X';
                }
                else {
                    startPlayer = 0;
                    c = 'O';
                }
            }
            else{
                System.out.println("Invalid move has been entered. Re-enter a valid move");

            }
        }

    }


    public static boolean isValidMove(Board b,String move){
        if (((int)move.charAt(0) > 96) && ((int)move.charAt(0) < 105) && (move.charAt(1) >= 0) && (move.charAt(1) - '0' < 9)){//if the row/col is valid (input: a-h,1-8)
            if (b.getCell(((int)move.charAt(0)) -97,move.charAt(1) - '0' -1) == '_'){//the move is an empty tile
                //DO STUFF
                return true;
            }
            else{
                System.out.println("This is not an empty space. Try again.");
                return false;
            }
        }
        else{
            System.out.println("Error: Move out of bounds. Try again. " + (int)move.charAt(0) + " " + move.charAt(1));
            return false;
        }

    }
}
