import graph.Graph;
import graph.scc.TarjanSCC;
import graph.scc.SCCCondensation;
import java.util.*;

public class MainRunner {
    public static void main(String[] args) {

        Graph g = new Graph(5);
        g.addEdge(0, 1, 1);
        g.addEdge(1, 2, 1);
        g.addEdge(2, 0, 1);
        g.addEdge(1, 3, 1);
        g.addEdge(3, 4, 1);

        TarjanSCC tarjan = new TarjanSCC();
        var sccs = tarjan.findSCC(g);

        System.out.println("SCC components:");
        System.out.println(sccs);

        SCCCondensation cond = new SCCCondensation();
        List<Integer> comp = cond.buildComponentMap(sccs, g.n);
        Graph dag = cond.buildCondensedGraph(g, comp, sccs.size());

        System.out.println("Condensed graph nodes = " + dag.n);
        for (int i = 0; i < dag.n; i++) {
            for (var e : dag.adj.get(i)) {
                System.out.println(i + " -> " + e.to);
            }
        }
    }
}
