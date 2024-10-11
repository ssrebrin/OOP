package ru.nsu.rebrin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class IncidenceMatrixGraph implements Graph {
    List<List<Integer>> incidenceMatrix;
    int edgeCount = 0;

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

    public void show() {
        for (List<Integer> row : incidenceMatrix) {
            System.out.println(Arrays.toString(row.toArray()));
        }
    }

    @Override
    public void addVertex() {
        List<Integer> newVer = new ArrayList<>();
        for (int i = 0; i < edgeCount; i++) {
            newVer.add(0);
        }
        incidenceMatrix.add(newVer);
    }

    @Override
    public void removeVertex(int vertex) {
        for (int i = 0; i < edgeCount; i++) {
            if (incidenceMatrix.get(vertex).get(i) != 0) {
                if (incidenceMatrix.get(vertex).get(i) == 2) {
                    this.removeEdge(vertex, vertex);
                } else {
                    for (int j = 0; j < incidenceMatrix.size(); j++) {
                        if (incidenceMatrix.get(j).get(i) != 0) {
                            if (incidenceMatrix.get(vertex).get(i) == 1) {
                                this.removeEdge(vertex, j);
                                break;
                            } else {
                                this.removeEdge(j, vertex);
                                break;
                            }
                        }
                    }
                }
            }
        }
        incidenceMatrix.remove(vertex);
    }

    @Override
    public void addEdge(int from, int to) {
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

    @Override
    public void removeEdge(int from, int to) {

        for (int i = 0; i < edgeCount; i++) {
            if (incidenceMatrix.get(from).get(i) != 0 && incidenceMatrix.get(to).get(i) != 0 && ((from == to) == (incidenceMatrix.get(from).get(i) == 2))) {
                for (List<Integer> row : incidenceMatrix) {
                    row.remove(i);
                }
                i--;
                edgeCount--;
            }
        }
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        List<Integer> neighbors = new ArrayList<>();
        for (int i = 0; i < edgeCount; i++) {
            if (incidenceMatrix.get(vertex).get(i) != 0 && incidenceMatrix.get(vertex).get(i) != 2) {
                neighbors.add(incidenceMatrix.get(vertex).get(i));
            }
        }

        return neighbors;
    }

    @Override
    public void readFromFile(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String[] firstLine = br.readLine().split(" ");
            int vertexCount = Integer.parseInt(firstLine[0]);

            incidenceMatrix = new ArrayList<>();
            for (int i = 0; i < vertexCount; i++) {
                this.addVertex();
            }
            int edge = Integer.parseInt(firstLine[1]);
            String line;
            for (int i = 0; i < edge; i++) {
                line = br.readLine();
                String[] eedge = line.split(" ");
                this.addEdge(Integer.parseInt(eedge[0]), Integer.parseInt(eedge[1]));
            }
        }
    }

    @Override
    public int eCount() {
        return edgeCount;
    }

    @Override
    public int vCount() {
        return incidenceMatrix.size();
    }

    public List<List<Integer>> getEdges() {
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

}
