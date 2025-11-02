package graph.dagsp;

import graph.Graph;
import org.junit.jupiter.api.Test;
import metrics.SimpleMetrics;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DAGSPTest {
    @Test
    public void testShortestAndLongest(){
        Graph g = new Graph(3);
        g.addEdge(0,1,2);
        g.addEdge(1,2,3);

        List<Integer> topo = List.of(0,1,2);

        SimpleMetrics spMetrics = new SimpleMetrics();
        int[] dist = DAGSP.shortestPath(g, topo, 0, spMetrics);
        assertArrayEquals(new int[]{0,2,5}, dist);
        System.out.println("SP ops: " + spMetrics.getOps());
        System.out.println("SP time (ms): " + spMetrics.getTimeNs()/1_000_000.0);

        SimpleMetrics lpMetrics = new SimpleMetrics();
        LongestPathDAG.Result lp = LongestPathDAG.longestPath(g, topo, 0, lpMetrics);
        assertEquals(5, lp.length);
        assertEquals(List.of(0,1,2), lp.path);
        System.out.println("LP ops: " + lpMetrics.getOps());
        System.out.println("LP time (ms): " + lpMetrics.getTimeNs()/1_000_000.0);
    }
}


