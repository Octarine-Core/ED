package Graphs;

import LinkedListsStacksAndQueues.LinkedQueue;
import LinkedListsStacksAndQueues.QueueADT;
import ListsAndIterators.ArrayIterator;
import ListsAndIterators.ArraySet;
import ListsAndIterators.ArrayUnorderedList;
import ListsAndIterators.UnorderedListADT;
import Stacks.ArrayStack;
import Stacks.StackADT;
import java.util.Iterator;

public class MatrixDiGraph<T>implements GraphADT<T> {
    Double[][] matrix;
    int numOfVertices;
    int numOfEdges;
    protected int SIZE = 50;
    T vertices[];

    public MatrixDiGraph() {
        matrix = new Double[SIZE][SIZE];
        vertices = (T[]) new Object[SIZE];
        numOfEdges = 0;
        numOfVertices = 0;
    }

    @Override
    public void addVertex(T vertex) {
        vertices[numOfVertices] = vertex;
        numOfVertices++;
    }

    @Override
    public void removeVertex(T vertex) {
        boolean found = false;
        for (int i = 0; i<numOfVertices;i++){
            if(vertex==vertices[i]&&!found){
                found=true;
            }
            if(found){
                vertices[i]=vertices[i+1];
            }
        }
    }

    @Override
    public void addEdge(T vertex1, T vertex2) {
        matrix[getIndex(vertex1)][getIndex(vertex2)]=1.0;
        numOfEdges++;
    }

    @Override
    public void removeEdge(T vertex1, T vertex2) {
        matrix[getIndex(vertex1)][getIndex(vertex2)]=null;
        numOfEdges--;
    }

    @Override
    public Iterator iteratorBFS(T startVertex) {
        LinkedQueue<T> queue = new LinkedQueue<>();
        ArraySet<T> visitedVertices = new ArraySet<>();
        visitedVertices.add(startVertex);
        queue.enqueue(startVertex);

        while (!queue.isEmpty()){
            T v = queue.dequeue();
            for(int i = 0; i<numOfVertices; i++){
                for (int j = 0;j < matrix[getIndex(v)].length; j++) {
                    if(matrix[getIndex(v)][j]!= null && !visitedVertices.contains(vertices[j])){
                        queue.enqueue(vertices[j]);
                        visitedVertices.add(vertices[j]);
                    }
                }
            }
        }

        return visitedVertices.iterator();
    }

    @Override
    public Iterator iteratorDFS(T startVertex) {
        return null;
    }

    @Override
    public Iterator iteratorShortestPath(T startVertex, T targetVertex){
        int startIndx = getIndex(startVertex);
        int parent[] = new int[size()];
        dijkstra(startIndx, size(), parent);
        ArrayUnorderedList<T> iterList = getPath(parent, getIndex(targetVertex));
        return iterList.iterator();
    }

    @Override
    public boolean isEmpty() {
        return numOfVertices==0;
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public int size() {
        return numOfVertices;
    }
    protected int getIndex(T vertex1){
        if(vertex1 == null)return -1;
        int i = 0;
        while (i < size()) {
            if (vertex1.equals(vertices[i])) {
                return i;
            } else {
                i++;
            }
        }
        return -1;
    }
    private void expandCapacity(){
        SIZE *= 2;
        T newVertices[] = (T[]) new Object[SIZE];
        Double newEdges[][] = new Double[SIZE][SIZE];
        for (int i = 0; i < vertices.length; i++) {
            newVertices[i] = vertices[i];
            for (int j = 0; j < vertices.length; j++) {
                newEdges[i][j] = matrix[i][j];
            }
        }
        vertices = newVertices;
        matrix = newEdges;
    }

    public Iterator<T> verticesIterator(){
        return new ArrayIterator<>(vertices, numOfVertices);
    }

    public boolean vertexExists(T vertex){
        for (T v:
                vertices) {
            if (v.equals(vertex)){
                return true;
            }
        };
        return false;
    }

    // Funtion that implements Dijkstra's single source shortest path
    // algorithm for a graph represented using adjacency matrix
    // representation
    protected double[] dijkstra(int src, int size, int[] parentArray) {

        double dist[] = new double[size]; // The output array. dist[i] will hold
        // the shortest distance from src to i

        // sptSet[i] will true if vertex i is included in shortest
        // path tree or shortest distance from src to i is finalized
        Boolean sptSet[] = new Boolean[size];

        // Initialize all distances as INFINITE and stpSet[] as false
        for (int i = 0; i < size; i++) {
            parentArray[i] = -1;
            dist[i] = Integer.MAX_VALUE;
            sptSet[i] = false;
        }

        // Distance of source vertex from itself is always 0
        dist[src] = 0;

        // Find shortest path for all vertices
        for (int count = 0; count < size - 1; count++) {
            // Pick the minimum distance vertex from the set of vertices
            // not yet processed. u is always equal to src in first
            // iteration.
            int u = minDistance(dist, sptSet, size);

            // Mark the picked vertex as processed
            sptSet[u] = true;

            // Update dist value of the adjacent vertices of the
            // picked vertex.
            for (int v = 0; v < size; v++)

                // Update dist[v] only if is not in sptSet, there is an
                // edge from u to v, and total weight of path from src to
                // v through u is smaller than current value of dist[v]
                if (!sptSet[v] && matrix[u][v] != null &&
                        dist[u] != Integer.MAX_VALUE &&
                        dist[u] + matrix[u][v] < dist[v]) {
                    parentArray[v] = u;
                    dist[v] = dist[u] + matrix[u][v];
                }
        }
        return dist;
    }

    static int minDistance (double dist[], Boolean sptSet[],int size) {
        // Initialize min value
        double min = Double.MAX_VALUE;
        int min_index = -1;

        for (int v = 0; v < size; v++)
            if (sptSet[v] == false && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }

        return min_index;
    }

    protected ArrayUnorderedList<T> getPath(int parent[], int j) {
        int vertex = j;
        StackADT<Integer> stack = new ArrayStack<>();
        ArrayUnorderedList<T> list= new ArrayUnorderedList<>();
        while (vertex!=-1){
            stack.push(vertex);
            vertex=parent[vertex];
        }
        //this just reverses the order. I could have used addToFront but whatever.
        while (!stack.isEmpty()){
            list.addToRear(vertices[stack.pop()]);
        }
        return list;
    }

    public UnorderedListADT<T> getNeighbours(T vertex){
        UnorderedListADT<T> neighbours = new ArrayUnorderedList<>();
        for (int i = 0; i < size(); i++) {
            if(matrix[getIndex(vertex)][i] != null){
                neighbours.addToRear(vertices[i]);
            }
        }
        return neighbours;
    }

    public static void main(String[] args) {
    }

}
