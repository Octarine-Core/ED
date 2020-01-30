package SearchAndSort;

public class Searcher<T extends Comparable<? super T>> {

    int linSearchInArray(T[] array, T element){
        for(int i= 0; i<array.length;i++){
            if(element.compareTo(array[i])==0){
                return i;
            }
        }
        return -1;
    }

    int binarySearchInArray(T[] array, T element){
        boolean found = false;
        int currIndx;
        int upperBoundIndx = array.length - 1;
        int lowerBoundIndx = 0 ;
        while (true){
            currIndx = lowerBoundIndx + (upperBoundIndx-lowerBoundIndx)/2;

            int comparison = element.compareTo(array[currIndx]);

            if(comparison == 0){
                return currIndx;
            }else {
                if(upperBoundIndx-lowerBoundIndx == 1){
                    currIndx++;
                }
                if (comparison>0){ //SE O ELEMENTO A SER ENCONTRADO FOR MAIOR QUE A POSICAO ATUAL
                    lowerBoundIndx = currIndx;
                }
                else {             //SE O ELEMENTO A SER ENCONTRADO FOR MENOR QUE A POSICAO ATUAL
                    upperBoundIndx = currIndx;
                }
            }
        }
    }

    void selectionSort(T[] array){
        int min;
        T temp;
        for (int index = 0; index < array.length-1; index++){
            min = index;
            for (int scan = index+1; scan < array.length; scan++)
                if (array[scan].compareTo(array[min])<0)
                    min = scan;
            /** Swap the values */
            temp = array[min];
            array[min] = array[index];
            array[index] = temp;
        }
    }

    void insertionSort(T[] array){
        for(int i = 1; i<array.length; i++){
            T elem = array[i];
            int j;
            for(j=i-1; j>=0;j--){
                array[j+1] = array[j];
            }
            array[j+1] = elem;
        }
    }
    void bubbleSort(T[] array){
        return;
    }
}
