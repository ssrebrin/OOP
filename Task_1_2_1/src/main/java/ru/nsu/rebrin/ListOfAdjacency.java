package ru.nsu.rebrin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

/**
 * List of adj.
 */
public class ListOfAdjacency implements Graph {
    List<List<Integer>> Gr = new ArrayList<>();

    /**
     * List.
     * @param matrix - matrix
     */
    public ListOfAdjacency(int[][] matrix) {
        if (matrix == null) {
            Gr = new ArrayList<>();
            return;
        }
        Gr = new ArrayList<>();
        for (int[] ints : matrix) {
            if(ints.length == 0){
                Gr.add(new ArrayList<>());
            }
            else {
                List<Integer> row = new ArrayList<>();
                for (int j = 0; j < matrix[0].length; j++) {
                    row.add(ints[j]);
                }
                Gr.add(row);
            }
        }
    }

    /**
     * add v
     */
    @Override
    public void addVertex() {
        Gr.add(new ArrayList<>());
    }

    /**
     * renove v.
     * @param vertex - v
     */
    @Override
    public void removeVertex(int vertex) {
        // Удаляем все рёбра, которые ссылаются на удаляемую вершину
        for (List<Integer> list : Gr) {
            list.removeIf(v -> v == vertex);
        }
        // Удаляем саму вершину
        Gr.remove(vertex);

        // Корректируем индексы, если нужно
        for (List<Integer> list : Gr) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) > vertex) {
                    list.set(i, list.get(i) - 1);
                }
            }
        }
    }

    /**
     * add e.
     * @param from - from
     * @param to - to
     */
    @Override
    public void addEdge(int from, int to) {
        if (from < Gr.size() && to < Gr.size()) {
            Gr.get(from).add(to);
        }
    }

    /**
     * remove e.
     * @param from - from
     * @param to - to
     */
    @Override
    public void removeEdge(int from, int to) {
        if (from < Gr.size()) {
            Gr.get(from).removeIf(v -> v == to);
        }
    }

    /**
     * Neighbors.
     * @param vertex - v
     * @return - n
     */
    @Override
    public List<Integer> getNeighbors(int vertex) {
        List<Integer> res = new ArrayList<>(Gr.get(vertex)); // Соседи по исходящим рёбрам

        // Находим входящие рёбра
        for (int i = 0; i < Gr.size(); i++) {
            if (i != vertex && Gr.get(i).contains(vertex)) {
                res.add(i); // добавляем вершины, которые ведут к vertex
            }
        }
        return res;
    }

    /**
     * read file.
     * @param filename - file name
     * @throws IOException - exception
     */
    @Override
    public void readFromFile(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String[] firstLine = br.readLine().split(" ");
            int vertexCount = Integer.parseInt(firstLine[0]);

            for (int i = 0; i < vertexCount; i++) {
                this.addVertex();
            }

            int edgeCount = Integer.parseInt(firstLine[1]);
            for (int i = 0; i < edgeCount; i++) {
                String[] edge = br.readLine().split(" ");
                this.addEdge(Integer.parseInt(edge[0]), Integer.parseInt(edge[1]));
            }
        }
    }


    /**
     * e count.
     * @return - e
     */
    @Override
    public int eCount() {
        int edgeCount = 0;
        for (List<Integer> list : Gr) {
            edgeCount += list.size();
        }
        return edgeCount;
    }

    /**
     * v count.
     * @return - v
     */
    @Override
    public int vCount() {
        return Gr.size();
    }

    /**
     * get edges.
     * @return - list of edges
     */
    @Override
    public List<List<Integer>> getEdges() {
        List<List<Integer>> edges = new ArrayList<>();
        for (int i = 0; i < Gr.size(); i++) {
            for (int to : Gr.get(i)) {
                edges.add(List.of(i, to));
            }
        }
        return edges;
    }

    /**
     * eq.
     * @param obj - obj
     * @return - equals
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Graph)) return false;

        Graph other = (Graph) obj;

        // Проверка на одинаковое количество вершин и рёбер
        if (this.vCount() != other.vCount()) return false;
        if (this.eCount() != other.eCount()) return false;

        List<List<Integer>> g1 = other.getEdges();
        List<List<Integer>> g2 = this.getEdges();

        g1.sort(Comparator.comparing((List<Integer> e) -> e.get(0)).thenComparing(e -> e.get(1)));
        g2.sort(Comparator.comparing((List<Integer> e) -> e.get(0)).thenComparing(e -> e.get(1)));

        return g1.equals(g2);
    }

    /**
     * toposort.
     * @return - sorted graph
     */
    @Override
    public List<Integer> topologicalSort() {
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[vCount()];
        boolean[] recStack = new boolean[vCount()]; // Для обнаружения циклов

        // Для каждой вершины запускаем сортировку
        for (int i = 0; i < vCount(); i++) {
            if (!visited[i]) {
                try {
                    topo(i, visited, recStack, stack);
                } catch (IllegalStateException e) {
                    return Collections.emptyList(); // Возвращаем пустой список при обнаружении цикла
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
     * @param vertex - v
     * @param visited -vis
     * @param recStack - s
     * @param stack - s
     */
    private void topo(int vertex, boolean[] visited, boolean[] recStack, Stack<Integer> stack) {
        visited[vertex] = true;
        recStack[vertex] = true; // Помечаем текущую вершину в рекурсивном стеке

        // Проходим по всем соседям текущей вершины
        for (int neighbor : Gr.get(vertex)) { // Gr — это список смежности
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
