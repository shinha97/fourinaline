import java.util.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.Point;

public class Board{
    private char [][] board;
    private int movesLeft;
    private char turn;

    //testing
    // public static void main(String[]args){
    //   Board b = new Board();
    //   b.printBoard(1);
    //   System.out.println(b.evaluate('O'));
    // }

    public Board(){
        board = new char[8][8];
        movesLeft = 64;

        //populate empty board
         for(int i=0;i<board.length;i++){
             for(int j=0;j<board[i].length;j++){
                 board[i][j] = '_';
             }
         }

        //test generate random boards
        // Random r = new Random();
        // for(int i=0;i<8;i++){
        // 	for(int j=0;j<8;j++){
        // 		if(r.nextInt(3)==0) board[i][j]='X';
        // 		else if(r.nextInt(3)==1) board[i][j]='O';
        // 		else board[i][j]='_';
        // 	}
        // }

        //visual test board
        // board=new char[][]{ {'O','O','O','X','_','X','_','X'},
        //                     {'_','_','_','_','_','_','_','_'},
        // 					   {'_','_','_','_','_','_','_','_'},
        // 					   {'_','_','_','_','_','_','_','_'},
        // 					   {'_','_','_','_','_','_','_','_'},
        // 					   {'_','_','_','_','_','_','_','_'},
        // 					   {'_','_','_','_','_','_','_','_'},
        // 					   {'_','_','_','_','_','_','_','_'}};
    }
    public Board(Board b){
      board = new char[8][8];
      movesLeft = b.getMovesLeft();

      //populate empty board
       for(int i=0;i<board.length;i++){
           for(int j=0;j<board[i].length;j++){
               board[i][j] = b.getCell(i,j);
           }
       }
    }
    public void setTurn(char c){
      turn = c;
    }
    public char getTurn(){
      return turn;
    }
    // public Board(char[][] b){
    // public Board(Board copy){
    //     board = new char[8][8];
    //
    //     //populate empty board
    //     for(int i=0;i<board.length;i++){
    //           for(int j=0;j<board[i].length;j++){
    //               board[i][j] = copy[i][j];
    //           }
    //     }
    //     movesLeft = copy.getMovesLeft();
    // }
    // copy constructor
    public void copy(Board copy) {
        board = new char[8][8];

        //populate empty board
        for(int i=0;i<board.length;i++){
              for(int j=0;j<board[i].length;j++){
                  board[i][j] = copy.getCell(i,j);
              }
        }
        movesLeft = copy.getMovesLeft();
    }
    public int getMovesLeft(){
      return movesLeft;
    }
    public void decrementMoveCount(){
      movesLeft--;
    }
    public void incrementMoveCount(){
      movesLeft++;
    }
    public boolean isEmpty(){
      for(int i=0;i<board.length;i++){
        for(int j=0;j<board[i].length;j++){
          if(board[i][j]!='_')
            return false;
        }
      }
      return true;
    }
    public boolean getTerminalState(){
        if(checkFours('X') || checkFours('O') || movesLeft == 0)
            return true;
        else
            return false;
    }
    public int evaluate(char c){
        //TODO: Daniel please write a kick-ass eval function here
        //Given a board, call it's eval function to give a rating on its desirability
        char enemy;
        if(c=='X') enemy  = 'O';
        else enemy = 'X';

        boolean fours = checkFours(c);
        if(fours) return 10000000;
        boolean enemyFours = checkFours(enemy);
        if(enemyFours) return -10000000;
        int threes = checkThrees(c) * 10000;
        int twos = checkTwos(c) * 10;
        int enemyThrees = checkThrees(enemy) * 10000;
        int enemyTwos = checkTwos(enemy) * 10;

        int turnScore = 0;
        // if(turn!=c){
        //   turnScore = 1;
        // }

        return threes + twos + turnScore - enemyThrees - enemyTwos;
    }
    private int checkTwos(char c){
        int score = 0;
        //check row
        for(int col=0;col<5;col++){
            for(int row=0;row<board.length;row++){
                if( (board[row][col]==c && board[row][col]==board[row][col+1] &&
                     board[row][col+2]=='_' && board[row][col+3]=='_') ||
                    (board[row][col]==c && board[row][col]==board[row][col+2] &&
                     board[row][col+1]=='_' && board[row][col+3]=='_') ||
                    (board[row][col]==c && board[row][col]==board[row][col+3] &&
                     board[row][col+1]=='_' && board[row][col+2]=='_') ){
                    score++;
                }
                if(col==4){
                    if( (board[row][col+1]==c && board[row][col+1]==board[row][col+2] &&
                         board[row][col]=='_' && board[row][col+3]=='_') ||
                        (board[row][col+1]==c && board[row][col+1]==board[row][col+3] &&
                         board[row][col]=='_' && board[row][col+2]=='_') ||
                        (board[row][col+2]==c && board[row][col+2]==board[row][col+3] &&
                         board[row][col]=='_' && board[row][col+1]=='_') ){
                          score++;
                    }
                }
            }
        }
        //check columns
        for(int row=0;row<5;row++){
            for(int col=0;col<board.length;col++){
                if( (board[row][col]==c && board[row][col]==board[row+1][col] &&
                     board[row+2][col]=='_' && board[row+3][col]=='_') ||
                    (board[row][col]==c && board[row][col]==board[row+2][col] &&
                     board[row+1][col]=='_' && board[row+3][col]=='_') ||
                    (board[row][col]==c && board[row][col]==board[row+3][col] &&
                     board[row+1][col]=='_' && board[row+2][col]=='_') ){
                    score++;
                }
                if(row==4){
                    if( (board[row+1][col]==c && board[row+1][col]==board[row+2][col] &&
                         board[row][col]=='_' && board[row+3][col]=='_') ||
                        (board[row+1][col]==c && board[row+1][col]==board[row+3][col] &&
                         board[row][col]=='_' && board[row][col]=='_') ||
                        (board[row+2][col]==c && board[row+2][col]==board[row+3][col] &&
                         board[row][col]=='_' && board[row+1][col]=='_') ){
                          score++;
                    }
                }
            }
        }
        // System.out.println(score+" twos");
        return score;
    }
    private int checkThrees(char c){
        int score = 0;
        //check rows
        for(int col=0;col<5;col++){
            for(int row=0;row<board.length;row++){
                if( (board[row][col]==c && board[row][col]==board[row][col+1] &&
                     board[row][col+1]==board[row][col+2] && board[row][col+3]=='_') ||
                    (board[row][col]==c && board[row][col]==board[row][col+1] &&
                     board[row][col+1]==board[row][col+3] && board[row][col+2]=='_') ||
                    (board[row][col]==c && board[row][col]==board[row][col+2] &&
                     board[row][col+2]==board[row][col+3] && board[row][col+1]=='_') ){
                    score++;
                }
                if(col==4){
                    if(board[row][col+1]==c && board[row][col+1]==board[row][col+2] &&
                       board[row][col+2]==board[row][col+3] && board[row][col]=='_'){
                        score++;
                    }
                }
            }
        }
        //check columns
        for(int row=0;row<5;row++){
            for(int col=0;col<board.length;col++){
                if( (board[row][col]==c && board[row][col]==board[row+1][col] &&
                     board[row+1][col]==board[row+2][col] && board[row+3][col]=='_') ||
                    (board[row][col]==c && board[row][col]==board[row+1][col] &&
                     board[row+1][col]==board[row+3][col] && board[row+2][col]=='_') ||
                    (board[row][col]==c && board[row][col]==board[row+2][col] &&
                     board[row+2][col]==board[row+3][col] && board[row+1][col]=='_') ){
                    score++;
                }
                if(row==4){
                    if(board[row+1][col]==c && board[row+1][col]==board[row+2][col] &&
                       board[row+2][col]==board[row+3][col] && board[row][col]=='_'){
                        score++;
                    }
                }
            }
        }
        // System.out.println(score+" threes");
        return score;
    }
    private boolean checkFours(char c){
      //check for rows for victory, check 5 positions across 8 columns
        for(int col=0;col<5;col++){
            for(int row=0;row<board.length;row++){
                if(board[row][col]==board[row][col+1] &&
                   board[row][col+1]==board[row][col+2] &&
                   board[row][col+2]==board[row][col+3]){
                    if(board[row][col]==c){
                        // System.out.println("ROW: row "+(char)('@'+row+1)+" col "+(col+1));
                        return true;
                    }
                }
            }
        }
        //check columns for victory, check 5 positions across 8 rows
        for(int row=0;row<5;row++){
            for(int col=0;col<board.length;col++){
                if(board[row][col]==board[row+1][col] &&
                   board[row+1][col]==board[row+2][col] &&
                   board[row+2][col]==board[row+3][col]){
                    if(board[row][col]==c){
                        // System.out.println("COLUMN: row "+(char)('@'+row+1)+" col "+(col+1));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public char getCell(int x, int y) {
        return board[x][y];
    }

    public void updateBoard(Point newPosition, char value){
        board[newPosition.x][newPosition.y] = value;
        turn = value;
    }

    public boolean tryMove(String move, char c){
        if (((int)move.charAt(0) > 96) && ((int)move.charAt(0) < 105) && (move.charAt(1) >= 0) && (move.charAt(1) - '0' < 9)){//if the row/col is valid (input: a-h,1-8)
            if (getCell(((int)move.charAt(0)) -97,move.charAt(1) - '0' -1) == '_'){//the move is an empty tile
                //If valid move, update the board
                Point validMove = new Point(((int)move.charAt(0)-97),(move.charAt(1)-'0'-1));
                updateBoard(validMove, c);
                turn = c;
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
    public boolean tryMoveAI(Point p, char c){
        if ((p.x>=0 && p.x<8) && (p.y>=0 && p.y<8)){//if the row/col is valid
            if (getCell(p.x,p.y) == '_'){//the move is an empty tile
                //If valid move, update the board
                updateBoard(p, c);
                turn = c;
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
    public void print(){
      System.out.println("test");
      for(int row=0;row<9;row++){//rowumn
          for(int col=0;col<9;col++){//col
              if(row-1 >= 0 && col-1 >= 0)//if not (1st column and 1st row), print board cells
                  System.out.print(board[row-1][col-1]+"  ");
              else{//1st row or 1st col
                  if(row==0 && col==0){//cell[0][0]
                      System.out.print("   ");
                  }else if(row==0){//first row, prints numbers
                      System.out.print(col+"  ");
                  }else if(col==0){//first column prints letters
                      System.out.print((char)('@'+row)+"  ");
                  }else;
              }
          }System.out.println();
      }System.out.println();
    }
    public void printBoard(int startPlayer,List<String> moveRecord){
        for(int row=0;row<9;row++){//rowumn
            for(int col=0;col<9;col++){//col
                if(row-1 >= 0 && col-1 >= 0)//if not (1st column and 1st row), print board cells
                    System.out.print(board[row-1][col-1]+"  ");
                else{//1st row or 1st col
                    if(row==0 && col==0){//cell[0][0]
                        System.out.print("   ");
                    }else if(row==0){//first row, prints numbers
                        System.out.print(col+"  ");
                    }else if(col==0){//first column prints letters
                        System.out.print((char)('@'+row)+"  ");
                    }else;
                }
            }System.out.println();
        }System.out.println();

        //print record of moves
        if(startPlayer==0){
            System.out.println("Player vs Opponent");
        }else{
            System.out.println("Opponent vs Player");
        }
        for(int i=0;i<moveRecord.size();i++){
          //round of turns from both players
          if(i%2==0){
            //print new line after every 2 moves
            if(i!=0) System.out.println();
            //print round number
            System.out.print(((i/2)+1)+". ");
          }
          System.out.print(moveRecord.get(i)+" ");
        }System.out.println();
    }
}
