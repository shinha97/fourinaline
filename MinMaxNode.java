public class MinMaxNode {
    private int value, alpha, beta;
    private String type;

    public MinMaxNode(){
        value = -1;
        alpha = -1;
        beta  = -1;
        type  = "max";
    }

    public void setType(String type) {
        this.type = type;
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

    public String getType() {
        return type;
    }

    public int maxValue(int[][] state, int plyLevel, int alpha, int beta){
        if (plyLevel == 5) return value;  //terminal stage, base case
        value = 9999;
        //TODO: finish implementation
        return -1;
    }
}
