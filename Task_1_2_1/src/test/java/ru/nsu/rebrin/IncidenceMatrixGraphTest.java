package ru.nsu.rebrin;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        graph.addVertex();
        graph.addEdge(2, 5);
        graph.addEdge(4, 5);
        graph.addEdge(5, 1);
        graph.addEdge(5, 5);
        graph.removeEdge(5, 5);

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
        graph.addVertex();
        graph.addVertex();

        assertEquals(2, graph.vCount());
    }

    @Test
    void testAddEdge() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph(null);
        graph.addVertex();
        graph.addVertex();
        graph.addEdge(0, 1);

        assertEquals(1, graph.eCount());
        // Проверяем значения в матрице инцидентности
        assertEquals(1, graph.incidenceMatrix.get(0).get(0)); // Из вершины 0
        assertEquals(-1, graph.incidenceMatrix.get(1).get(0)); // В вершину 1
    }

    @Test
    void testRemoveEdge() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph(null);
        graph.addVertex();
        graph.addVertex();
        graph.addEdge(0, 1);
        graph.removeEdge(0, 1);

        assertEquals(0, graph.eCount());
        assertTrue(graph.incidenceMatrix.get(0).isEmpty());
        assertTrue(graph.incidenceMatrix.get(1).isEmpty());
    }

    @Test
    void testRemoveVertex() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph(null);
        graph.addVertex(); // 0
        graph.addVertex(); // 1
        graph.addVertex(); // 2
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);

        graph.removeVertex(1);

        assertEquals(2, graph.vCount());
        assertEquals(0, graph.eCount());
    }

    @Test
    void testGetNeighbors() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph(null);
        graph.addVertex(); // 0
        graph.addVertex(); // 1
        graph.addVertex(); // 2
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 0);

        List<Integer> neighborsOf0 = graph.getNeighbors(0);
        assertTrue(neighborsOf0.contains(1));
        assertTrue(neighborsOf0.contains(2));

        List<Integer> neighborsOf1 = graph.getNeighbors(1);
        assertTrue(neighborsOf1.contains(0));
        assertTrue(neighborsOf1.contains(2));
    }

    @Test
    void testTopologicalSort() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph(null);
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
                {1, 0, 0},
                {-1, 1, 0},
                {0, -1, 1},
                {0, 0, -1}
        };

        IncidenceMatrixGraph graph = new IncidenceMatrixGraph(cyclicGraphMatrix);
        graph.addEdge(3, 1); // Introduce a cycle: 3 -> 1

        List<Integer> sorted = graph.topologicalSort();

        assertTrue(sorted.isEmpty());
    }

    @Test
    void testGetEdges() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph(null);
        graph.addVertex(); // 0
        graph.addVertex(); // 1
        graph.addEdge(0, 1);

        List<List<Integer>> edges = graph.getEdges();
        assertEquals(1, edges.size());
        assertEquals(List.of(0, 1), edges.get(0));
    }

    @Test
    void testFile() throws IOException {
        int[][] m = {{1, 0, 0},
                {-1, 1, 0},
                {0, -1, 1},
                {0, 0, -1}};
        IncidenceMatrixGraph G = new IncidenceMatrixGraph(m);
        IncidenceMatrixGraph G1 = new IncidenceMatrixGraph(null);

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("Test.txt")).getFile());

        G1.readFromFile(file.getAbsolutePath());
        assertTrue(G1.equals(G));
    }

}