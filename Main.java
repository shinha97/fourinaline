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
        int currentPlayer = 0;
        if(startPlayer==1) currentPlayer = 1;
        List<String> moveRecord = new ArrayList<String>();
        Scanner input = new Scanner(System.in);
        Board b = new Board();
        String move;
        long startTime = System.currentTimeMillis(),totalTime,timeLimit = time*1000;
        b.printBoard(startPlayer, moveRecord);
        while(!b.getTerminalState()){
            char c;
            if (currentPlayer == 0){
                System.out.print("\nPlayer's move is: ");
                c = 'X';
                Board tempBoard = new Board(), currentBoard = new Board();
                for(int maxDepth=1;maxDepth<=2 && (System.currentTimeMillis()-startTime)<timeLimit;maxDepth++){
                    startTime = System.nanoTime();
                    //call minimax
                    tempBoard = minimax(b,0,true,-999999999,999999999,maxDepth);
                    currentBoard.copy(tempBoard);
                    totalTime = System.currentTimeMillis()-startTime;
                }
                System.out.println(currentBoard.getLastMove().x+" "+currentBoard.getLastMove().y);
                moveRecord.add(Character.toString((char)(currentBoard.getLastMove().x+97))+
                                    Character.toString((char)(currentBoard.getLastMove().x+97)));
                b.copy(currentBoard);
                b.printBoard(startPlayer,moveRecord);
                //test ai player with keyboard
                // do{
                //     System.out.print("\nPlayer's move is: ");
                //     c = 'X';
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
            }else {//startplayer = 1
                do{
                    System.out.print("\nOpponent's move is: ");
                    c = 'O';
                    move = input.next().toLowerCase();

                    if (b.tryMove(move, c)){
                        //record move
                        moveRecord.add(move);
                        b.printBoard(startPlayer,moveRecord);
                        break;
                    }
                }while(true);
            }System.out.println();
            //swap turns
            if (currentPlayer == 0){
                currentPlayer = 1;
            }else {
                currentPlayer = 0;
            }
        }

        if (currentPlayer == 1) System.out.println("Player Wins!");
        else System.out.println("Opponent Wins!");

    }
	/**======================
     *    A L G O R I T H M
     * ======================*/

    private static Board minimax(Board state, int depth, boolean isMaxPlayer, int alpha, int beta, int maxDepth){
        Scanner kb = new Scanner(System.in);
        if ((depth == maxDepth) || (state.getTerminalState() )){
            return state;
        }

        Point childMove = new Point();
        if (isMaxPlayer){
            System.out.println("X");
            int bestValue = -999999999, value;
            Board returnBoard = new Board(), bestState = new Board();
            boolean getOut = false;
            //check children
            for (int i = 0; i < 8; i++){
                for (int j = 0; j < 8; j++){
                    if (state.getCell(i,j) == '_'){//if the potential child is an empty tile..
                        System.out.println(i+" "+j);
                        // System.out.println("X turn");
                        childMove = new Point(i,j);
                        Board childBoard = new Board();
                        childBoard.copy(state);                       //childBoard = parentBoard
                        childBoard.updateBoard(childMove,'X');    //             + childMove
                        childBoard.setLastMove(childMove);
                        bestState = minimax(childBoard, depth+1, false, alpha, beta, maxDepth);
                        value   = bestState.evaluate('X');

                        if (bestValue > value) returnBoard.copy(state);
                        else{ returnBoard.copy(bestState);}
                        bestValue = Math.max(bestValue, value);
                        alpha     = Math.max(alpha, bestValue);
                        if (beta <= alpha){
                            break;
                        }
                    }
                }
            }
            return returnBoard;
        }
        else{
            System.out.println("O");
            int bestValue = 999999999, value;
            Board returnBoard = new Board(), bestState = new Board();
            boolean getOut = false;
            //check children
            for (int i = 0; i < 8; i++){
                for (int j = 0; j < 8; j++){
                    if (state.getCell(i,j) == '_'){//if the potential child is an empty tile..
                        // System.out.println("O turn");
                        childMove = new Point(i,j);
                        Board childBoard = new Board();
                        childBoard.copy(state);                      //childBoard = parentBoard
                        childBoard.updateBoard(childMove,'O');    //             + childMove
                        childBoard.setLastMove(childMove);

                        bestState = minimax(childBoard, depth+1, true, alpha, beta, maxDepth);
                        value   = bestState.evaluate('O');

                        if (bestValue < value) returnBoard.copy(state);
                        else{ returnBoard.copy(bestState);}
                        bestValue = Math.min(bestValue, value);
                        beta      = Math.min(beta, bestValue);
                        if (beta <= alpha){
                            break;
                        }
                    }
                }
            }
            return returnBoard;
        }
    }
}
