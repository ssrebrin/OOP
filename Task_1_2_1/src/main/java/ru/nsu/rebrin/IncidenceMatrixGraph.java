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
 * Inc matrix.
 */
public class IncidenceMatrixGraph implements Graph {
    List<List<Integer>> incidenceMatrix;
    int edgeCount = 0;

    /**
     * Matrix.
     *
     * @param incidenceVer - array
     */
    public IncidenceMatrixGraph(int[][] incidenceVer) {
        if (incidenceVer == null) {
            edgeCount = 0;
            incidenceMatrix = new ArrayList<>();
            return;
        }
        incidenceMatrix = new ArrayList<>();
        for (int[] ints : incidenceVer) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < incidenceVer[0].length; j++) {
                row.add(ints[j]);
            }
            incidenceMatrix.add(row);
        }
        edgeCount = incidenceMatrix.get(0).size();
    }

    /**
     * add v.
     */
    @Override
    public void add_vertex() {
        List<Integer> newVer = new ArrayList<>();
        for (int i = 0; i < edgeCount; i++) {
            newVer.add(0);
        }
        incidenceMatrix.add(newVer);
    }

    /**
     * remove.
     *
     * @param vertex - v
     */
    @Override
    public void remove_vertex(int vertex) {
        for (int i = 0; i < edgeCount; i++) {
            if (incidenceMatrix.get(vertex).get(i) != 0) {
                if (incidenceMatrix.get(vertex).get(i) == 2) {
                    this.remove_edge(vertex, vertex);
                } else {
                    for (int j = 0; j < incidenceMatrix.size(); j++) {
                        if (incidenceMatrix.get(j).get(i) != 0 && j != vertex) {
                            if (incidenceMatrix.get(vertex).get(i) == 1) {
                                this.remove_edge(vertex, j);
                            } else {
                                this.remove_edge(j, vertex);
                            }
                            i--;
                            break;
                        }
                    }
                }
            }
        }
        incidenceMatrix.remove(vertex);
    }

    /**
     * add e.
     *
     * @param from - from
     * @param to - to
     */
    @Override
    public void add_edge(int from, int to) {
        if (from == to) {
            incidenceMatrix.get(from).add(2);
        } else {
            incidenceMatrix.get(from).add(1);
            incidenceMatrix.get(to).add(-1);
        }
        int i = 0;
        for (List<Integer> row : incidenceMatrix) {
            if (!(i == from || i == to)) {
                row.add(0);
            }
            i++;
        }
        edgeCount++;
    }

    /**
     * remove e.
     *
     * @param from - from
     * @param to - to
     */
    @Override
    public void remove_edge(int from, int to) {

        for (int i = 0; i < edgeCount; i++) {
            if (incidenceMatrix.get(from).get(i) != 0 && incidenceMatrix.get(to).get(i) != 0
                    && ((from == to) == (incidenceMatrix.get(from).get(i) == 2))) {
                for (List<Integer> row : incidenceMatrix) {
                    row.remove(i);
                }
                i--;
                edgeCount--;
            }
        }
    }

    /**
     * get neighbors.
     *
     * @param vertex - v
     * @return - list
     */
    @Override
    public List<Integer> get_neighbors(int vertex) {
        List<Integer> neighbors = new ArrayList<>();
        for (int i = 0; i < edgeCount; i++) {
            if (incidenceMatrix.get(vertex).get(i) != 0
                    && incidenceMatrix.get(vertex).get(i) != 2) {
                int ii = 0;
                for (List<Integer> row : incidenceMatrix) {
                    if (row.get(i) != 0) {
                        neighbors.add(ii);
                    }
                    ii++;
                }

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

            incidenceMatrix = new ArrayList<>();
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
     * e count.
     *
     * @return e
     */
    @Override
    public int ecount() {
        return edgeCount;
    }

    /**
     * v count.
     *
     * @return - v
     */
    @Override
    public int vcount() {
        return incidenceMatrix.size();
    }

    /**
     * Get e.
     *
     * @return - e
     */
    @Override
    public List<List<Integer>> get_edges() {
        List<List<Integer>> edges = new ArrayList<>();

        // Итерируем по количеству ребер
        for (int i = 0; i < edgeCount; i++) {
            List<Integer> edge = new ArrayList<>();
            int fromVertex = -1;
            int toVertex = -1;

            // Итерируем по вершинам (строкам матрицы инцидентности)
            for (int j = 0; j < incidenceMatrix.size(); j++) {
                // Если значение -1, это исходящая вершина (from)
                if (incidenceMatrix.get(j).get(i) == 1) {
                    fromVertex = j;
                }
                // Если значение 1, это входящая вершина (to)
                if (incidenceMatrix.get(j).get(i) == -1) {
                    toVertex = j;
                }
                // Если значение 2, это петля
                if (incidenceMatrix.get(j).get(i) == 2) {
                    fromVertex = j;
                    toVertex = j;
                }
            }

            // Добавляем вершины как пару [from, to]
            edge.add(fromVertex);
            edge.add(toVertex);
            edges.add(edge);
        }

        return edges;
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
                    topological_sort_util(i, visited, recStack, stack);
                } catch (IllegalStateException e) {
                    return Collections.emptyList(); // Return an empty list in case of a cycle
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
     * @param visited - vis
     * @param recStack -stack1
     * @param stack - stack2
     */
    private void topological_sort_util(int vertex, boolean[] visited,
            boolean[] recStack, Stack<Integer> stack) {
        visited[vertex] = true;
        recStack[vertex] = true; // Mark the current node in the recursion stack

        for (int i = 0; i < edgeCount; i++) {
            int neighbor = -1;
            if (incidenceMatrix.get(vertex).get(i) == 1) {
                // Find the neighbor vertex
                for (int j = 0; j < vcount(); j++) {
                    if (incidenceMatrix.get(j).get(i) == -1) {
                        neighbor = j;
                        break;
                    }
                }
            }

            if (neighbor != -1) {
                if (!visited[neighbor]) {
                    topological_sort_util(neighbor, visited, recStack, stack);
                } else if (recStack[neighbor]) {
                    throw new IllegalStateException("Cycle detected");
                }
            }
        }

        recStack[vertex] = false; // Remove from recursion stack
        stack.push(vertex); // Add to the result stack
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
}
