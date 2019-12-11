package pp;

/*
* "I will practice academic and personal integrity and excellence of character
* and expect the same from others." - FSC Honor Code Pledge
* Author: Kyler Volakos, ID# 1210434
* CSC 3380 Section 001, kyler.volakos@gmail.com
 */

import java.util.Comparator;

public class TSPGraph {
    
    // TSPGraph class; holds 2d array of TSPNodes (def. below)
    
    private TSPNode[][] graph;
    private int size;
    
    public TSPGraph(int size) {
        graph = new TSPNode[size][size];
        this.size = size;
    }
    
    public TSPNode getNode(int x, int y) {
        return this.graph[x][y];
    }
    
    public void setNode(int x, int y, int node) {
        this.graph[x][y] = new TSPNode(x, y, node);
    }
    
    public int getSize() {
        return this.size;
    }
    
    public int getDistance(int x, int y) {
        return this.graph[x][y].getVal();
    }
    
}

class TSPNode {
    
    // Class for nodes in TSPGraph
    
    private int x;
    private int y;
    private int val;
    
    public TSPNode(int x, int y, int val) {
        this.x = x;
        this.y = y;
        this.val = val;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getVal() {
        return this.val;
    }
}

class SortByValue implements Comparator<TSPNode> {
    public int compare(TSPNode a, TSPNode b) {
        return a.getVal() - b.getVal();
    }
}
