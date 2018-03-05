import java.util.*;
import java.awt.Point;

public class Board{
    private static char [][] board;
    private final int winCount = 4;

    public Board(){
        board = new char[8][8];

        //populate empty board
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[i].length;j++){
                board[i][j] = '_';
            }
        }

        // test generate random boards
        // Random r = new Random();
        // for(int i=0;i<8;i++){
        // 	for(int j=0;j<8;j++){
        // 		if(r.nextInt(3)==0) board[i][j]='X';
        // 		else if(r.nextInt(3)==1) board[i][j]='O';
        // 		else board[i][j]='_';
        // 	}
        // }

        //visual test board
        // board=new char[][]{{'O','O','O','_','O','_','_','_'},
        //                    {'_','O','O','_','_','_','_','_'},
        // 					          {'_','_','O','O','_','_','_','_'},
        // 					          {'_','_','_','O','_','_','_','_'},
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
    public int isTerminal(){
        //check for rows for X or O victory, check 5 positions across 8 columns
        for(int col=0;col<board.length-winCount+1;col++){
            for(int row=0;row<board.length;row++){
                if(board[row][col]==board[row][col+1] &&
                        board[row][col+1]==board[row][col+2] &&
                        board[row][col+2]==board[row][col+3]){
                    if(board[row][col]=='X'){
                        // System.out.println("ROW: row "+(char)('@'+row+1)+" col "+(col+1));
                        return 1;
                    }
                    if(board[row][col]=='O'){
                        // System.out.println("ROW: row "+(char)('@'+row+1)+" col "+(col+1));
                        return -1;
                    }
                }
            }
        }
        //check columns for X or O victory, check 5 positions across 8 rows
        for(int row=0;row<board.length-winCount+1;row++){
            for(int col=0;col<board.length;col++){
                if(board[row][col]==board[row+1][col] &&
                        board[row+1][col]==board[row+2][col] &&
                        board[row+2][col]==board[row+3][col]){
                    if(board[row][col]=='X'){
                        // System.out.println("COLUMN: row "+(char)('@'+row+1)+" col "+(col+1));
                        return 1;
                    }
                    if(board[row][col]=='O'){
                        // System.out.println("COLUMN: row "+(char)('@'+row+1)+" col "+(col+1));
                        return -1;
                    }
                }
            }
        }
        return 0;
    }
    public char[][] getBoard(){
        return board;
    }
    public int evaluate(char c){
        //TODO: Daniel please write a kick-ass eval function here
        //Given a board, call it's eval function to give a rating on its desirability
        int score = 0;
        for(int col=0;col<board.length-2;col++){
            for(int row=0;row<board.length;row++){
                if(board[row][col]==c){
                    if(board[row][col]+1<board.length);
                }
            }
        }

        //check for rows for X or O victory, check 5 positions across 8 columns
        for(int col=0;col<board.length-winCount+1;col++){
            for(int row=0;row<board.length;row++){
                if(board[row][col]==board[row][col+1] &&
                        board[row][col+1]==board[row][col+2] &&
                        board[row][col+2]==board[row][col+3]){
                    if(board[row][col]=='X'){
                        // System.out.println("ROW: row "+(char)('@'+row+1)+" col "+(col+1));
                        return 1;
                    }
                    if(board[row][col]=='O'){
                        // System.out.println("ROW: row "+(char)('@'+row+1)+" col "+(col+1));
                        return -1;
                    }
                }
            }
        }
        //check columns for X or O victory, check 5 positions across 8 rows
        for(int row=0;row<board.length-winCount+1;row++){
            for(int col=0;col<board.length;col++){
                if(board[row][col]==board[row+1][col] &&
                        board[row+1][col]==board[row+2][col] &&
                        board[row+2][col]==board[row+3][col]){
                    if(board[row][col]=='X'){
                        // System.out.println("COLUMN: row "+(char)('@'+row+1)+" col "+(col+1));
                        return 1;
                    }
                    if(board[row][col]=='O'){
                        // System.out.println("COLUMN: row "+(char)('@'+row+1)+" col "+(col+1));
                        return -1;
                    }
                }
            }
        }
        return -1;
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
