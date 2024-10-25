package ru.nsu.rebrin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Test;


class AdjecencyMatrixGraphTest {

    @Test
    void testAddVertex() {
        // Create an empty graph
        AdjecencyMatrixGraph graph = new AdjecencyMatrixGraph(null);

        // Add vertices
        graph.add_vertex();
        graph.add_vertex();

        // The vertex count should be 2
        assertEquals(2, graph.vcount());
    }

    @Test
    void testAddEdge() {
        // Create a graph with a 2x2 matrix
        int[][] matrix = {
            {0, 0},
            {0, 0}
        };
        AdjecencyMatrixGraph graph = new AdjecencyMatrixGraph(matrix);

        // Add edge from vertex 0 to vertex 1
        graph.add_edge(0, 1);

        // Verify that the edge is added
        assertEquals(1, graph.adjacencyMatrix.get(0).get(1));
        assertEquals(1, graph.ecount());
    }

    @Test
    void testRemoveEdge() {
        // Create a graph with a 2x2 matrix
        int[][] matrix = {
            {0, 1},
            {0, 0}
        };
        AdjecencyMatrixGraph graph = new AdjecencyMatrixGraph(matrix);

        // Remove edge from vertex 0 to vertex 1
        graph.remove_edge(0, 1);

        // Verify that the edge is removed
        assertEquals(0, graph.adjacencyMatrix.get(0).get(1));
        assertEquals(0, graph.ecount());
    }

    @Test
    void testRemoveVertex() {
        // Create a graph with a 3x3 matrix
        int[][] matrix = {
            {0, 1, 0},
            {0, 0, 0},
            {1, 0, 0}
        };
        AdjecencyMatrixGraph graph = new AdjecencyMatrixGraph(matrix);

        // Remove vertex 1
        graph.remove_vertex(1);

        // Verify that vertex 1 is removed
        assertEquals(2, graph.vcount());
        assertEquals(0, graph.adjacencyMatrix.get(0).get(1));
    }

    @Test
    void testGetNeighbors() {
        // Create a graph with a 3x3 matrix
        int[][] matrix = {
            {0, 1, 0},
            {0, 0, 1},
            {0, 0, 0}
        };
        AdjecencyMatrixGraph graph = new AdjecencyMatrixGraph(matrix);

        // Get neighbors of vertex 0
        List<Integer> neighbors = graph.get_neighbors(0);

        // Vertex 0 has one neighbor: vertex 1
        assertEquals(1, neighbors.size());
        assertEquals(1, neighbors.get(0));

        // Get neighbors of vertex 1
        neighbors = graph.get_neighbors(1);

        // Vertex 1 has one neighbor: vertex 2
        assertEquals(1, neighbors.size());
        assertEquals(1, neighbors.get(0));
    }

    @Test
    void testTopologicalSort() {
        // Create a Directed Acyclic Graph (DAG)
        int[][] matrix = {
            {0, 1, 0, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 1},
            {0, 0, 0, 0}
        };
        AdjecencyMatrixGraph graph = new AdjecencyMatrixGraph(matrix);

        List<Integer> sorted = graph.topological_sort();

        assertEquals(List.of(0, 1, 2, 3), sorted);
    }

    @Test
    void testGraphEquality() {
        int[][] matrix1 = {
            {0, 1, 0},
            {0, 0, 1},
            {1, 0, 0}
        };
        AdjecencyMatrixGraph graph1 = new AdjecencyMatrixGraph(matrix1);

        int[][] matrix2 = {
            {0, 1, 0},
            {0, 0, 1},
            {1, 0, 0}
        };
        AdjecencyMatrixGraph graph2 = new AdjecencyMatrixGraph(matrix2);

        assertEquals(graph1, graph2);
    }

    @Test
    void testTopologicalSortWithCycle() {
        // Create a graph with a cycle
        int[][] cyclicGraphMatrix = {
            {0, 1, 0, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 1},
            {0, 1, 0, 0}
        };

        AdjecencyMatrixGraph graph = new AdjecencyMatrixGraph(cyclicGraphMatrix);

        List<Integer> sorted = graph.topological_sort();

        assertTrue(sorted.isEmpty());
    }

    @Test
    void testFile() throws IOException {
        int[][] matrix = {
            {0, 1, 0, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 1},
            {0, 0, 0, 0}};
        AdjecencyMatrixGraph graph = new AdjecencyMatrixGraph(matrix);
        AdjecencyMatrixGraph graphF = new AdjecencyMatrixGraph(null);

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(
                classLoader.getResource("Test.txt")).getFile());

        graphF.read_from_file(file.getAbsolutePath());

        assertTrue(graph.equals(graphF));
        assertEquals(graphF.to_string(),"0 1 0 0 \n0 0 1 0 \n0 0 0 1 \n0 0 0 0 \n");
    }
}
