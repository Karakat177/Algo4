package graph.dagsp;
import graph.Graph;
import graph.Edge;
import metrics.SimpleMetrics;
import java.util.*;

public class LongestPathDAG {
    public static class Result {
        public int length;
        public List<Integer> path;
    }

    public static Result longestPath(Graph g, List<Integer> topo, int start, SimpleMetrics metrics){
        int n = g.n;
        int[] dp = new int[n];
        int[] prev = new int[n];
        Arrays.fill(dp, Integer.MIN_VALUE);
        Arrays.fill(prev, -1);
        dp[start]=0;

        metrics.start();
        for(int u: topo){
            if(dp[u]==Integer.MIN_VALUE) continue;
            for(Edge e: g.adj.get(u)){
                metrics.incOps(); // проверка обновления dp
                if(dp[u]+e.weight > dp[e.to]){
                    dp[e.to] = dp[u]+e.weight;
                    prev[e.to] = u;
                    metrics.incOps(); // обновление dp
                }
            }
        }
        metrics.stop();

        int best=-1, node=-1;
        for(int i=0;i<n;i++){
            if(dp[i]>best){
                best=dp[i];
                node=i;
            }
        }

        List<Integer> path = new ArrayList<>();
        for(int v=node; v!=-1; v=prev[v]) path.add(v);
        Collections.reverse(path);

        Result r = new Result();
        r.length = best;
        r.path = path;
        return r;
    }
}




/**package graph.dagsp;

import graph.Graph;
import graph.Edge;
import java.util.*;

public class LongestPathDAG {

    public static class Result {
        public int length;
        public List<Integer> path;
    }

    public static Result longestPath(Graph g, List<Integer> topo, int start){
        int n = g.n;
        int[] dp = new int[n];
        int[] prev = new int[n];
        Arrays.fill(dp, Integer.MIN_VALUE);
        Arrays.fill(prev, -1);
        dp[start]=0;

        for(int u: topo){
            if(dp[u]==Integer.MIN_VALUE) continue;
            for(Edge e: g.adj.get(u)){
                if(dp[u]+e.weight>dp[e.to]){
                    dp[e.to] = dp[u]+e.weight;
                    prev[e.to] = u;
                }
            }
        }

        int best=-1, node=-1;
        for(int i=0;i<n;i++){
            if(dp[i]>best){
                best=dp[i];
                node=i;
            }
        }

        List<Integer> path=new ArrayList<>();
        for(int v=node; v!=-1; v=prev[v]) path.add(v);
        Collections.reverse(path);

        Result r=new Result();
        r.length=best;
        r.path=path;
        return r;
    }
}*/
