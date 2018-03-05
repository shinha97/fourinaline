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


      Board b = new Board();
      b.isTerminal();
      b.printBoard(startPlayer);

      if (startPlayer == 0) System.out.print("\nPlayer's move is: ");
      else System.out.print("\nOpponent's move is: ");

      String move = input.next().toLowerCase();
      if (isValidMove(b,move)){
          System.out.println("Valid move!");
      }
      else System.out.println("Invalid move..");
  }

  /**======================
   *    HELPER FUNCTIONS
   * ======================*/

  public static boolean isValidMove(Board b,String move){
      if (((int)move.charAt(0) > 96) && ((int)move.charAt(0) < 105) && (move.charAt(1) >= 0) && (move.charAt(1) - '0' < 9)){//if the row/col is valid (input: a-h,1-8)
              if (b.getCell(((int)move.charAt(0)) -97,move.charAt(1) - '0') == '_'){//the move is an empty ti5le
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
