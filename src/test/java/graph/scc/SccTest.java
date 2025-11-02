package graph.scc;

import graph.Graph;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class SCCTest {

    @Test
    public void testSimpleSCC(){
        Graph g = new Graph(3);
        g.addEdge(0,1,1);
        g.addEdge(1,2,1);
        g.addEdge(2,0,1);

        TarjanSCC scc = new TarjanSCC(g);
        List<List<Integer>> res = scc.findSCCs();
        assertEquals(1, res.size());
        assertTrue(res.get(0).containsAll(List.of(0,1,2)));
    }
}
