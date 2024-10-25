package ru.nsu.rebrin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

/**
 * Adj matrix.
 */
public class AdjecencyMatrixGraph implements Graph {
    List<List<Integer>> adjacencyMatrix;
    int verCount;
    int edgCount = 0;

    /**
     * Matrix.
     *
     * @param matrix - m
     */
    public AdjecencyMatrixGraph(int[][] matrix) {
        verCount = 0;
        edgCount = 0;
        if (matrix == null) {
            adjacencyMatrix = new ArrayList<>();
            return;
        }
        adjacencyMatrix = new ArrayList<>();
        for (int[] ints : matrix) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < matrix[0].length; j++) {
                row.add(ints[j]);
                if (ints[j] >= 1) {
                    edgCount++;
                }
            }
            adjacencyMatrix.add(row);
        }
        verCount = adjacencyMatrix.get(0).size();
    }

    /**
     * add v.
     */
    @Override
    public void add_vertex() {
        for (List<Integer> row : adjacencyMatrix) {
            row.add(0);
        }
        List<Integer> newVer = new ArrayList<>();
        verCount++;
        for (int i = 0; i < verCount; i++) {
            newVer.add(0);
        }
        adjacencyMatrix.add(newVer);
    }

    /**
     * remove v.
     *
     * @param vertex - v
     */
    @Override
    public void remove_vertex(int vertex) {
        verCount--;
        for (List<Integer> row : adjacencyMatrix) {
            row.remove(vertex);
        }
        adjacencyMatrix.remove(vertex);
    }

    /**
     * Add e.
     *
     * @param from - from
     * @param to - to
     */
    @Override
    public void add_edge(int from, int to) {
        edgCount++;
        int currentCount = adjacencyMatrix.get(from).get(to);
        List<Integer> newRow = adjacencyMatrix.get(from);
        newRow.set(to, currentCount + 1);
        adjacencyMatrix.set(from, newRow);
    }

    /**
     * Remove e.
     *
     * @param from - from
     * @param to - to
     */
    @Override
    public void remove_edge(int from, int to) {
        edgCount--;
        int currentCount = adjacencyMatrix.get(from).get(to) - 1;
        if (currentCount < 0) {
            currentCount = 0;
        }
        List<Integer> newRow = adjacencyMatrix.get(from);
        newRow.set(to, currentCount);
        adjacencyMatrix.set(from, newRow);
    }

    /**
     * Get neighbors.
     *
     * @param vertex - v
     * @return - n
     */
    @Override
    public List<Integer> get_neighbors(int vertex) {
        List<Integer> neighbors = new ArrayList<>();
        for (int i = 0; i < adjacencyMatrix.get(vertex).size(); i++) {
            if (adjacencyMatrix.get(vertex).get(i) != 0 && i != vertex) {
                neighbors.add(adjacencyMatrix.get(vertex).get(i));
            }
        }
        Collections.sort(neighbors);
        for (int i = 0; i < adjacencyMatrix.size(); i++) {
            if (adjacencyMatrix.get(i).get(vertex) > 0
                    && Collections.binarySearch(neighbors, i) == 0 && i != vertex) {
                neighbors.add(i);
                Collections.sort(neighbors);
            }
        }

        return neighbors;
    }

    /**
     * Read file.
     *
     * @param filename - file name
     * @throws IOException - exception
     */
    @Override
    public void read_from_file(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String[] firstLine = br.readLine().split(" ");
            int vertexCount = Integer.parseInt(firstLine[0]);

            adjacencyMatrix = new ArrayList<>();
            for (int i = 0; i < vertexCount; i++) {
                this.add_vertex();
            }
            int edge = Integer.parseInt(firstLine[1]);
            String line;
            for (int i = 0; i < edge; i++) {
                line = br.readLine();
                String[] eedge = line.split(" ");
                this.add_edge(Integer.parseInt(eedge[0]), Integer.parseInt(eedge[1]));
            }
        }
    }

    /**
     * E count.
     *
     * @return - e
     */
    @Override
    public int ecount() {
        return edgCount;
    }

    /**
     * V count.
     *
     * @return - v
     */
    @Override
    public int vcount() {
        return verCount;
    }

    /**
     * Eq.
     *
     * @param obj - obj
     * @return - eq
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Graph)) {
            return false;
        }
        boolean flag1 = true;
        boolean flag2 = true;

        Graph other = (Graph) obj;

        // Проверка на одинаковое количество вершин и рёбер
        if (this.vcount() != other.vcount()) {
            return false;
        }
        if (this.ecount() != other.ecount()) {
            return false;
        }

        List<List<Integer>> g1 = other.get_edges();
        List<List<Integer>> g2 = this.get_edges();

        g1.sort(Comparator.comparing(
            (List<Integer> e) -> e.get(0)).thenComparing(e -> e.get(1)));
        g2.sort(Comparator.comparing(
            (List<Integer> e) -> e.get(0)).thenComparing(e -> e.get(1)));

        return g1.equals(g2);
    }

    /**
     * Get e.
     *
     * @return - list e
     */
    @Override
    public List<List<Integer>> get_edges() {
        List<List<Integer>> degrees = new ArrayList<>();
        int i = 0;
        for (List<Integer> row : adjacencyMatrix) {
            int ii = 0;
            for (Integer val : row) {
                for (int iii = 0; iii < val; iii++) {
                    List<Integer> newRow = new ArrayList<>();
                    newRow.add(i);
                    newRow.add(ii);
                    degrees.add(newRow);
                }
                ii++;
            }
            i++;
        }
        return degrees;
    }

    /**
     * To string.
     *
     * @return string.
     */
    @Override
    public String to_string() {
        StringBuilder res = new StringBuilder();
        for (List<Integer> row : adjacencyMatrix) {
            for (Integer i : row) {
                res.append(i);
                res.append(' ');
            }
            res.append('\n');
        }
        return res.toString();
    }


    /**
     * Toposort.
     *
     * @return - list
     */
    @Override
    public List<Integer> topological_sort() {
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[vcount()];
        boolean[] recStack = new boolean[vcount()]; // To detect cycles

        for (int i = 0; i < vcount(); i++) {
            if (!visited[i]) {
                try {
                    topo(i, visited, recStack, stack);
                } catch (IllegalStateException e) {
                    return Collections.emptyList();
                }
            }
        }

        List<Integer> result = new ArrayList<>();
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        return result;
    }

    /**
     * Helper.
     *
     * @param vertex - v
     * @param visited - v
     * @param recStack - s
     * @param stack - s
     */
    private void topo(int vertex, boolean[] visited, boolean[] recStack, Stack<Integer> stack) {
        visited[vertex] = true;
        recStack[vertex] = true; // Mark the current vertex in the recursion stack

        // Iterate over the neighbors using indices
        for (int i = 0; i < adjacencyMatrix.get(vertex).size(); i++) {
            if (adjacencyMatrix.get(vertex).get(i) == 1) {
                if (!visited[i]) {
                    topo(i, visited, recStack, stack);
                } else if (recStack[i]) {
                    throw new IllegalStateException("Cycle detected");
                }
            }
        }

        recStack[vertex] = false; // Remove the vertex from the recursion stack
        stack.push(vertex); // Add to the result stack
    }


}
