package graph.topo;

import graph.Graph;
import org.junit.jupiter.api.Test;
import metrics.SimpleMetrics;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TopoTest {
    @Test
    public void testTopoSimple(){
        Graph g = new Graph(3);
        g.addEdge(0,1,1);
        g.addEdge(1,2,1);

        SimpleMetrics metrics = new SimpleMetrics();
        List<Integer> order = TopologicalSortKahn.sort(g, metrics);

        assertEquals(List.of(0,1,2), order);
        System.out.println("Topo ops: " + metrics.getOps());
        System.out.println("Topo time (ms): " + metrics.getTimeNs()/1_000_000.0);
    }
}
