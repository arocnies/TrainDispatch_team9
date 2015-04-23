import java.util.Scanner;

/**
 * Created by J. Nguy on 4/22/2015.
 */
public class Output {
    public static void main(String [] args) {
        Scanner sc = new Scanner(System.in);
        String userMap, userTrains, userTrials, userDispatch, userSamples;

        // Checks for program arguments
        if(args.length == 0)
        {
            System.out.println("Please input the map [i.e. res\\name_of_map.graph] : ");
            userMap = sc.next();

            System.out.println("Please input the dispatch for the visualization [1 for Base Case - 2 for Single Locking - 3 for Optimized] : ");
            userDispatch = sc.next();

            System.out.println("Please input the number of trials per calculation : ");
            userTrials = sc.next();

            System.out.println("Please input the max number of trains : ");
            userTrains = sc.next();

            System.out.println("Please input the number of samples per test : ");
            userSamples = sc.next();
        } else {
            userMap = args[0];
            userTrials = args[1];
            userTrains = args[2];
            userSamples = args[3];
            userDispatch = args[4];
        }

        DispatchTest.DispatchVisualization(userMap, userDispatch);

        JFreeChartTest.JFreeCreation(userMap, userMap, userTrials, userTrains, userSamples);


    }
}
