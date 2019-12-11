package pp;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Queue;
import static pp.trianglePP.importGraph;


/*
* "I will practice academic and personal integrity and excellence of character
* and expect the same from others." - FSC Honor Code Pledge
* Author: Kyler Volakos, ID# 1210434
* CSC 3380 Section 001, kyler.volakos@gmail.com
 */
public class verifier {
    
    public static void main(String[] args) throws Exception {
        
        // Input both graph and results files
        File graphFile = new File("graph.txt");
        if (!graphFile.exists()) {
            System.out.println(graphFile + " not found.");
            System.exit(0);
        }
        
        File resultsFile = new File("results.txt");
        if (!resultsFile.exists()) {
            System.out.println(resultsFile + " not found.");
            System.exit(0);
        }
        
        Scanner graph = new Scanner(graphFile);
        Scanner results = new Scanner(resultsFile);
        
        // Create graphs, error array
        Queue<TSPGraph> graphQueue = importGraph(graph);
        ArrayList<Integer> errors = new ArrayList<>();
        int size = graphQueue.size();
        
        // Iterate through each supplied graph, compare to results
        for (int i = 0; i < size; i++) {
            int chkSum = 0;
            TSPGraph chkGraph = graphQueue.poll();
            int graphSize = chkGraph.getSize();
            
            // declare vars for traversal
            int start = results.nextInt();
            int prev = start;
            int next = 0;
            // Traverse, add each edge together
            for(int j = 1; j < graphSize; j++) {
                next = results.nextInt();
                TSPNode match = chkGraph.getNode(prev, next);
                chkSum += match.getVal();
                prev = next;
            }
            // Add return to origin
            TSPNode match = chkGraph.getNode(start, next);
            chkSum += match.getVal();
            
            // Check computed sum against given sum
            int expSum = results.nextInt();
            if(chkSum != expSum) {
                errors.add(i+1);
            }
        }
        
        if(errors.isEmpty()) {
            System.out.println("All cases are valid!");
        } else {
            System.out.println("The following cases are invalid or incorrect: ");
            for (int e : errors) {
                System.out.print(e + " ");
            }
            System.out.println();
        }
    }
    
}
