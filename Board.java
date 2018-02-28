import java.util.*;

public class Board{
	private char [][] board;
	private final int winCount = 4;

	public Board(){
		board = new char[8][8];

		//populate empty board
		for(int i=0;i<board.length;i++){
			for(int j=0;j<board[i].length;j++){
				board[i][j] = '_';
			}
		}

		// // test generate random boards
		// Random r = new Random();
		// for(int i=0;i<8;i++){
		// 	for(int j=0;j<8;j++){
		// 		if(r.nextInt(3)==0) board[i][j]='X';
		// 		else if(r.nextInt(3)==1) board[i][j]='O';
		// 		else board[i][j]='_';
		// 	}
		// }

		// //visual test board
		// board=new char[][]{{'O','O','O','_','O','_','_','_'},
		// 			 						 {'_','O','O','_','_','_','_','_'},
		// 								   {'_','_','O','O','_','_','_','_'},
		// 								   {'_','_','_','O','O','_','_','_'},
		// 								   {'_','_','_','_','_','_','_','_'},
		// 									 {'_','_','_','_','_','_','_','_'},
		// 									 {'_','_','_','_','_','_','_','_'},
		// 								   {'_','_','_','_','_','_','_','_'}};
	}
	public int evaluate(){
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
		//check diagonals
		for(int row=0;row<board.length-winCount+1;row++){
			for(int col=0;col<board.length-winCount+1;col++){
				//check forward diagonals for X or O victory
				if(board[row+winCount-1][col]==board[row+winCount-2][col+1] &&
				board[row+winCount-2][col+1]==board[row+winCount-3][col+2] &&
				board[row+winCount-3][col+2]==board[row+winCount-4][col+3]){
					if(board[row+winCount-1][col]=='X'){
						// System.out.println("FORWARD DIAGONAL: row "+(char)('@'+row+winCount)+" col "+(col+1));
						return 1;
					}
					if(board[row+winCount-1][col]=='O'){
						// System.out.println("FORWARD DIAGONAL: row "+(char)('@'+row+winCount)+" col "+(col+1));
						return -1;
					}
				}
				//check backwards diagonals for X or O victory
				if(board[row][col+winCount-4]==board[row+1][col+winCount-3] &&
				board[row+1][col+winCount-3]==board[row+2][col+winCount-2] &&
				board[row+2][col+winCount-2]==board[row+3][col+winCount-1]){
					if(board[row][col+winCount-4]=='X'){
						// System.out.println("BACKWARD DIAGONAL: row "+(char)('@'+row+1)+" col "+(col+winCount-3));
						return 1;
					}
					if(board[row][col+winCount-4]=='O'){
						// System.out.println("BACKWARD DIAGONAL: row "+(char)('@'+row+1)+" col "+(col+winCount-3));
						return -1;
					}
				}
			}
		}
		//no victories for X or O
		// System.out.println("NO VICTORIES");
		return 0;
	}
	public void printBoard(){
		for(int col=0;col<9;col++){//column
		  for(int row=0;row<9;row++){//row
		    if(col-1 >= 0 && row-1 >= 0)
		      System.out.print(board[col-1][row-1]+"  ");
		    else{
		      if(col==0 && row==0) System.out.print("   ");
		      else if(col==0) System.out.print((row)+"  ");
		      else System.out.print((char)('@'+col)+"  ");
		    }
		  }System.out.println("\n");
		}
	}
	public static void main(String[] args){
		Board b = new Board();
		b.evaluate();
		b.printBoard();
	}
}