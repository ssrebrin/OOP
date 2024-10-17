package ru.nsu.rebrin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ListOfAdjacency implements Graph {
    List<List<Integer>> Gr = new ArrayList<>();

    @Override
    public void addVertex() {
        Gr.add(new ArrayList<>());
    }

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

    @Override
    public void addEdge(int from, int to) {
        if (from < Gr.size() && to < Gr.size()) {
            Gr.get(from).add(to);
        }
    }

    @Override
    public void removeEdge(int from, int to) {
        if (from < Gr.size()) {
            Gr.get(from).removeIf(v -> v == to);
        }
    }

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

    @Override
    public int eCount() {
        int edgeCount = 0;
        for (List<Integer> list : Gr) {
            edgeCount += list.size();
        }
        return edgeCount;
    }

    @Override
    public int vCount() {
        return Gr.size();
    }

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

    public List<Integer> topologicalSort() {
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[vCount()];

        for (int i = 0; i < vCount(); i++) {
            if (!visited[i]) {
                topo(i, visited, stack);
            }
        }

        List<Integer> result = new ArrayList<>();
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        return result;
    }

    private void topo(int vertex, boolean[] visited, Stack<Integer> stack) {
        visited[vertex] = true;

        for (int neighbor : Gr.get(vertex)) {
            if (!visited[neighbor]) {
                topo(neighbor, visited, stack);
            }
        }

        stack.push(vertex);
    }
}
