package graph.scc;
import graph.Graph;
import metrics.SimpleMetrics;
import java.util.*;

public class TarjanSCC {
    private Graph g;
    private int[] ids, low;
    private boolean[] onStack;
    private Deque<Integer> stack;
    private int id;
    public List<List<Integer>> sccs;
    private SimpleMetrics metrics; // добавляем метрики

    public TarjanSCC(Graph g, SimpleMetrics metrics){
        this.g = g;
        this.metrics = metrics;
    }

    public List<List<Integer>> findSCCs(){
        int n = g.n;
        ids = new int[n];
        Arrays.fill(ids, -1);
        low = new int[n];
        onStack = new boolean[n];
        stack = new ArrayDeque<>();
        sccs = new ArrayList<>();
        id = 0;

        metrics.start(); // запускаем таймер
        for(int i=0; i<n; i++){
            if(ids[i]==-1) dfs(i);
        }
        metrics.stop(); // останавливаем таймер
        return sccs;
    }

    private void dfs(int u){
        ids[u] = low[u] = id++;
        stack.push(u);
        onStack[u] = true;
        metrics.incOps(); // посещение вершины

        for(var e: g.adj.get(u)){
            int v = e.to;
            metrics.incOps(); // проверка ребра
            if(ids[v]==-1){
                dfs(v);
                low[u] = Math.min(low[u], low[v]);
            } else if(onStack[v]){
                low[u] = Math.min(low[u], ids[v]);
            }
        }

        if(ids[u]==low[u]){
            List<Integer> component = new ArrayList<>();
            int node;
            do{
                node = stack.pop();
                onStack[node] = false;
                component.add(node);
                metrics.incOps(); // добавление в компонент
            } while(node!=u);
            sccs.add(component);
        }
    }

    public Graph condense(){
        List<Integer> compMap = buildCompMap();
        int compCount = sccs.size();
        Graph dag = new Graph(compCount);
        Set<String> added = new HashSet<>();

        for(int u=0; u<g.n; u++){
            for(var e: g.adj.get(u)){
                int v = e.to;
                if(compMap.get(u)!=compMap.get(v)){
                    String key = compMap.get(u)+"-"+compMap.get(v);
                    if(!added.contains(key)){
                        dag.addEdge(compMap.get(u), compMap.get(v), e.weight);
                        added.add(key);
                        metrics.incOps(); // добавление ребра в DAG
                    }
                }
            }
        }
        return dag;
    }

    private List<Integer> buildCompMap(){
        List<Integer> comp = new ArrayList<>(Collections.nCopies(g.n, -1));
        for(int i=0;i<sccs.size();i++){
            for(int node: sccs.get(i)){
                comp.set(node, i);
            }
        }
        return comp;
    }
}



/**package graph.scc;

import graph.Graph;
import java.util.*;

public class TarjanSCC {

    private Graph g;
    private int[] ids, low;
    private boolean[] onStack;
    private Deque<Integer> stack;
    private int id;
    public List<List<Integer>> sccs;

    public TarjanSCC(Graph g){
        this.g = g;
    }

    public List<List<Integer>> findSCCs(){
        int n = g.n;
        ids = new int[n];
        Arrays.fill(ids, -1);
        low = new int[n];
        onStack = new boolean[n];
        stack = new ArrayDeque<>();
        sccs = new ArrayList<>();
        id = 0;

        for(int i=0; i<n; i++){
            if(ids[i]==-1) dfs(i);
        }
        return sccs;
    }

    private void dfs(int u){
        ids[u] = low[u] = id++;
        stack.push(u);
        onStack[u] = true;

        for(var e: g.adj.get(u)){
            int v = e.to;
            if(ids[v]==-1){
                dfs(v);
                low[u] = Math.min(low[u], low[v]);
            } else if(onStack[v]){
                low[u] = Math.min(low[u], ids[v]);
            }
        }

        if(ids[u]==low[u]){
            List<Integer> component = new ArrayList<>();
            int node;
            do{
                node = stack.pop();
                onStack[node]=false;
                component.add(node);
            } while(node!=u);
            sccs.add(component);
        }
    }


    public Graph condense(){
        List<Integer> compMap = buildCompMap();
        int compCount = sccs.size();
        Graph dag = new Graph(compCount);
        Set<String> added = new HashSet<>();
        for(int u=0; u<g.n; u++){
            for(var e: g.adj.get(u)){
                int v = e.to;
                if(compMap.get(u)!=compMap.get(v)){
                    String key = compMap.get(u)+"-"+compMap.get(v);
                    if(!added.contains(key)){
                        dag.addEdge(compMap.get(u), compMap.get(v), e.weight);
                        added.add(key);
                    }
                }
            }
        }
        return dag;
    }

    private List<Integer> buildCompMap(){
        List<Integer> comp = new ArrayList<>(Collections.nCopies(g.n, -1));
        for(int i=0;i<sccs.size();i++){
            for(int node: sccs.get(i)){
                comp.set(node, i);
            }
        }
        return comp;
    }
}
*/