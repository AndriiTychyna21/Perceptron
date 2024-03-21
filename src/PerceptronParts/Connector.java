package PerceptronParts;

public class Connector {
    private double input;
    private double w;

    public Connector(){
        this.w = 0.5;

    }

    public Connector(double w){
        this.w = w;
    }

    public Connector(double input, double w){
        this.input = input;
        this.w = w;
    }
    public void setInput(double input) {
        this.input = input;
    }

    public void setW(double w) {
        this.w = w;
    }

    public double getInput() {
        return input;
    }

    public double getW() {
        return w;
    }

}
