package ru.nsu.rebrin;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        AdjecencyMatrixGraph g1 = new AdjecencyMatrixGraph(null);

        IncidenceMatrixGraph g2 = new IncidenceMatrixGraph(null);

        g1.addVertex();
        g2.addVertex();
        g1.addVertex();
        g2.addVertex();
        g1.addEdge(1,0);
        g1.addEdge(0,1);
        g2.addEdge(1,0);
        g2.addEdge(0,1);
        List<List<Integer>> a = g1.getEdges();
        List<List<Integer>> b = g2.getEdges();
        System.out.println(g1.equals(g2));
    }
}