package ListsAndIterators;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoubleLinkedList<T> implements ListADT<T> {
    protected DoubleLinkedNode<T> first;
    protected DoubleLinkedNode<T> last;
    protected int count;
    private int expectedModCount;
    /**
     * Removes and returns the first element from this list.
     *
     * @return the first element from this list
     */
    @Override
    public T removeFirst() {
        T data;
        if(count>0){
            first.getNext().setPrevious(null);
            data = first.getData();
            first = first.getNext();
            count--;
            expectedModCount++;
            return data;
        }
        else {
            return null;
        }
    }

    /**
     * Removes and returns the last element from this list.
     *
     * @return the last element from this list
     */
    @Override
    public T removeLast() {
        T data;
        if(count>0) {
            last.getPrevious().setNext(null);
            data = last.getData();
            last = last.getPrevious();
            count--;
            expectedModCount++;
            return null;

        }
        else {
            return null;
        }
    }

    /**
     * Removes and returns the specified element from this list.
     *
     * @param element the element to be removed from the list
     */
    @Override
    public T remove(T element) {
        DoubleLinkedNode<T> current;
        current = first;
        while (current!=null){
            if (current.getData().equals(element)){
                current.getPrevious().setNext(current.getNext());
                current.getNext().setPrevious(current.getPrevious());
                count--;
                expectedModCount++;
                return element;
            }
            else {
                current=current.getNext();
            }
        }
        return null;
    }

    /**
     * Returns a reference to the first element in this list.
     *
     * @return a reference to the first element in this list
     */
    @Override
    public T first() {
        return first.getData();
    }

    /**
     * Returns a reference to the last element in this list.
     *
     * @return a reference to the last element in this list
     */
    @Override
    public T last() {
        return last.getData();
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
        DoubleLinkedNode<T> current;
        current=first;
        while (current!=null){
            if(current.getData().equals(target)){
                return true;
            }
            current.setNext(current.getNext());
        }
        return false;
    }

    /**
     * Returns true if this list contains no elements.
     *
     * @return true if this list contains no elements
     */
    @Override
    public boolean isEmpty() {
        if (first==null){
            return true;
        }
        return false;
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return the integer representation of number of
     * elements in this list
     */
    @Override
    public int size() {
        return count;
    }

    /**
     * Returns an iterator for the elements in this list.
     *
     * @return an iterator over the elements in this list
     */
    @Override
    public Iterator iterator() {
        return new DoubleLinkedIterator();
    }

    public DoubleLinkedNode<T> getFirst() {
        return first;
    }

    public DoubleLinkedNode<T> getLast() {
        return last;
    }

    private class DoubleLinkedIterator<T> implements Iterator<T>{

        private int modCount;
        private DoubleLinkedNode<T> current;


        public DoubleLinkedIterator() {
            this.modCount = expectedModCount;
            this.current = new DoubleLinkedNode<>(null);
            current.setNext(first);
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return (current.getNext()!=null);
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         * @throws ConcurrentModificationException if the collection was modified during iteration
         */
        @Override
        public T next()throws NoSuchElementException, ConcurrentModificationException {
            if(modCount!=expectedModCount){
                throw new ConcurrentModificationException();
            }
            current = current.getNext();
            if(current!=null) {
                return current.getData();
            }
            throw new NoSuchElementException();
        }
    }
}
