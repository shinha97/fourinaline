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
      if (isValidMove(move)){
          System.out.println("Valid move!");
      }
      else System.out.println("Invalid move..");
  }
}
