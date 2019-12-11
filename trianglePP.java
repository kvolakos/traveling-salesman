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
import java.util.Queue;
import java.util.PriorityQueue;

/**
 * trianglePP.java
 * "Pizza Pilgrimage" approximating a solution for the metric Traveling
 * Salesman Problem, i.e. one following the triangle inequality.
 * This program constructs a minimum spanning tree from the supplied graph
 * to conduct nearest-neighbor search.
 */
public class trianglePP {
    
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
    
    public static ArrayList<Integer> createMST(TSPGraph graph) {
        // Initialize p. queue for sorting graph, and ArrayList for holding MST
        PriorityQueue<TSPNode> pQueue = new PriorityQueue<>(new SortByValue());
        ArrayList<Integer> MST = new ArrayList<>();
        
        // Add nodes to p. queue
        for(int i = 0; i < graph.getSize(); i++) {
            for(int j = 0; j < graph.getSize(); j++) {
                if(!(i == j)) { pQueue.add(graph.getNode(i, j)); }
            }
        }
        
        // Pull shortest path in p. queue, set X to origin and Y to origin dest
        TSPNode origin = pQueue.poll();
        MST.add(origin.getX());
        MST.add(origin.getY());
        
        // Iterate through remaining graph, finding minimum edge from each dest
        while(graph.getSize() > MST.size()) {
            for (TSPNode n : pQueue) {
                if(MST.contains(n.getX())) {
                    if(!(MST.contains(n.getY()))) {
                        MST.add(n.getY());
                    }
                }
            }
        }
        
        return MST;
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
        
        // Creating minimum spanning trees and print
        int size = graphQueue.size();
        for (int i = 0; i < size; i++) {
            // Pull each graph and create MST
            TSPGraph graph = graphQueue.poll();
            ArrayList<Integer> MST = createMST(graph);
            // Print MST path
            for (int node : MST) {
                output.print(node + " ");
            }
            output.println();
            // Print sum
            int start = MST.get(0);
            int sum = 0;
            for(int j = 1; j < MST.size(); j++) {
                sum += graph.getDistance(start, MST.get(j));
                start = MST.get(j);
            }
            sum += graph.getDistance(start, MST.get(0));
            output.println(sum);
            output.println();
        }
        output.flush();
    }

}
