package pp;

/*
* "I will practice academic and personal integrity and excellence of character
* and expect the same from others." - FSC Honor Code Pledge
* Author: Kyler Volakos, ID# 1210434
* CSC 3380 Section 001, kyler.volakos@gmail.com
 */
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * PP.java
 * "Pizza Pilgrimage" approximating a solution for the generic Traveling
 * Salesman Problem, not necessarily following the triangle inequality.
 * This version implements nearest-neighbor search.
 */
public class PP {
    
    public static Queue<TSPGraph> importGraph(Scanner input) {
        // Declaration
        int numIter = input.nextInt();
        Queue<TSPGraph> graphQueue = new LinkedList<>();
        // For each graph in the set
        for(int i = 0; i < numIter; i++) {
            int rows = input.nextInt();
            TSPGraph tempGraph = new TSPGraph(rows);
            // For each row in the graph
            for(int j = 0; j < rows; j++) {
                // For each node in the row
                for (int k = 0; k < rows; k++) {
                    // Create new "node" object in the graph
                    tempGraph.setNode(j, k, input.nextInt());
                }
            }
            // Add graph to the queue of graphs
            graphQueue.add(tempGraph);
        }
        return graphQueue;
    }
    
    public static ArrayList<Integer> nearestNeighbor(TSPGraph graph) {
        // Create Priority Queue to sort the graph, and ArrayList to hold the resulting path
        PriorityQueue<TSPNode> pQueue = new PriorityQueue<>(new SortByValue());
        ArrayList<Integer> NN = new ArrayList<>();
        
        // Traverse through the graph, and add all existing edges
        for(int i = 0; i < graph.getSize(); i++) {
            for(int j = 0; j < graph.getSize(); j++) {
                if(!(i == j)) { pQueue.add(graph.getNode(i, j)); }
            }
        }
        
        // Initial variables for nearest-neighbor
        TSPNode initial = pQueue.poll();
        int start = initial.getX();
        int res = 0;
        
        NN.add(start);
        
        // Traverse through the graph, find each 
        for(int i = 0; i < graph.getSize(); i++) {
            int min = Integer.MAX_VALUE;
            for(int j = 0; j < graph.getSize(); j++) {
                if (j != start && !(NN.contains(j))) {
                    int dist = graph.getDistance(start, j);
                    if (dist < min) {
                        min = dist;
                        res = j;
                    }
                }
            }
            if(!(NN.contains(res))){
                NN.add(res);
                start = res;
            } else {
                return NN;
            }
        }
        return null;
    }

    public static void main(String[] args) throws Exception {

        // Establishing input file and error if input file not found
        File inputFile = new File("graph.txt");
        if (!inputFile.exists()) {
            System.out.println(inputFile + " not found.");
            System.exit(0);
        }
        // Creating output file
        File outputFile = new File("results.txt");

        // Binding scanner input and PrintWriter output to input and output files
        Scanner input = new Scanner(inputFile);
        PrintWriter output = new PrintWriter(outputFile);
        
        // Importing all graphs
        Queue<TSPGraph> graphQueue = importGraph(input);
        int size = graphQueue.size();
        
        // Finding nearest-neighbor path for each graph
        for(int i = 0; i < size; i++) {
            TSPGraph graph = graphQueue.poll();
            ArrayList<Integer> NN = nearestNeighbor(graph);
            // Printing path
            for(int n : NN) {
                output.print(n + " ");
            }
            output.println();
            // Printing sum
            int start = NN.get(0);
            int sum = 0;
            for(int j = 1; j < NN.size(); j++) {
                sum += graph.getDistance(start, NN.get(j));
                start = NN.get(j);
            }
            sum += graph.getDistance(start, NN.get(0));
            output.println(sum);
            output.println();
        }
        output.flush();
    }

}
