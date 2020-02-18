package ListsAndIterators;

import javax.activation.UnsupportedDataTypeException;
import java.util.Iterator;

public class ArrayOrderedList<T> extends ArrayList<T> implements OrderedListADT<T>{



    public ArrayOrderedList() {

        array = (T[])(new Object[100]);
        size = 0;
    }

    /**
     * Adds the specified element to this list at
     * the proper location
     *
     * @param element the element to be added to this list
     */
    @Override
    public void add(T element) throws UnsupportedDataTypeException {

        if(!(element instanceof Comparable)){
            throw new UnsupportedDataTypeException();
        }

        if(size+1 >= array.length){
            expandCapacity();
        }

        int i;
        for(i = 0; i<size;i++){
            if(((Comparable) element).compareTo(array[i]) < 0){
                break;
            }
        }

        for(int j = size; j>i; j--){
            array[j+1] = array[j];
        }

        array[i] = element;
        size++;
    }
    /**
     * Removes and returns the first element from this list.
     *
     * @return the first element from this list
     */

    public static void main(String[] args){
        ArrayOrderedList<Integer> A = new ArrayOrderedList<>();

        try {
            A.add(1);
            A.add(2);
            A.add(2);
            A.add(3);
            A.add(3);
            A.add(3);
            A.add(3);

        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println(A.size());

        System.out.println(A.last());
        Iterator<Integer> iterA = A.iterator();

        while (iterA.hasNext()){
            System.out.println(iterA.next());
        }
    }
}
