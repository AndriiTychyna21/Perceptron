import PerceptronParts.Neuron;
public class Perceptron {
    protected final int INPUT;
    protected final int OUTPUT;
    protected final int HIDDEN;

    protected double[] inputLayer;
    protected Neuron[][] hiddenLayer;
    protected Neuron[] outputLayer;

    public Perceptron(int input, int output, int hidden){
        this.INPUT = input;
        this.OUTPUT = output;
        this.HIDDEN = hidden - 1;

        inputLayer = new double[INPUT];
        hiddenLayer = new Neuron[INPUT][HIDDEN];
        outputLayer = new Neuron[OUTPUT];

        for (int i = 0; i < INPUT; i++){
            for (int j = 0; j < HIDDEN; j++){
                hiddenLayer[i][j] = new Neuron(INPUT);
            }
        }

        for (int i = 0; i < OUTPUT; i++){
            outputLayer[i] = new Neuron(INPUT);
        }
    }

    private void outputToTheNext(int layer){
        double temp;
        if (layer >= 0 && layer < HIDDEN) {
            for (int i = 0; i < hiddenLayer.length; i++) {
                hiddenLayer[i][layer].resultCalculate();
                temp = hiddenLayer[i][layer].getOutput();
                if (layer + 1 < HIDDEN) {
                    for (int j = 0; j < hiddenLayer.length; j++) {
                        hiddenLayer[j][layer + 1].setInput(i, temp);
                    }
                } else if (layer + 1 == HIDDEN) {
                    for (int j = 0; j < outputLayer.length; j++) {
                        outputLayer[j].setInput(i, temp);
                    }
                }
            }
        }
    }

    public double[] calculateResult(double[] initialInput){
        if (initialInput.length != INPUT){
            System.out.println("Input error");
            return new double[INPUT];
        }
        for (int i = 0; i < INPUT; i++){
            inputLayer[i] = initialInput[i];
        }
        for (int i = 0; i < initialInput.length; i++){
            for (int j = 0; j < INPUT; j++){
                hiddenLayer[j][0].setInput(i, inputLayer[i]);
            }
        }
        for (int i = 0; i < HIDDEN; i++){
            outputToTheNext(i);
        }
        double[] result = new double[OUTPUT];
        for (int i = 0; i < result.length; i++){
            outputLayer[i].resultCalculate();
            result[i] = outputLayer[i].getOutput();
        }
        return result;
    }
}
