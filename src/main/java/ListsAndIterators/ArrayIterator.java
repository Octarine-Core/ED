package ListsAndIterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayIterator<T> implements Iterator<T> {
    /**
     * Returns {@code true} if the iteration has more elements.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    T[] array;
    int cursor;
    int last;


    public ArrayIterator(T[] array,int count) {
        this.array = array;
        cursor = 0;
        this.last = count;
    }

    @Override
    public boolean hasNext() {
        return (cursor<last);
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */

    public T next() {
        try {
            T elem = array[cursor];
            cursor++;
            return elem;
        }catch (ArrayIndexOutOfBoundsException e){
            throw new NoSuchElementException();
        }
    }
}
