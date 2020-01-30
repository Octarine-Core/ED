package Graphs;
import ListsAndIterators.ArraySet;

import java.awt.geom.QuadCurve2D;
import java.util.Iterator;

public class MatrixWeightedDiGraph<T> extends MatrixDiGraph<T> implements NetworkADT<T> {

    @Override
    public void addEdge(T vertex1, T vertex2, double weight) {
        try {
            matrix[getIndex(vertex1)][getIndex(vertex2)] = weight;
            numOfEdges++;
        }catch (IndexOutOfBoundsException e){
            return;
        }
    }

    @Override
    public double shortestPathWeight(T vertex1, T vertex2) {
        double[] distanceArray = Utils.dijkstra(matrix,getIndex(vertex1), size(), new int[size()]);
        try{
            return distanceArray[getIndex(vertex2)];
        }catch (IndexOutOfBoundsException e){
            return -1;
        }
    }

    public static void main(String[] args) {
        //testing
        NetworkADT<String> graph = new MatrixWeightedDiGraph<>();

        graph.addVertex("Quarto1");
        graph.addVertex("Quarto2");
        graph.addVertex("Quarto3");
        graph.addVertex("Quarto4");
        graph.addVertex("Sala");

        graph.addEdge("Quarto1", "Quarto2", 2.0);
        graph.addEdge("Quarto1", "Quarto3", 4.0);
        graph.addEdge("Quarto2", "Quarto3", 1.0);
        graph.addEdge("Quarto3", "Sala", 3.0);
        graph.addEdge("Quarto3", "Quarto4", 1.0);
        graph.addEdge("Sala", "Quarto2", 1.0);
        graph.addEdge("Quarto4", "Sala", 1.0);

        Iterator<String> spIter= graph.iteratorShortestPath("Quarto1", "jshdjashdhj");
        System.out.println(graph.shortestPathWeight("Quarto1", "ansda"));
        while (spIter.hasNext()){
            System.out.println(spIter.next());
        }
    }
}
