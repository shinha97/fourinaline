import java.awt.Point;
import java.util.Arrays;

public class MinMaxNode {
    private int value, alpha, beta;
    private Point currPosition;
    private boolean maxPlayer;
    private Board state;

    public MinMaxNode(){
        value = -1;
        alpha = -1;
        beta  = -1;
        maxPlayer = true;
    }

    public MinMaxNode(int nvalue, int nalpha, int nbeta, Point ncurrPosition, boolean nmaxPlayer, Board nstate){
        value        = nvalue;
        alpha        = nalpha;
        beta         = nbeta;
        currPosition = ncurrPosition;
        maxPlayer    = nmaxPlayer;
        //set board, given a new board state
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                state.updateBoard(new Point(i,j), nstate.getCell(i,j);
            }

        }
    }

    public void setCurrPosition(Point currPosition) {
        this.currPosition = currPosition;
    }

    public void setState(Board state) {
        this.state = state;
    }

    public void setMaxPlayer(boolean maxPlayer) {
        this.maxPlayer = maxPlayer;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public void setBeta(int beta) {
        this.beta = beta;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getAlpha() {
        return alpha;
    }

    public int getBeta() {
        return beta;
    }

    public int getValue() {
        return value;
    }

    public boolean isMaxPlayer() { return maxPlayer;}

    public int maxValue(Board state, int plyLevel, int alpha, int beta){
        //TODO: redefine stop case to: "time run out && depth >= 5"
        if (plyLevel == 5) return state.evaluate('X');  //base case, leaf node has been reached.
        value = -9999;

        for (int i = getLeftBound(currPosition.x); i < getRightBound(currPosition.x);i++){
            for (int j = getLeftBound(currPosition.y); j < getRightBound(currPosition.y);j++){
                if (isEmpty(currPosition)){
                    Board newState = new Board(state.updateBoard(currPosition, 'X'));
                    MinMaxNode min = new MinMaxNode(value, alpha, beta, currPosition, false, newState);
                    //creates proper new min node
                    //TODO: Use this min node for new state, maaybe redefine recursive calls?
                    int newValue = min.minValue(state, plyLevel+1, alpha, beta);
                    if (newValue > value) value = newValue;
                    if (newValue >= beta) return value;
                    if (newValue > alpha) alpha = newValue;
                }
            }
        }

        return value;
    }

    public int minValue(Board state, int plyLevel, int alpha, int beta){
        if (plyLevel == 5) return state.evaluate('O');  //base case, leaf node has been reached.
        value = 9999;

        for (int i = getLeftBound(currPosition.x); i < getRightBound(currPosition.x);i++){
            for (int j = getLeftBound(currPosition.y); j < getRightBound(currPosition.y);j++){
                if (isEmpty(currPosition)){
                    Board newState = new Board(state.updateBoard(currPosition, 'O'));
                    MinMaxNode max = new MinMaxNode(value, alpha, beta, currPosition, true, newState);

                    int newValue = max.maxValue(state, plyLevel+1, alpha, beta);
                    if (newValue < value) value = newValue;
                    if (newValue <= alpha) return value;
                    if (newValue < beta) beta = newValue;
                }
            }
        }

        return value;
    }

    /*==================
     * HELPER FUNCTIONS
     *==================*/

    private int getLeftBound(int x){
        if ((x -3) < 0) return 0;
        return x-3;
    }
    private int getRightBound(int x){
        if (x + 3 > 7) return 7;
        return x+3;
    }
    private boolean isEmpty(Point x){
        if (state.getCell(x.x, x.y) == '_') return true;
        return false;
    }
}
