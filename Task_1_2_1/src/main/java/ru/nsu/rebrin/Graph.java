package ru.nsu.rebrin;

import java.io.IOException;
import java.util.List;

public interface Graph {
    /**
     * Add v.
     */
    void addVertex();

    /**
     * Remove v.
     * @param vertex - v
     */
    void removeVertex(int vertex);

    /**
     * Add e.
     * @param from - from
     * @param to - to
     */
    void addEdge(int from, int to);

    /**
     * Remove e.
     * @param from - from
     * @param to - to
     */
    void removeEdge(int from, int to);

    /**
     * Get neighbors.
     * @param vertex - v
     * @return - neighbors
     */
    List<Integer> getNeighbors(int vertex);

    /**
     * Read from file.
     * @param filename - file name
     * @throws IOException - Exception
     */
    void readFromFile(String filename) throws IOException;

    /**
     * To string.
     * @return - string
     */
    String toString();

    /**
     * Equals.
     * @param obj - obj
     * @return - eq
     */
    boolean equals(Object obj);

    /**
     * E count.
     * @return - count
     */
    int eCount();

    /**
     * V count.
     * @return - count
     */
    int vCount();

    /**
     * Toposort.
     * @return - List
     */
    List<Integer> topologicalSort();

    /**
     * Helper.
     * @return - edges
     */
    List<List<Integer>> getEdges();
}
