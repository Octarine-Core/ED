package ListsAndIterators;

import Trees.ElementNotFoundException;

import java.util.Iterator;

public class ArrayList<T> implements ListADT<T>{

    protected T[] array;
    protected int last;


    @Override
    public T removeFirst() {
        T removed = array[0];
        for(int i = 1; i<last;i++){
            array[i-1]=array[i];
        }
        last--;
        return removed;
    }

    /**
     * Removes and returns the last element from this list.
     *
     * @return the last element from this list
     */
    @Override
    public T removeLast() {
        T newLast = array[last-1];
        last--;
        return newLast;
    }

    /**
     * Removes and returns the specified element from this list.
     *
     * @param element the element to be removed from the list
     */
    @Override
    public T remove(T element) {
        T rElem;
        int i = 0;
        boolean found = false;
        while (!found){
            if(array[i].equals(element)){
                found = true;
            }else {
                i++;
            }
        }
        rElem = array[i];
        if(found){
            last--;
            return rElem;
        }
        else {
            return null;
        }

    }

    /**
     * Returns a reference to the first element in this list.
     *
     * @return a reference to the first element in this list
     */
    @Override
    public T first() {
        return array[0];
    }

    /**
     * Returns a reference to the last element in this list.
     *
     * @return a reference to the last element in this list
     */
    @Override
    public T last() {
        return array[last-1];
    }

    /**
     * Returns true if this list contains the specified target
     * element.
     *
     * @param target the target that is being sought in the list
     * @return true if the list contains this element
     */
    @Override
    public boolean contains(T target) {
        return false;
    }

    /**
     * Returns true if this list contains no elements.
     *
     * @return true if this list contains no elements
     */
    @Override
    public boolean isEmpty() {
        return (last == 0);
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return the integer representation of number of
     * elements in this list
     */
    @Override
    public int size() {
        return last;
    }

    /**
     * Returns an iterator for the elements in this list.
     *
     * @return an iterator over the elements in this list
     */
    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator<>(array, last);
    }
    
    protected void expandCapacity(){
        T[] newArray = (T[])(new Object[array.length*2]);
        for(int i = 0; i<array.length; i++){
            newArray[i] = array[i];
        }
        array = newArray;
    }
}
