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
        b.printBoard(startPlayer, moveRecord);


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
                Point validMove = new Point(((int)move.charAt(0)-97),(move.charAt(1)-'0'-1));
                if (startPlayer == 0){
                    MinMaxNode AI       = new MinMaxNode(-9999,9999,validMove,true,b);
                    MinMaxNode nextMove = AI.minValue(b, 0, -9999,9999);
                    //MinMaxNode nextMove = MinMaxNode.minimax(0,true,-9999,9999,AI);
                    System.out.println("Opponent will move to pos: " + nextMove.getCurrPosition());

                }
                else{
                    MinMaxNode AI = new MinMaxNode(-9999,9999,validMove,false,b);
                }


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

    /**======================
     *    A L G O R I T H M
     * ======================*/

    public static Board minimax(Board state, int depth, boolean isMaxPlayer, int alpha, int beta, int maxDepth){
        if ((depth == maxDepth) || (state.getTerminalState() )){
            return state;
        }


        Point childMove = new Point();
        if (isMaxPlayer){
            int bestValue = -999999999, value;
            Board returnBoard = new Board(), bestState;
            //check children
            for (int i = 0; i < 8; i++){
                for (int j = 0; j < 8; j++){
                    if (state.getCell(i,j) == '_'){//if the potential child is an empty tile..

                        childMove = new Point(i,j);
                        Board childBoard = state;                       //childBoard = parentBoard
                        childBoard.updateBoard(childMove,'X');    //             + childMove


                        bestState = minimax(childBoard, depth+1, false, alpha, beta, maxDepth);
                        value   = bestState.evaluate('X');

                        if (bestValue > value) returnBoard = state;
                        else{ returnBoard = bestState;}
                        bestValue = Math.max(bestValue, value);
                        alpha     = Math.max(alpha, bestValue);
                        if (beta <= alpha) break;
                    }
                }
            }
            return returnBoard;
        }
        else{
            int bestValue = 999999999, value;
            Board returnBoard = new Board(), bestState;
            //check children
            for (int i = 0; i < 8; i++){
                for (int j = 0; j < 8; j++){
                    if (state.getCell(i,j) == '_'){//if the potential child is an empty tile..

                        childMove = new Point(i,j);
                        Board childBoard = state;                       //childBoard = parentBoard
                        childBoard.updateBoard(childMove,'O');    //             + childMove


                        bestState = minimax(childBoard, depth+1, true, alpha, beta, maxDepth);
                        value   = bestState.evaluate('O');

                        if (bestValue < value) returnBoard = state;
                        else{ returnBoard = bestState;}
                        bestValue = Math.min(bestValue, value);
                        beta      = Math.max(beta, bestValue);
                        if (beta <= alpha) break;
                    }
                }
            }
            return returnBoard;
        }
    }
}
