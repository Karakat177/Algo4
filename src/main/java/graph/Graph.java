package graph;

import java.util.*;

public class Graph {
    public int n;
    public List<List<Edge>> adj;

    public Graph(int n){
        this.n = n;
        adj = new ArrayList<>();
        for(int i=0;i<n;i++) adj.add(new ArrayList<>());
    }

    public void addEdge(int u, int v, int w){
        adj.get(u).add(new Edge(u, v, w));
    }
}
