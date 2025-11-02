package graph.scc;

import graph.Graph;
import org.junit.jupiter.api.Test;
import metrics.SimpleMetrics;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SccTest {
    @Test
    public void testSimpleSCC(){
        Graph g = new Graph(3);
        g.addEdge(0,1,1);
        g.addEdge(1,2,1);
        g.addEdge(2,0,1);

        SimpleMetrics metrics = new SimpleMetrics();
        TarjanSCC scc = new TarjanSCC(g, metrics);
        List<List<Integer>> res = scc.findSCCs();

        assertEquals(1, res.size());
        assertTrue(res.get(0).containsAll(List.of(0,1,2)));
        System.out.println("SCC ops: " + metrics.getOps());
        System.out.println("SCC time (ms): " + metrics.getTimeNs()/1_000_000.0);
    }
}
