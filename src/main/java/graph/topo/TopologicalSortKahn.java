package graph.topo;
import graph.Graph;
import metrics.SimpleMetrics;
import java.util.*;

public class TopologicalSortKahn {
    public static List<Integer> sort(Graph g, SimpleMetrics metrics){
        int n = g.n;
        int[] inDeg = new int[n];
        for(var edges: g.adj){
            for(var e: edges){
                inDeg[e.to]++;
                metrics.incOps(); // подсчёт входящей степени
            }
        }

        Deque<Integer> q = new ArrayDeque<>();
        for(int i=0;i<n;i++) if(inDeg[i]==0) q.add(i);

        List<Integer> order = new ArrayList<>();
        metrics.start();
        while(!q.isEmpty()){
            int u = q.poll();
            order.add(u);
            metrics.incOps(); // вершина добавлена в порядок

            for(var e: g.adj.get(u)){
                inDeg[e.to]--;
                metrics.incOps(); // обработка ребра
                if(inDeg[e.to]==0) q.add(e.to);
            }
        }
        metrics.stop();
        return order;
    }
}


/**package graph.topo;

import graph.Graph;
import java.util.*;

public class TopologicalSortKahn {

    public static List<Integer> sort(Graph g){
        int n = g.n;
        int[] inDeg = new int[n];
        for(var edges: g.adj){
            for(var e: edges) inDeg[e.to]++;
        }
        Deque<Integer> q = new ArrayDeque<>();
        for(int i=0;i<n;i++) if(inDeg[i]==0) q.add(i);

        List<Integer> order = new ArrayList<>();
        while(!q.isEmpty()){
            int u = q.poll();
            order.add(u);
            for(var e: g.adj.get(u)){
                inDeg[e.to]--;
                if(inDeg[e.to]==0) q.add(e.to);
            }
        }
        return order;
    }
}
*/