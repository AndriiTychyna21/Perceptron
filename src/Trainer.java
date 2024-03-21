import java.util.Random;

public class Trainer extends Perceptron{
    private double[][] test_images = {
            {2, 3, 6}, {2, 3, 7},
            {2, 3, 5}, {2, 4, 6},
            {2, 4, 7}, {2, 4, 5},
            {2, 2, 6}, {2, 2, 7},
            {2, 2, 5}, {3, 3, 6},
            {3, 3, 7}, {3, 3, 5},
            {3, 4, 6}, {3, 4, 7},
            {3, 4, 5}, {3, 2, 6},
            {3, 2, 7}, {3, 2, 5},
            {1, 3, 6}, {1, 3, 7},
            {1, 3, 5}, {1, 4, 6},
            {1, 4, 7}, {1, 4, 5},
            {1, 2, 6}, {1, 2, 7},
            {1, 2, 5}, {2, 3, 8},
            {2, 5, 6}, {4, 3, 6}
    };

    private double[][] function_value = new double[test_images.length][OUTPUT];

    private int toLearn;

    private double average;

    private final double n = 0.3;

    public Trainer(int input, int output, int hidden, int toLearn){
        super(input, output, hidden);
        shuffle(test_images.length);
        this.toLearn = toLearn;
        double averageCalc = 0;
        for (int i = 0; i < test_images.length; i++){
            averageCalc += function(test_images[i][0], test_images[i][1], test_images[i][2]);
        }
        this.average = averageCalc / test_images.length;
        for (int i = 0; i < test_images.length; i++){
            function_value[i][0] = function(test_images[i][0], test_images[i][1], test_images[i][2]);
            if (function_value[i][0] > average) function_value[i][1] = 1;
            else function_value[i][1] = 0;
        }
        normalise();
    }

    private double function(double x1, double x2, double x3){
        double result = Math.pow(2, x1) + Math.cos(x2) - Math.sin(x3);
        return result;
    }

    private void shuffle(int range) {
        int index;
        double[] temp;
        Random random = new Random();
        for (int i = range - 1; i > 0; i--) {
            index = random.nextInt(i);
            temp = test_images[index];
            test_images[index] = test_images[i];
            test_images[i] = temp;
            temp = function_value[index];
            function_value[index] = function_value[i];
            function_value[i] = temp;
        }
    }
    private void normalise(){
       /* double min, max, range;
        for (int j = 0; j < INPUT; j++){
            min = test_images[0][j];
            max = test_images[0][j];
            for (int i = 1; i < test_images.length; i++){
                if (test_images[i][j] > max) max = test_images[i][j];
                if (test_images[i][j] < min) min = test_images[i][j];
            }
            range = max - min;
            for (int i = 0; i < test_images.length; i++){
                test_images[i][j] = (test_images[i][j] - min)/range;
            }
        }
        for (int j = 0; j < OUTPUT; j++){
            min = function_value[0][j];
            max = function_value[0][j];
            for (int i = 1; i < function_value.length; i++){
                if (function_value[i][j] > max) max = function_value[i][j];
                if (function_value[i][j] < min) min = function_value[i][j];
            }
            range = max - min;
            for (int i = 0; i < function_value.length; i++){
                function_value[i][j] = (function_value[i][j] - min)/range;
            }
        }*/
        for (int i = 0; i < test_images.length; i++){
            for (int j =0; j < test_images[i].length; j++){
                test_images[i][j] = 1.0/(1 + Math.exp(-test_images[i][j]));
            }
        }
        for (int i = 0; i < function_value.length; i++){
            for (int j =0; j < function_value[i].length; j++){
                function_value[i][j] = 1.0/(1 + Math.exp(-function_value[i][j]));
            }
        }

    }

    public void train(){
        double[] actual_value = new double[OUTPUT];
        double totalE, dw, counter;
        int[][] possible_ways;
        int size, previos_connect;
        shuffle(toLearn);
        for(int i = 0; i < toLearn; i++){
            actual_value = calculateResult(test_images[i]);
            totalE=0;
            for (int e = 0; e < OUTPUT; e++){
                totalE += Math.pow(function_value[i][e] - actual_value[e], 2);
            }
            totalE /= 2;
            System.out.println((i+1) + ") Total E = " + totalE);
            for (int C = 0; C < HIDDEN; C++){
                for (int R = 0; R < hiddenLayer.length; R++){
                    for (int thisW = 0; thisW < hiddenLayer[R][C].getInputNumber(); thisW++){
                        dw = 0;
                        size = HIDDEN-C-1;
                        possible_ways = generateAllCombinations(size);
                        for (int way = 0; way < possible_ways.length; way++){
                            counter = 1;
                            for (int hidden_way = 0; hidden_way < possible_ways[way].length - 1; hidden_way++){
                                if (hidden_way == 0) previos_connect = R;
                                else previos_connect = possible_ways[way][hidden_way-1];
                                counter *= hiddenLayer[possible_ways[way][hidden_way]][C + 1 + hidden_way].getW(previos_connect);
                                counter *= hiddenLayer[possible_ways[way][hidden_way]][C + 1 + hidden_way].getOutput();
                                counter *= (1 - hiddenLayer[possible_ways[way][hidden_way]][C + 1 + hidden_way].getOutput());
                            }
                            if (size == 0) previos_connect = R;
                            else previos_connect = possible_ways[way][size-1];
                            counter *= outputLayer[possible_ways[way][size]].getW(previos_connect);
                            counter *= actual_value[possible_ways[way][size]];
                            counter *= (1 - actual_value[possible_ways[way][size]]);
                            counter *= (actual_value[possible_ways[way][size]] - function_value[i][possible_ways[way][size]]);
                            dw += counter;
                        }
                        dw *= hiddenLayer[R][C].getOutput() * (1 -hiddenLayer[R][C].getOutput()) * hiddenLayer[R][C].getInput(thisW);
                        dw = hiddenLayer[R][C].getW(thisW) - (n * dw);
                        hiddenLayer[R][C].setW(thisW, dw);
                    }
                }
            }
            for (int O = 0; O < OUTPUT; O++){
                for (int thisW = 0; thisW < outputLayer[O].getInputNumber(); thisW++){
                    dw = 1;
                    dw *= actual_value[O] * (1 - actual_value[O]) * outputLayer[O].getInput(thisW);
                    dw *= (actual_value[O] - function_value[i][O]);
                    dw = outputLayer[O].getW(thisW) - (n * dw);
                    outputLayer[O].setW(thisW, dw);
                }
            }
        }
    }

    private int[][] generateAllCombinations(int N) {
        int numOfCombinations = ((int) Math.pow(hiddenLayer.length, N)) * OUTPUT;
        int[][] combinations = new int[numOfCombinations][N+1];

        for (int i = 0; i < numOfCombinations; i+=OUTPUT) {
            int temp = i;
            for (int j = 0; j < N; j++) {
                combinations[i][j] = temp % hiddenLayer.length;
                temp /= hiddenLayer.length;
            }
            combinations[i][N] = 0;
            for (int output = i+1; output < i + OUTPUT; output++){
                combinations[output] = combinations[output-1];
                combinations[output][N]++;
            }
        }
        return combinations;
    }

    public double control(){
        double totalE = 0;
        for (int i = toLearn; i < test_images.length; i++){
            double[] actual_value = calculateResult(test_images[i]);
            for (int e = 0; e < OUTPUT; e++){
                totalE += Math.pow(function_value[i][e] - actual_value[e], 2);
            }
        }
        totalE /= (2*(test_images.length - toLearn));
        return totalE;
    }

}
