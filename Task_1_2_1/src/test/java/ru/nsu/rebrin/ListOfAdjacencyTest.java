package ru.nsu.rebrin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Test;

class ListOfAdjacencyTest {

    @Test
    void testAddVertex() {
        ListOfAdjacency graph = new ListOfAdjacency(null);
        graph.addVertex();
        graph.addVertex();
        assertEquals(2, graph.vCount());
    }

    @Test
    void testRemoveVertex() {
        ListOfAdjacency graph = new ListOfAdjacency(null);
        graph.addVertex(); // 0
        graph.addVertex(); // 1
        graph.addVertex(); // 2
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.removeVertex(1); // Remove vertex 1
        assertEquals(2, graph.vCount());
        assertEquals(List.of(), graph.getNeighbors(0)); // Vertex 0 should now point to 1 (originally 2)
        graph.addEdge(0, 1);
        assertEquals(List.of(1), graph.getNeighbors(0)); // Vertex 0 should now point to 1 (originally 2)
    }

    @Test
    void testAddEdge() {
        ListOfAdjacency graph = new ListOfAdjacency(null);
        graph.addVertex(); // 0
        graph.addVertex(); // 1
        graph.addEdge(0, 1);
        assertEquals(List.of(1), graph.getNeighbors(0));
    }

    @Test
    void testRemoveEdge() {
        ListOfAdjacency graph = new ListOfAdjacency(null);
        graph.addVertex(); // 0
        graph.addVertex(); // 1
        graph.addEdge(0, 1);
        graph.removeEdge(0, 1);
        assertEquals(0, graph.eCount());
    }

    @Test
    void testGetNeighbors() {
        ListOfAdjacency graph = new ListOfAdjacency(null);
        graph.addVertex(); // 0
        graph.addVertex(); // 1
        graph.addVertex(); // 2
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        List<Integer> neighbors = graph.getNeighbors(1);
        assertTrue(neighbors.contains(2));
        assertTrue(neighbors.contains(0));
    }

    @Test
    void testTopologicalSort() {
        ListOfAdjacency graph = new ListOfAdjacency(null);
        graph.addVertex(); // 0
        graph.addVertex(); // 1
        graph.addVertex(); // 2
        graph.addVertex(); // 3
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        List<Integer> sorted = graph.topologicalSort();
        assertEquals(List.of(0, 1, 2, 3), sorted);
    }

    @Test
    void testTopologicalSortWithCycle() {
        // Create a graph with a cycle
        int[][] cyclicGraphMatrix = {
                {1},
                {2},
                {3},
                {1}
        };

        AdjecencyMatrixGraph graph = new AdjecencyMatrixGraph(cyclicGraphMatrix);

        List<Integer> sorted = graph.topologicalSort();

        assertTrue(sorted.isEmpty());
    }

    @Test
    void testEquals() {
        ListOfAdjacency graph1 = new ListOfAdjacency(null);
        graph1.addVertex();
        graph1.addVertex();
        graph1.addEdge(0, 1);

        ListOfAdjacency graph2 = new ListOfAdjacency(null);
        graph2.addVertex();
        graph2.addVertex();
        graph2.addEdge(0, 1);

        assertTrue(graph1.equals(graph2));
    }

    @Test
    void testFile() throws IOException {
        int[][] matrix = {
                {1},
                {2},
                {3},
                {}};
        ListOfAdjacency graph = new ListOfAdjacency(matrix);
        ListOfAdjacency graphF = new ListOfAdjacency(null);

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("Test.txt")).getFile());

        graphF.readFromFile(file.getAbsolutePath());

        assertTrue(graph.equals(graphF));
    }
}
