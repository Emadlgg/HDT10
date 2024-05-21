package grafo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;

class GraphTest {
    Graph graph;

    @BeforeEach
    void setUp() throws IOException {
        graph = new Graph("guategrafo.txt");
    }

    @Test
    void testFloydWarshall() {
        graph.floydWarshall();
        setUp(30, graph.getAdjMatrix()[0][1]); // Mixco to Antigua
        setUp(55, graph.getAdjMatrix()[0][2]); // Mixco to Escuintla
    }

    @Test
    void testUpdateGraph() {
        graph.updateGraph("Mixco", "Antigua", 20);
        setUp(20, graph.getAdjMatrix()[0][1]); // Mixco to Antigua updated
    }

    @Test
    void testRemoveEdge() {
        graph.removeEdge("Mixco", "Antigua");
        setUp(Integer.MAX_VALUE / 2, graph.getAdjMatrix()[0][1]); // Mixco to Antigua removed
    }
}
