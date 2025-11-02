package graph.topo;

import graph.Graph;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TopoTest {

    @Test
    public void testTopoSimple(){
        Graph g = new Graph(3);
        g.addEdge(0,1,1);
        g.addEdge(1,2,1);
        List<Integer> order = KahnTopoSort.sort(g);
        assertEquals(List.of(0,1,2), order);
    }
}
