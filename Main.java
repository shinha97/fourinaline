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

        runGame(startPlayer,time);
    }

    /**======================
     *    HELPER FUNCTIONS
     * ======================*/

    public static void runGame(int startPlayer,int time){
        List<String> moveRecord = new ArrayList<String>();
        Scanner input = new Scanner(System.in);
        Board b = new Board();
        String move;
        long startTime = System.currentTimeMillis(),timeLimit = time*1000;
        b.printBoard(startPlayer, moveRecord);
        while(!b.getTerminalState()){
            char c;
            if (startPlayer == 0){
                System.out.print("\nPlayer's move is: ");
                c = 'X';

                iterative deepening minimax
                Board tempBoard;
                for(int maxDepth=0;maxDepth<=5 && (System.currentTimeMillis()-startTime)<timeLimit;maxDepth++){
                    startTime = System.nanoTime();

                    //call minimax
                    tempBoard = minimax(b,0,true,-999999999,999999999,maxDepth);

                    totalTime = System.currentTimeMillis()-startTime;
                }
                moveRecord.add(Character.toString((char)(tempBoard.getLastMove().x+97))+
                                              Integer.toString(tempBoard.getLastMove().y+1));
                b = tempBoard;

                // //test ai player with keyboard
                // do{
                //     System.out.print("\nPlayer's move is: ");
                //     c = 'O';
                //     move = input.next().toLowerCase();
                //
                //     if (b.tryMove(move, c)){
                //         //record move
                //         moveRecord.add(move);
                //         b.printBoard(startPlayer,moveRecord);
                //         Point validMove = new Point(((int)move.charAt(0)-97),(move.charAt(1)-'0'-1));
                //         break;
                //     }
                // }while(true);
            }else {
                do{
                    System.out.print("\nOpponent's move is: ");
                    c = 'O';
                    move = input.next().toLowerCase();

                    if (b.tryMove(move, c)){
                        //record move
                        moveRecord.add(move);
                        b.printBoard(startPlayer,moveRecord);
                        Point validMove = new Point(((int)move.charAt(0)-97),(move.charAt(1)-'0'-1));
                        break;
                    }
                }while(true);
            }

            //swap turns
            if (startPlayer == 0){
                startPlayer =1;
                c = 'X';
            }else {
                startPlayer = 0;
                c = 'O';
            }
        }

        if (startPlayer == 1) System.out.println("Player Wins!");
        else System.out.println("Opponent Wins!");

    }
}
