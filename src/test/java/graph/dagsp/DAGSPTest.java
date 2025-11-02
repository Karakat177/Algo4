package graph.dagsp;

import graph.Graph;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class DAGSPTest {

    @Test
    public void testShortestAndLongest(){
        Graph g = new Graph(3);
        g.addEdge(0,1,2);
        g.addEdge(1,2,3);

        List<Integer> topo = List.of(0,1,2);

        int[] dist = DAGSP.shortestPath(g, topo, 0);
        assertArrayEquals(new int[]{0,2,5}, dist);

        LongestPathDAG.Result lp = LongestPathDAG.longestPath(g, topo,0);
        assertEquals(5, lp.length);
        assertEquals(List.of(0,1,2), lp.path);
    }
}
