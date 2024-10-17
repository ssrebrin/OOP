package ru.nsu.rebrin;

import java.io.IOException;
import java.util.List;

public interface Graph {
    void addVertex();
    void removeVertex(int vertex);
    void addEdge(int from, int to);
    void removeEdge(int from, int to);
    List<Integer> getNeighbors(int vertex);
    void readFromFile(String filename) throws IOException;
    String toString();
    boolean equals(Object obj);
    int eCount();
    int vCount();
    public List<Integer> topologicalSort();
    List<List<Integer>> getEdges();
}
