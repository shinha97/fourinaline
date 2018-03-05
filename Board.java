import java.util.*;
import java.awt.Point;

public class Board{
    private static char [][] board;

    //testing
    // public static void main(String[]args){
    //   Board b = new Board();
    //   b.printBoard(1);
    //   System.out.println(b.evaluate('O'));
    // }

    public Board(){
        board = new char[8][8];

        //populate empty board
        // for(int i=0;i<board.length;i++){
        //     for(int j=0;j<board[i].length;j++){
        //         board[i][j] = '_';
        //     }
        // }

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
        // 					          {'_','_','_','_','_','_','_','_'},
        // 					          {'_','_','_','_','_','_','_','_'},
        // 					          {'_','_','_','_','_','_','_','_'},
        // 					          {'_','_','_','_','_','_','_','_'},
        // 					          {'_','_','_','_','_','_','_','_'},
        // 					          {'_','_','_','_','_','_','_','_'}};
    }
    public Board(char[][] b){
        board = new char[8][8];

        //populate empty board
        for(int i=0;i<board.length;i++){
              for(int j=0;j<board[i].length;j++){
                  board[i][j] = b[i][j];
              }
        }
    }
    public char[][] getBoard(){
        return board;
    }
    public int evaluate(char c){
        //TODO: Daniel please write a kick-ass eval function here
        //Given a board, call it's eval function to give a rating on its desirability

        boolean fours = checkFours(c);
        if(fours) return 10000000;
        boolean enemyFours = checkFours(enemy);
        if(enemyFours) return -10000000;
        int threes = checkThrees(c) * 10000;
        int twos = checkTwos(c) * 10;
        int enemyThrees = checkThrees(enemy) * 10000;
        int enemyTwos = checkTwos(enemy) * 10;

        return threes + twos - enemyThrees - enemyTwos;
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

    public static int getCell(int x, int y) {
        return board[x][y];
    }

    public char[][] updateBoard(Point newPosition, char value){
        board[newPosition.x][newPosition.y] = value;
        return board;
    }

    public void printBoard(int startPlayer){
        for(int col=0;col<9;col++){//column
            for(int row=0;row<9;row++){//row
                if(col-1 >= 0 && row-1 >= 0)
                    System.out.print(board[col-1][row-1]+"  ");
                else{
                    if(col==0 && row==0) System.out.print("   ");
                    else if(col==0){
                        System.out.print((row)+"  ");
                        if (row == 8 && (startPlayer == 0)) System.out.print("\t\tPlayer vs. Opponent");
                        else if (row == 8) System.out.print("\t\tOpponent vs. Player");
                    }
                    else System.out.print((char)('@'+col)+"  ");
                }
            }System.out.println();
        }
    }


}
