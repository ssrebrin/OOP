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
 * List of adj.
 */
public class ListOfAdjacency implements Graph {
    List<List<Integer>> gr = new ArrayList<>();

    /**
     * List.
     *
     * @param matrix - matrix
     */
    public ListOfAdjacency(int[][] matrix) {
        if (matrix == null) {
            gr = new ArrayList<>();
            return;
        }
        gr = new ArrayList<>();
        for (int[] ints : matrix) {
            if (ints.length == 0) {
                gr.add(new ArrayList<>());
            } else {
                List<Integer> row = new ArrayList<>();
                for (int j = 0; j < matrix[0].length; j++) {
                    row.add(ints[j]);
                }
                gr.add(row);
            }
        }
    }

    /**
     * add v.
     */
    @Override
    public void add_vertex() {
        gr.add(new ArrayList<>());
    }

    /**
     * renove v.
     *
     * @param vertex - v
     */
    @Override
    public void remove_vertex(int vertex) {
        if (vertex >= gr.size()) {
            throw new IndexOutOfBoundsException("Vertex index out of bounds: " + vertex);
        }
        for (List<Integer> list : gr) {
            list.removeIf(v -> v == vertex);
        }
        // Удаляем саму вершину
        gr.remove(vertex);

        // Корректируем индексы, если нужно
        for (List<Integer> list : gr) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) > vertex) {
                    list.set(i, list.get(i) - 1);
                }
            }
        }
    }

    /**
     * add e.
     *
     * @param from - from
     * @param to   - to
     */
    @Override
    public void add_edge(int from, int to) {
        if (from < gr.size() && to < gr.size()) {
            gr.get(from).add(to);
        }
    }

    /**
     * remove e.
     *
     * @param from - from
     * @param to   - to
     */
    @Override
    public void remove_edge(int from, int to) {
        if (from < gr.size()) {
            gr.get(from).removeIf(v -> v == to);
        }
    }

    /**
     * Neighbors.
     *
     * @param vertex - v
     * @return - n
     */
    @Override
    public List<Integer> get_neighbors(int vertex) {
        List<Integer> res = new ArrayList<>(gr.get(vertex)); // Соседи по исходящим рёбрам

        // Находим входящие рёбра
        for (int i = 0; i < gr.size(); i++) {
            if (i != vertex && gr.get(i).contains(vertex)) {
                res.add(i); // добавляем вершины, которые ведут к vertex
            }
        }
        return res;
    }

    /**
     * read file.
     *
     * @param filename - file name
     * @throws IOException - exception
     */
    @Override
    public void read_from_file(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String[] firstLine = br.readLine().split(" ");
            int vertexCount = Integer.parseInt(firstLine[0]);

            for (int i = 0; i < vertexCount; i++) {
                this.add_vertex();
            }

            int edgeCount = Integer.parseInt(firstLine[1]);
            for (int i = 0; i < edgeCount; i++) {
                String[] edge = br.readLine().split(" ");
                this.add_edge(Integer.parseInt(edge[0]), Integer.parseInt(edge[1]));
            }
        }
    }


    /**
     * To string.
     *
     * @return string.
     */
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        int ii = 0;
        for (List<Integer> row : gr) {
            res.append(ii++).append(": ");
            for (Integer i : row) {
                res.append(i);
                res.append(' ');
            }
            res.append('\n');
        }
        return res.toString();
    }

    /**
     * e count.
     *
     * @return - e
     */
    @Override
    public int ecount() {
        int edgeCount = 0;
        for (List<Integer> list : gr) {
            edgeCount += list.size();
        }
        return edgeCount;
    }

    /**
     * v count.
     *
     * @return - v
     */
    @Override
    public int vcount() {
        return gr.size();
    }

    /**
     * get edges.
     *
     * @return - list of edges
     */
    @Override
    public List<List<Integer>> get_edges() {
        List<List<Integer>> edges = new ArrayList<>();
        for (int i = 0; i < gr.size(); i++) {
            for (int to : gr.get(i)) {
                edges.add(List.of(i, to));
            }
        }
        return edges;
    }

    /**
     * eq.
     *
     * @param obj - obj
     * @return - equals
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Graph)) {
            return false;
        }

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
     * Hashcode.
     *
     * @return - code
     */
    @Override
    public int hashCode() {
        return get_edges().hashCode();
    }

    /**
     * toposort.
     *
     * @return - sorted graph
     */
    @Override
    public List<Integer> topological_sort() {
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[vcount()];
        boolean[] recStack = new boolean[vcount()]; // Для обнаружения циклов

        // Для каждой вершины запускаем сортировку
        for (int i = 0; i < vcount(); i++) {
            if (!visited[i]) {
                try {
                    topo(i, visited, recStack, stack);
                } catch (IllegalStateException e) {
                    return Collections.emptyList(); // Возвращаем пустой список
                }
            }
        }

        List<Integer> result = new ArrayList<>();
        while (!stack.isEmpty()) {
            result.add(stack.pop()); // Извлекаем вершины в порядке топологической сортировки
        }
        return result;
    }

    /**
     * Helper.
     *
     * @param vertex   - v
     * @param visited  -vis
     * @param recStack - s
     * @param stack    - s
     */
    private void topo(int vertex, boolean[] visited, boolean[] recStack, Stack<Integer> stack) {
        visited[vertex] = true;
        recStack[vertex] = true; // Помечаем текущую вершину в рекурсивном стеке

        // Проходим по всем соседям текущей вершины
        for (int neighbor : gr.get(vertex)) { // gr — это список смежности
            if (!visited[neighbor]) {
                topo(neighbor, visited, recStack, stack);
            } else if (recStack[neighbor]) {
                throw new IllegalStateException("Цикл обнаружен"); // Обнаружен цикл
            }
        }

        recStack[vertex] = false; // Убираем вершину из рекурсивного стека
        stack.push(vertex); // Добавляем вершину в стек результата
    }

}
