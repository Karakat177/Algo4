package graph.dagsp;
import graph.Graph;
import graph.Edge;
import metrics.SimpleMetrics;
import java.util.*;

public class DAGSP {
    public static int[] shortestPath(Graph g, List<Integer> topo, int start, SimpleMetrics metrics){
        int n = g.n;
        int INF = 1_000_000_000;
        int[] dist = new int[n];
        Arrays.fill(dist, INF);
        dist[start]=0;

        metrics.start();
        for(int u: topo){
            if(dist[u]==INF) continue;
            for(Edge e: g.adj.get(u)){
                metrics.incOps();
                if(dist[u]+e.weight < dist[e.to]){
                    dist[e.to] = dist[u]+e.weight;
                    metrics.incOps();
                }
            }
        }
        metrics.stop();
        return dist;
    }
}

