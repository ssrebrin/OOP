package ru.nsu.rebrin;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class IncidenceMatrixGraphTest {

    @Test
    void testAddVertex() {
        int[][] initialIncidenceMatrix = {
                {1,0,0,0,0,0,0},
                {-1,1,0,0,0,2,-1},
                {0,-1,1,1,0,0,0},
                {0,0,-1,0,1,0,0},
                {0,0,0,-1,-1,0,1}
        };

        IncidenceMatrixGraph graph = new IncidenceMatrixGraph(initialIncidenceMatrix);
        graph.addVertex();
        graph.addEdge(2,5);
        graph.addEdge(4,5);
        graph.addEdge(5,1);
        graph.addEdge(5,5);
        graph.removeEdge(5,5);

        int[][] expectedIncidenceMatrix = {
                {1,0,0,0,0,0,0,0,0,0},
                {-1,1,0,0,0,2,-1,0,0,-1},
                {0,-1,1,1,0,0,0,1,0,0},
                {0,0,-1,0,1,0,0,0,0,0},
                {0,0,0,-1,-1,0,1,0,1,0},
                {0,0,0,0,0,0,0,-1,-1,1}
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
}