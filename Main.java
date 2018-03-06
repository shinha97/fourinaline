import java.awt.*;
import java.util.List;
import java.util.ArrayList;
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
        List<String> moveRecord = new ArrayList<String>();
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
                //record move
                moveRecord.add(move);
                b.printBoard(startPlayer,moveRecord);
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

        if (startPlayer == 1) System.out.println("Player Wins!");
        else System.out.println("Opponent Wins!");

    }
}
