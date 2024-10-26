package ru.nsu.rebrin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IncidenceMatrixGraphTest {

    @Test
    void testAddVertexx() {
        int[][] initialIncidenceMatrix = {
            {1, 0, 0, 0, 0, 0, 0},
            {-1, 1, 0, 0, 0, 2, -1},
            {0, -1, 1, 1, 0, 0, 0},
            {0, 0, -1, 0, 1, 0, 0},
            {0, 0, 0, -1, -1, 0, 1}
        };

        IncidenceMatrixGraph graph = new IncidenceMatrixGraph(initialIncidenceMatrix);
        graph.add_vertex();
        graph.add_edge(2, 5);
        graph.add_edge(4, 5);
        graph.add_edge(5, 1);
        graph.add_edge(5, 5);
        graph.remove_edge(5, 5);

        int[][] expectedIncidenceMatrix = {
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {-1, 1, 0, 0, 0, 2, -1, 0, 0, -1},
            {0, -1, 1, 1, 0, 0, 0, 1, 0, 0},
            {0, 0, -1, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, -1, -1, 0, 1, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, -1, -1, 1}
        };

        assertEquals(expectedIncidenceMatrix.length, graph.incidenceMatrix.size());
        for (int i = 0; i < expectedIncidenceMatrix.length; i++) {
            List<Integer> row = graph.incidenceMatrix.get(i);
            assertEquals(expectedIncidenceMatrix[i].length, row.size());
            for (int j = 0; j < expectedIncidenceMatrix[i].length; j++) {
                assertEquals(expectedIncidenceMatrix[i][j], row.get(j));
            }
        }
    }

    @Test
    void testAddVertex() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph(null);
        graph.add_vertex();
        graph.add_vertex();

        assertEquals(2, graph.vcount());
    }

    @Test
    void testAddEdge() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph(null);
        graph.add_vertex();
        graph.add_vertex();
        graph.add_edge(0, 1);

        assertEquals(1, graph.ecount());
        // Проверяем значения в матрице инцидентности
        assertEquals(1, graph.incidenceMatrix.get(0).get(0)); // Из вершины 0
        assertEquals(-1, graph.incidenceMatrix.get(1).get(0)); // В вершину 1
    }

    @Test
    void testRemoveEdge() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph(null);
        graph.add_vertex();
        graph.add_vertex();
        graph.add_edge(0, 1);
        graph.remove_edge(0, 1);

        assertEquals(0, graph.ecount());
        assertTrue(graph.incidenceMatrix.get(0).isEmpty());
        assertTrue(graph.incidenceMatrix.get(1).isEmpty());
    }

    @Test
    void testRemoveVertex() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph(null);
        graph.add_vertex(); // 0
        graph.add_vertex(); // 1
        graph.add_vertex(); // 2
        graph.add_edge(0, 1);
        graph.add_edge(1, 2);

        graph.remove_vertex(1);

        assertEquals(2, graph.vcount());
        assertEquals(0, graph.ecount());
    }

    @Test
    void testGetNeighbors() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph(null);
        graph.add_vertex(); // 0
        graph.add_vertex(); // 1
        graph.add_vertex(); // 2
        graph.add_edge(0, 1);
        graph.add_edge(1, 2);
        graph.add_edge(2, 0);

        List<Integer> neighborsOf0 = graph.get_neighbors(0);
        assertTrue(neighborsOf0.contains(1));
        assertTrue(neighborsOf0.contains(2));

        List<Integer> neighborsOf1 = graph.get_neighbors(1);
        assertTrue(neighborsOf1.contains(0));
        assertTrue(neighborsOf1.contains(2));
    }

    @Test
    void testTopologicalSort() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph(null);
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
            {1, 0, 0},
            {-1, 1, 0},
            {0, -1, 1},
            {0, 0, -1}
        };

        IncidenceMatrixGraph graph = new IncidenceMatrixGraph(cyclicGraphMatrix);
        graph.add_edge(3, 1); // Introduce a cycle: 3 -> 1

        List<Integer> sorted = graph.topological_sort();

        assertTrue(sorted.isEmpty());
    }

    @Test
    void testGetEdges() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph(null);
        graph.add_vertex(); // 0
        graph.add_vertex(); // 1
        graph.add_edge(0, 1);

        List<List<Integer>> edges = graph.get_edges();
        assertEquals(1, edges.size());
        assertEquals(List.of(0, 1), edges.get(0));
    }

    @Test
    void testFile() throws IOException {
        int[][] m = {{1, 0, 0},
            {-1, 1, 0},
            {0, -1, 1},
            {0, 0, -1}};
        IncidenceMatrixGraph g = new IncidenceMatrixGraph(m);
        IncidenceMatrixGraph g1 = new IncidenceMatrixGraph(null);

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(
            classLoader.getResource("Test.txt")).getFile());

        g1.read_from_file(file.getAbsolutePath());
        assertTrue(g1.equals(g));
        assertEquals(g1.hashCode(),g.hashCode());
        assertEquals(g1.toString(), "1 0 0 \n-1 1 0 \n0 -1 1 \n0 0 -1 \n");
    }

}