import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

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
        Point p = new Point();
        while(!b.getTerminalState()){
            char c;
            if (currentPlayer == 0){
                System.out.println("\nPlayer's move");
                c = 'X';

                //if empty board, choose random cell in 4x4 box in middle
                if(b.isEmpty() || moveRecord.size()<2){
                    Random r = new Random();
                    p = new Point(r.nextInt(4)+2,r.nextInt(4)+2);
                }else{
                    Point tempP = new Point(p.x,p.y);
                    for(int maxDepth=1;maxDepth<=5 && (System.currentTimeMillis()-startTime)<timeLimit;maxDepth++){
                        startTime = System.nanoTime();
                        //call minimax

                        tempP = minimax(tempP,b,0,true,-999999999,999999999,maxDepth,System.currentTimeMillis()-startTime);
                        p.x = tempP.x;
                        p.y = tempP.y;
                        totalTime = System.currentTimeMillis()-startTime;
                    }
                    //p = minimax(p,b,0,true,-999999999,999999999,5);
                }

                b.updateBoard(p,'X');
                moveRecord.add(Character.toString((char)(p.x+97))+Integer.toString((char)(p.y+1)));
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

    private static Point minimax(Point currentMove,Board currentState, int depth, boolean isMaxPlayer, int alpha, int beta, int maxDepth,long time){
        // currentState.print();
        // System.out.println(alpha+" "+beta);
        // Scanner kb = new Scanner(System.in);
        //if maxdepth reached or board is full or in a win currentState
        Random r = new Random();
        if ((depth == maxDepth) || (currentState.getTerminalState() )){
            return currentMove;
        }

        Point childMove = new Point();
        if (isMaxPlayer){
            int bestValue = -999999999, value;
            Board returnBoard = new Board(), bestState = new Board(), childBoard = new Board(currentState);
            //initialize to random move near last move if cant find any better move
            Board tempBoard;
            Point nextMove;
            int newx,newy;
                tempBoard = new Board(currentState);
                do{
                    if(r.nextInt(2)==0){//add or subtract x
                        newx = r.nextInt(3);
                    }else{
                      newx = -r.nextInt(3);
                    }
                    if(r.nextInt(2)==0){//add or subtract y
                      newy = r.nextInt(3);
                    }else{
                      newy = -r.nextInt(3);
                    }
                    nextMove = new Point(currentMove.x+newx,currentMove.y+newy);
                }while(!tempBoard.tryMoveAI(nextMove,'X'));
            //check children
            for (int i = 0; i < 8; i++){
                for (int j = 0; j < 8; j++){
                    if (currentState.getCell(i,j) == '_'){//create childnode
                        childMove = new Point(i,j);
                        childBoard = new Board(currentState);                     //childBoard = parentBoard
                        childBoard.updateBoard(childMove,'X');    //             + childMove
                        if(childBoard.evaluate('X') > currentState.evaluate('X')){
                            long newTime = System.currentTimeMillis()-time;
                            nextMove = minimax(childMove,childBoard, depth+1, false, alpha, beta, maxDepth,newTime);
                            childBoard.updateBoard(nextMove,'O');
                            childBoard.decrementMoveCount();
                            bestValue = Math.max(bestValue,childBoard.evaluate('X'));
                            alpha     = Math.max(alpha, bestValue);

                            if (beta <= alpha || newTime < 0){
                                childBoard.incrementMoveCount();
                                return nextMove;
                            }
                        }
                    }
                }
            }
            childBoard.decrementMoveCount();
            return nextMove;
        }
        else{
          int bestValue = 999999999, value;
          Board returnBoard = new Board(), bestState = new Board(), childBoard = new Board(currentState);;
          //initialize to random move near last move if cant find any better move
          Board tempBoard;
          Point nextMove;
          int newx,newy;
            tempBoard = new Board(currentState);
            do{
              if(r.nextInt(2)==0){//add or subtract x
                  newx = r.nextInt(3);
              }else{
                newx = -r.nextInt(3);
              }
              if(r.nextInt(2)==0){//add or subtract y
                newy = r.nextInt(3);
              }else{
                newy = -r.nextInt(3);
              }
              nextMove = new Point(currentMove.x+newx,currentMove.y+newy);
            }while(!tempBoard.tryMoveAI(nextMove,'O'));
          //check children
          for (int i = 0; i < 8; i++){
              for (int j = 0; j < 8; j++){
                  if (currentState.getCell(i,j) == '_'){//create childnode
                      childMove = new Point(i,j);
                      childBoard = new Board(currentState);                     //childBoard = parentBoard
                      childBoard.updateBoard(childMove,'O');    //             + childMove
                      if(childBoard.evaluate('O') > currentState.evaluate('O')){
                          long newTime = System.currentTimeMillis()-time;
                          nextMove = minimax(childMove,childBoard, depth+1, true, alpha, beta, maxDepth,newTime);
                          childBoard.updateBoard(nextMove,'X');
                          childBoard.decrementMoveCount();
                          bestValue = Math.min(bestValue,childBoard.evaluate('O'));
                          beta     = Math.min(beta, bestValue);

                          if (beta <= alpha){
                            childBoard.incrementMoveCount();
                              return nextMove;
                          }
                      }
                  }
              }
          }
          childBoard.decrementMoveCount();
          return nextMove;
        }
    }
}
