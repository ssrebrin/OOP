package ru.nsu.rebrin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdjecencyMatrixGraphTest {
    @Test
    void testAddVertex() {
        AdjecencyMatrixGraph g1 = new AdjecencyMatrixGraph(null);
        AdjecencyMatrixGraph g2 = new AdjecencyMatrixGraph(null);
        g1.addVertex();
        g1.addVertex();
        g2.addVertex();
        g2.addVertex();
        g1.addEdge(1,0);
        g2.addEdge(1,0);
        assertTrue(g1.equals(g2));
    }
}