package ru.nsu.rebrin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AdjecencyMatrixGraph implements Graph {
    List<List<Integer>> adjacencyMatrix;
    int verCount;
    int edgCount = 0;

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

    @Override
    public void addVertex() {
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

    public void show() {
        for (List<Integer> row : adjacencyMatrix) {
            System.out.println(Arrays.toString(row.toArray()));
        }
    }

    @Override
    public void removeVertex(int vertex) {
        for (List<Integer> row : adjacencyMatrix) {
            row.remove(vertex);
        }
        adjacencyMatrix.remove(vertex);
    }

    @Override
    public void addEdge(int from, int to) {
        edgCount++;
        int currentCount = adjacencyMatrix.get(from).get(to);
        List<Integer> newRow = adjacencyMatrix.get(from);
        newRow.set(to, currentCount + 1);
        adjacencyMatrix.set(from, newRow);
    }


    @Override
    public void removeEdge(int from, int to) {
        int currentCount = adjacencyMatrix.get(from).get(to) - 1;
        if (currentCount < 0) {
            currentCount = 0;
        }
        List<Integer> newRow = adjacencyMatrix.get(from);
        newRow.set(to, currentCount);
        adjacencyMatrix.set(from, newRow);
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        List<Integer> neighbors = new ArrayList<>();
        for (int i = 0; i < adjacencyMatrix.get(vertex).size(); i++) {
            if (adjacencyMatrix.get(vertex).get(i) != 0 && i != vertex) {
                neighbors.add(adjacencyMatrix.get(vertex).get(i));
            }
        }
        Collections.sort(neighbors);
        for (int i = 0; i < adjacencyMatrix.size(); i++) {
            if (adjacencyMatrix.get(i).get(vertex) > 0 && Collections.binarySearch(neighbors, i) == 0 && i != vertex) {
                neighbors.add(i);
                Collections.sort(neighbors);
            }
        }

        return neighbors;
    }

    @Override
    public void readFromFile(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String[] firstLine = br.readLine().split(" ");
            int vertexCount = Integer.parseInt(firstLine[0]);

            adjacencyMatrix = new ArrayList<>();
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
        return edgCount;
    }

    @Override
    public int vCount() {
        return verCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Graph)) return false;
        boolean flag1 = true;
        boolean flag2 = true;

        Graph other = (Graph) obj;

        // Проверка на одинаковое количество вершин и рёбер
        if (this.vCount() != other.vCount()) return false;
        if (this.eCount() != other.eCount()) return false;

        List<List<Integer>> g1 = other.getEdges();
        List<List<Integer>> g2 = this.getEdges();

        for (int i = 0; i < g1.size(); i++) {
            if (!Objects.equals(g1.get(i).get(0), g2.get(i).get(0)) || !Objects.equals(g1.get(i).get(1), g2.get(i).get(1))) {
                flag1 = false;
                break;
            }
        }

        int s = g1.size() - 1;
        for (int i = 0; i < g1.size(); i++) {
            if (!Objects.equals(g1.get(s - i).get(0), g2.get(i).get(0)) || !Objects.equals(g1.get(s - i).get(1), g2.get(i).get(1))) {
                flag2 = false;
            }
        }

        return flag1 || flag2;
    }

    public List<List<Integer>> getEdges() {
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
}
