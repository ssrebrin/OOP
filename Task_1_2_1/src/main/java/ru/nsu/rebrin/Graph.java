package ru.nsu.rebrin;

import java.io.IOException;
import java.util.List;

/**
 * Graph.
 */
public interface Graph {
    /**
     * Add v.
     */
    void add_vertex();

    /**
     * Remove v.
     *
     * @param vertex - v
     */
    void remove_vertex(int vertex);

    /**
     * Add e.
     *
     * @param from - from
     * @param to - to
     */
    void add_edge(int from, int to);

    /**
     * Remove e.
     *
     * @param from - from
     * @param to - to
     */
    void remove_edge(int from, int to);

    /**
     * Get neighbors.
     *
     * @param vertex - v
     * @return - neighbors
     */
    List<Integer> get_neighbors(int vertex);

    /**
     * Read from file.
     *
     * @param filename - file name
     * @throws IOException - Exception
     */
    void read_from_file(String filename) throws IOException;

    /**
     * Equals.
     *
     * @param obj - obj
     * @return - eq
     */
    boolean equals(Object obj);

    /**
     * E count.
     *
     * @return - count
     */
    int ecount();

    /**
     * V count.
     *
     * @return - count
     */
    int vcount();

    /**
     * Toposort.
     *
     * @return - List
     */
    List<Integer> topological_sort();

    /**
     * Helper.
     *
     * @return - edges
     */
    List<List<Integer>> get_edges();

    /**
     * To String.
     *
     * @return - string
     */
    String to_string();
}
