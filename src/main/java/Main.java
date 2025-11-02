import graph.Graph;
import graph.scc.TarjanSCC;
import graph.topo.TopologicalSortKahn;
import graph.dagsp.DAGSP;
import graph.dagsp.LongestPathDAG;
import data.DataGenerator;
import metrics.SimpleMetrics;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Path;
import java.nio.file.Files;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        DataGenerator.generateAll();
        System.out.println("Generated 9 JSON files in data/generated/");

        String[] files = {
                "input_small1.json","input_small2.json","input_small3.json",
                "input_medium1.json","input_medium2.json","input_medium3.json",
                "input_large1.json","input_large2.json","input_large3.json"
        };

        for (String fn : files) {
            String filename = "data/generated/" + fn;
            System.out.println("\n==== Processing " + filename + " ====");
            String content = Files.readString(Path.of(filename));
            JSONObject obj = new JSONObject(content);

            int n = obj.getInt("n");
            int source = obj.getInt("source");

            Graph g = new Graph(n);
            JSONArray edges = obj.getJSONArray("edges");
            for (int j = 0; j < edges.length(); j++) {
                JSONObject e = edges.getJSONObject(j);
                g.addEdge(e.getInt("from"), e.getInt("to"), e.getInt("weight"));
            }

            //Tarjan SCC
            SimpleMetrics sccMetrics = new SimpleMetrics();
            TarjanSCC sccAlg = new TarjanSCC(g, sccMetrics);
            List<List<Integer>> sccs = sccAlg.findSCCs();
            Graph dag = sccAlg.condense();

            System.out.println("SCCs found: " + sccs.size());
            System.out.println("SCC operations: " + sccMetrics.getOps());
            System.out.printf("SCC time (ms): %.3f%n", sccMetrics.getTimeNs()/1_000_000.0);

            //Topological Sort
            SimpleMetrics topoMetrics = new SimpleMetrics();
            List<Integer> topoOrder = TopologicalSortKahn.sort(dag, topoMetrics);

            System.out.println("Topo order of SCCs: " + topoOrder);
            System.out.println("Topo operations: " + topoMetrics.getOps());
            System.out.printf("Topo time (ms): %.3f%n", topoMetrics.getTimeNs()/1_000_000.0);

            //Shortest Paths
            SimpleMetrics spMetrics = new SimpleMetrics();
            int[] dist = DAGSP.shortestPath(dag, topoOrder, 0, spMetrics);

            System.out.print("Shortest distances from source 0: ");
            for (int d : dist) System.out.print(d + " ");
            System.out.println();
            System.out.println("SP operations: " + spMetrics.getOps());
            System.out.printf("SP time (ms): %.3f%n", spMetrics.getTimeNs()/1_000_000.0);

            //Longest Path
            SimpleMetrics lpMetrics = new SimpleMetrics();
            LongestPathDAG.Result lp = LongestPathDAG.longestPath(dag, topoOrder, 0, lpMetrics);

            System.out.println("Longest path length: " + lp.length);
            System.out.println("Longest path: " + lp.path);
            System.out.println("LP operations: " + lpMetrics.getOps());
            System.out.printf("LP time (ms): %.3f%n", lpMetrics.getTimeNs()/1_000_000.0);
        }

        System.out.println("\nAll 9 graphs processed!");
    }
}




