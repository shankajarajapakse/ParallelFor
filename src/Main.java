import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main
{

    private static final int MAX_THREADS = Runtime.getRuntime().availableProcessors();
    private final ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);

    public static void main(String[] args)
    {
        System.out.println("Matrix multiplexer");
        Scanner s = new Scanner(System.in);
        System.out.println("Enter matrix A n,m");
        int rowsA = s.nextInt();
        int columnsA = rowsA;
        System.out.println("Enter matrix B n,m");
        int rowsB = s.nextInt();
        int columnsB = rowsB;
        ArrayList<ArrayList<Double>> a = generateRandomMatrix(rowsA, columnsA);
        ArrayList<ArrayList<Double>> b = generateRandomMatrix(rowsB, columnsB);
        System.out.println("--------------------A---------------------");
        System.out.println(Arrays.toString(new ArrayList[]{a}));
        System.out.println("--------------------B---------------------");
        System.out.println(Arrays.toString(new ArrayList[]{b}));
        System.out.println("------------------Result------------------");
        ArrayList<ArrayList<Double>> result = serialMultiplication(a,b);
        parallelMultiply(a,b);
//        for (ArrayList<Double> res :result)
//        {
//            System.out.println(Arrays.toString(new ArrayList[]{res}));
//        }
    }

    public static ArrayList<ArrayList<Double>> serialMultiplication(ArrayList<ArrayList<Double>> a, ArrayList<ArrayList<Double>> b)
    {
        if (b.size() == a.get(0).size()) {
            long startTime = System.currentTimeMillis();

            ArrayList<ArrayList<Double>> matrix = new ArrayList<ArrayList<Double>>();
            double tempVal = 0.0;
            ArrayList<Double> row;
            int columns = a.get(0).size();
            int rows = a.size();
            int bSize = b.get(0).size();
            for (int k = 0; k < rows; k++) {
                row = new ArrayList<Double>();
                for (int j = 0; j < bSize; j++) {
                    tempVal = 0.0;
                    for (int i = 0; i < columns; i++) {
                        tempVal += a.get(k).get(i) * b.get(i).get(j);
                    }
                    row.add(Math.round(tempVal*1)/10.0);
                }
                matrix.add(row);
            }
            System.out.println("Time taken = "+ (System.currentTimeMillis() - startTime) + " milliseconds.");
            return matrix;
        }
        else
        {
            System.out.println("Please enter valid matrices to multiply");
            return null;
        }
    }

    public static ArrayList<ArrayList<Double>> generateRandomMatrix(int rows, int columns)
    {
        ArrayList<ArrayList<Double>> matrix = new ArrayList<ArrayList<Double>>();
        ArrayList<Double> tempRow;

        for (int i = 0; i < rows; i++)
        {
            tempRow = new ArrayList<Double>();
            for (int j = 0; j < columns ; j++)
            {
                tempRow.add(Math.round((new Random().nextDouble())*1000.0)/100.0);                                  //limiting to two decima; pointss
            }
            matrix.add(tempRow);
        }

        return matrix;
    }

    public static void parallelMultiply(ArrayList<ArrayList<Double>> a, ArrayList<ArrayList<Double>> b)
    {
        System.out.println("------------------A*B parallel---------------------");
        long startTime = System.currentTimeMillis();
        ParallelMatrixMultiplication pm = new ParallelMatrixMultiplication(a,b);
        pm.multiply();
        System.out.println("Time taken to parallel multiplication = " + (System.currentTimeMillis()-startTime) + " milliseconds.");
    }
}
