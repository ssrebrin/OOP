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
        graph.add_vertex();
        graph.add_vertex();
        assertEquals(2, graph.vcount());
    }

    @Test
    void testRemoveVertex() {
        ListOfAdjacency graph = new ListOfAdjacency(null);
        graph.add_vertex(); // 0
        graph.add_vertex(); // 1
        graph.add_vertex(); // 2
        graph.add_edge(0, 1);
        graph.add_edge(1, 2);
        graph.remove_vertex(1); // Remove vertex 1
        assertEquals(2, graph.vcount());
        assertEquals(List.of(), graph.get_neighbors(0));
        graph.add_edge(0, 1);
        assertEquals(List.of(1), graph.get_neighbors(0));
    }

    @Test
    void testAddEdge() {
        ListOfAdjacency graph = new ListOfAdjacency(null);
        graph.add_vertex(); // 0
        graph.add_vertex(); // 1
        graph.add_edge(0, 1);
        assertEquals(List.of(1), graph.get_neighbors(0));
    }

    @Test
    void testRemoveEdge() {
        ListOfAdjacency graph = new ListOfAdjacency(null);
        graph.add_vertex(); // 0
        graph.add_vertex(); // 1
        graph.add_edge(0, 1);
        graph.remove_edge(0, 1);
        assertEquals(0, graph.ecount());
    }

    @Test
    void testGetNeighbors() {
        ListOfAdjacency graph = new ListOfAdjacency(null);
        graph.add_vertex(); // 0
        graph.add_vertex(); // 1
        graph.add_vertex(); // 2
        graph.add_edge(0, 1);
        graph.add_edge(1, 2);
        List<Integer> neighbors = graph.get_neighbors(1);
        assertTrue(neighbors.contains(2));
        assertTrue(neighbors.contains(0));
    }

    @Test
    void testTopologicalSort() {
        ListOfAdjacency graph = new ListOfAdjacency(null);
        graph.add_vertex(); // 0
        graph.add_vertex(); // 1
        graph.add_vertex(); // 2
        graph.add_vertex(); // 3
        graph.add_edge(0, 1);
        graph.add_edge(1, 2);
        graph.add_edge(2, 3);
        List<Integer> sorted = graph.topological_sort();
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

        List<Integer> sorted = graph.topological_sort();

        assertTrue(sorted.isEmpty());
    }

    @Test
    void testEquals() {
        ListOfAdjacency graph1 = new ListOfAdjacency(null);
        graph1.add_vertex();
        graph1.add_vertex();
        graph1.add_edge(0, 1);

        ListOfAdjacency graph2 = new ListOfAdjacency(null);
        graph2.add_vertex();
        graph2.add_vertex();
        graph2.add_edge(0, 1);

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
        File file = new File(Objects.requireNonNull(
            classLoader.getResource("Test.txt")).getFile());

        graphF.read_from_file(file.getAbsolutePath());

        assertTrue(graph.equals(graphF));
    }
}
