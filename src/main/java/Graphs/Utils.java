package Graphs;

import ListsAndIterators.ArrayUnorderedList;
import Stacks.ArrayStack;
import Stacks.StackADT;

import java.awt.image.AreaAveragingScaleFilter;

public class Utils{

    private class Pair{
        int indx;
        double weight;
        public Pair(int indx, double weight) {
            this.indx = indx;
            this.weight = weight;
        }
    }

    // Funtion that implements Dijkstra's single source shortest path
    // algorithm for a graph represented using adjacency matrix
    // representation
    static double[] dijkstra(double graph[][], int src, int size, int[] parentArray) {

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
                if (!sptSet[v] && graph[u][v] != 0 &&
                        dist[u] != Integer.MAX_VALUE &&
                        dist[u] + graph[u][v] < dist[v]) {
                    parentArray[v] = u;
                    dist[v] = dist[u] + graph[u][v];
                }
        }
        return dist;
    }

    static int minDistance ( double dist[], Boolean sptSet[],int size) {
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

    //testing
    public static void main (String[] args) {
        double graph[][] = new double[][]{
                {0, 4, 0, 0, 0, 0, 0, 8, 0},
                {4, 0, 8, 0, 0, 0, 0, 11, 0},
                {0, 8, 0, 7, 0, 4, 0, 0, 2},
                {0, 0, 7, 0, 9, 14, 0, 0, 0},
                {0, 0, 0, 9, 0, 10, 0, 0, 0},
                {0, 0, 4, 14, 10, 0, 2, 0, 0},
                {0, 0, 0, 0, 0, 2, 0, 1, 6},
                {8, 11, 0, 0, 0, 0, 1, 0, 7},
                {0, 0, 2, 0, 0, 0, 6, 7, 0}
        };

        int i = 0;
        int parent[] = new int[9];
        for (double d:
                dijkstra(graph, 2, 9, parent)) {

            System.out.println(i + "  " + d);
            i++;
        }
        print("\n\n");
        for (Integer integer :
                getPath(parent, 6)) {
            print(integer);
        }
    }

    static ArrayUnorderedList<Integer> getPath(int parent[], int j) {
        int vertex = j;
        StackADT<Integer> stack = new ArrayStack<>();
        ArrayUnorderedList<Integer> list= new ArrayUnorderedList<>();
        while (vertex!=-1){
            stack.push(vertex);
            vertex=parent[vertex];
        }
        //this just reverses the order. I could have used addToFront but whatever.
        while (!stack.isEmpty()){
            list.addToRear(stack.pop());
        }
        return list;
    }

    public static void print(Object o){
        System.out.println(o);
    }
}
