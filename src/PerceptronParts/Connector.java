package PerceptronParts;

import java.util.Random;

public class Connector {
    private double input;
    private double w;

    public Connector(){
        Random random = new Random();
        this.w = 0.4 + random.nextDouble() * 0.2;
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
