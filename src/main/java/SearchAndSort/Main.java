package SearchAndSort;

public class Main {

    public static void main(String[] args) {


        Integer[] A = new Integer[10];

        for(int i = 0; i<A.length; i++){
            A[i] = (i+1)*2;
            System.out.print(" "+ A[i] + "\t");
        }
        System.out.print("\n");
        for(int i = 0; i<A.length; i++){
            System.out.print("["+i+"]" + "\t");
        }

        Searcher<Integer> searcher = new Searcher<>();
        System.out.println("\n\nTHE ELEMENT IS IN INDEX : " + searcher.binarySearchInArray(A, 16));

    }
}
