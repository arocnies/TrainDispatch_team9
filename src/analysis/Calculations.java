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

    public static double deviation(double[] array){
        double sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        double mu = 0;
        mu = sum / array.length;

        double[] variance = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            variance[i] = (array[i] - mu) * (array[i] - mu);
        }

        double varianceSum = 0;
        for (int i = 0; i < array.length; i++) {
            varianceSum += variance[i];
        }
        varianceSum = varianceSum / array.length;

        double result = 0;
        result = Math.sqrt(varianceSum);



        return result;

    }


    public static void main(String [] args) {
        double[] array = {5, 6, 5, 4, 6, 5};
        double result = deviation(array);
        double result1 = deviation(array);
        System.out.println("Standard Deviation Upper : " + result);
        System.out.println("Standard Deviation Lower : " + result1);

        System.out.printf("Train");

    }

}
