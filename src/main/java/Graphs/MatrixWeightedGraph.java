package Graphs;

import java.util.Iterator;

public class MatrixWeightedGraph<T> extends MatrixDiGraph<T> implements NetworkADT<T>{
    @Override
    public void addEdge(T vertex1, T vertex2, double weight) {
        matrix[getIndex(vertex1)][getIndex(vertex2)] = weight;
        matrix[getIndex(vertex2)][getIndex(vertex1)] = weight;
        numOfEdges++;
    }

    @Override
    public double shortestPathWeight(T vertex1, T vertex2) {
        double[] distanceArray = Utils.dijkstra(matrix,getIndex(vertex1), size(), new int[size()]);
        return distanceArray[getIndex(vertex2)];

    }

}
