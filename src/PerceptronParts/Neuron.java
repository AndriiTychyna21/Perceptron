package PerceptronParts;

public class Neuron {
    private Connector[] inputs;
    private double output;

    public Neuron(){
        this.inputs = new Connector[]{new Connector()};
    }

    public Neuron(int inputs){
        this.inputs = new Connector[inputs];
        for (int i = 0; i < this.inputs.length; i++){
            this.inputs[i] = new Connector();
        }
    }

    public void resultCalculate(){
        double s=0, w, x;
        for (int i = 0; i < inputs.length; i++){
            x = inputs[i].getInput();
            w = inputs[i].getW();
            s += x * w;
        }
        double y = 1.0/(1 + Math.exp(-s));
        output = y;
    }

    public void setInput (int i, double value){
        if (0 <= i && i < inputs.length){
            inputs[i].setInput(value);
        }
    }

    public double getOutput() {
        return output;
    }


    public int getInputNumber(){
        return inputs.length;
    }
    public double getInput(int number){
        if (number >= 0 && number < inputs.length){
            return inputs[number].getInput();
        }
        return -1;
    }

    public double getW(int number){
        if (number >= 0 && number < inputs.length){
            return inputs[number].getW();
        }
        return -1;
    }

    public void setW(int number, double W){
        if (number >= 0 && number < inputs.length){
            inputs[number].setW(W);
        }
    }
}
