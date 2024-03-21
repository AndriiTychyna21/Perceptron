public class Main {
    public static void main(String[] args) {
        double averageE = 0;
        Trainer myPerceptron;
        final int N = 10000;
        for (int n = 0; n < N; n++){
            System.out.println("Perceptron №" + (n+1));
            myPerceptron = new Trainer(3, 2, 3, 20);
            myPerceptron.train();
            averageE += myPerceptron.control();
            System.out.println();
        }
        averageE /= N;
        System.out.println("----------------------------------");
        System.out.println("Average mistake: " + averageE);
    }
}