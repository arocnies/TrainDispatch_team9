package analysis;

/**
 * Package : analysis
 * Created by J. Nguy on 4/21/2015.
 */
public class Calculations {

    public static double mean(double sum, int n) {
        double placeholder = sum / n;
        return placeholder;
    }

    public static double variance(double[] array, int n) {
        double summation = 0.0;
        double [] arrayTwo = new double [n];
        for (int m = 0; m <= array.length; m++) {
            arrayTwo[m] = array[m * m];
            summation =+ arrayTwo[m];
        }
        summation = summation / (n - 1);
        return summation;
    }

    public static double deviation(double variance) {
        double output = 0.0;
        output = Math.sqrt(variance);
        return output;
    }

    public static double deviationOfUpper(double sum, int n, double[] array, double myNumber) {
        double outgoing;
        outgoing = mean(sum, n);
        outgoing = variance(array, n);
        outgoing = deviation(outgoing);
        double upper = myNumber + outgoing;
        return upper;
    }

    public static double deviationOfLower(double sum, int n, double[] array, double myNumber) {
        double outgoing;
        outgoing = mean(sum, n);
        outgoing = variance(array, n);
        outgoing = deviation(outgoing);
        double lower = myNumber - outgoing;
        return lower;
    }
}
