public class MinMaxNode {
    private int value, alpha, beta;
    private boolean maxPlayer;

    public MinMaxNode(){
        value = -1;
        alpha = -1;
        beta  = -1;
        maxPlayer = true;
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

    public int maxValue(int[][] state, int plyLevel, int alpha, int beta){
        if (plyLevel == 5) return value;  //terminal stage, base case
        setValue(9999);
        //TODO: finish implementation
        if (maxPlayer){

        }
        if (isMaxPlayer()){

        }
        return -1;
    }
}
