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

    public MinMaxNode(int nalpha, int nbeta, Point ncurrPosition, boolean nmaxPlayer, Board nstate){
        alpha        = nalpha;
        beta         = nbeta;
        currPosition = ncurrPosition;
        maxPlayer    = nmaxPlayer;
        //set board, given a new board state
        state = new Board();
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                state.updateBoard(new Point(i,j), nstate.getCell(i,j));
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

    public Point getCurrPosition() {
        return currPosition;
    }

    public Board getState() {
        return state;
    }

    public boolean isMaxPlayer() { return maxPlayer;}

    public MinMaxNode maxValue(Board state, int plyLevel, int alpha, int beta){
        //TODO: redefine stop case to: "time run out && depth >= 5"
        if (plyLevel == 5){
            if (isMaxPlayer()){
                this.value = state.evaluate('X');  //base case, leaf node has been reached.
                return this;
            }
            else{
                this.value = state.evaluate('O');
                return this;
            }
        }
        value = -9999;

        for (int i = getLeftBound(currPosition.x); i < getRightBound(currPosition.x);i++){
            for (int j = getLeftBound(currPosition.y); j < getRightBound(currPosition.y);j++){
                if (isEmpty(currPosition)){
                    Board newState = new Board(state.updateBoard(currPosition, 'X'));
                    MinMaxNode min = new MinMaxNode(alpha, beta, currPosition, false, newState);

                    int newValue = min.minValue(state, plyLevel+1, alpha, beta).getValue();
                    if (newValue > value) value = newValue;
                    if (newValue >= beta) return this;
                    if (newValue > alpha) alpha = newValue;
                }
            }
        }

        return this;
    }

    public MinMaxNode minValue(Board state, int plyLevel, int alpha, int beta){
        //if (plyLevel == 5) return state.evaluate('O');  //base case, leaf node has been reached.
        if (plyLevel == 5){
            if (isMaxPlayer()){
                this.value = state.evaluate('X');  //base case, leaf node has been reached.
                return this;
            }
            else{
                this.value = state.evaluate('O');
                return this;
            }
        }
        value = 9999;

        for (int i = getLeftBound(currPosition.x); i < getRightBound(currPosition.x);i++){
            for (int j = getLeftBound(currPosition.y); j < getRightBound(currPosition.y);j++){
                if (isEmpty(currPosition)){
                    Board newState = new Board(state.updateBoard(currPosition, 'O'));
                    MinMaxNode max = new MinMaxNode(alpha, beta, currPosition, true, newState);

                    int newValue = max.maxValue(state, plyLevel+1, alpha, beta).getValue();
                    if (newValue < value) value = newValue;
                    if (newValue <= alpha) return this;
                    if (newValue < beta) beta = newValue;
                }
            }
        }

        return this;
    }

    public static MinMaxNode minimax(int depth, boolean maxPlayer, int alpha, int beta, MinMaxNode node){
        if (depth == 5){
            if (node.isMaxPlayer()){
                node.value = node.getState().evaluate('X');  //base case, leaf node has been reached.
                return node;
            }
            else{
                node.value = node.getState().evaluate('O');
                return node;
            }
        }
        if (maxPlayer){
            int best = -9999;
            for (int i = node.getLeftBound(node.currPosition.x); i < node.getRightBound(node.currPosition.x);i++){
                for (int j = node.getLeftBound(node.currPosition.y); j < node.getRightBound(node.currPosition.y);j++){
                    if (node.isEmpty(node.currPosition)){
                        Board newState = new Board(node.state.updateBoard(node.currPosition, 'O'));
                        MinMaxNode max = new MinMaxNode(alpha, beta, node.currPosition, false, newState);
                        MinMaxNode nextLevel = minimax(depth+1, false, alpha,beta,max);

                        best = Math.max(best, nextLevel.getValue());
                        node.setValue(best);
                        alpha = Math.max(alpha, best);

                        // Alpha Beta Pruning
                        if (beta <= alpha)
                            break;
                    }
                }
            }
            return node;
        }
        else{
            int best = 9999;
            for (int i = node.getLeftBound(node.currPosition.x); i < node.getRightBound(node.currPosition.x);i++){
                for (int j = node.getLeftBound(node.currPosition.y); j < node.getRightBound(node.currPosition.y);j++){
                    if (node.isEmpty(node.currPosition)){
                        Board newState = new Board(node.state.updateBoard(node.currPosition, 'X'));
                        MinMaxNode min = new MinMaxNode(alpha, beta, node.currPosition, true, newState);
                        MinMaxNode nextLevel = minimax(depth+1, true, alpha,beta,min);

                        best = Math.min(best, nextLevel.getValue());
                        node.setValue(best);
                        beta = Math.min(beta, best);

                        // Alpha Beta Pruning
                        if (beta <= alpha)
                            break;
                    }
                }
            }
            return node;
        }
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
