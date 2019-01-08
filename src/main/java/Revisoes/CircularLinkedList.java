package Revisoes;

import LinkedListsStacksAndQueues.LinearNode;
import ListsAndIterators.ListADT;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CircularLinkedList<T> implements ListADT<T>{

    LinearNode<T> head;
    int count;
    int modCount;

    /**
     * Removes and returns the first element from this list.
     *
     * @return the first element from this list
     */
    @Override
    public T removeFirst() {
        LinearNode<T> current = head;
        T data = head.getElement();
        while (current.getNext()!=head){
            current=current.getNext();
        }
        current.setNext(head.getNext());
        head = current.getNext();
        modCount++;
        count--;
        return data;
    }

    /**
     * Removes and returns the last element from this list.
     *
     * @return the last element from this list
     */
    @Override
    public T removeLast() {
       return removeFirst();
    }
    /**
     * Removes and returns the specified element from this list.
     *
     * @param element the element to be removed from the list
     */
    @Override
    public T remove(T element) {
        if(isEmpty()){
            return null;
        }

        if(size()==1){
            int comp = ((Comparable<T>)element).compareTo(head.getNext().getElement());
            if (comp==0){
                head=null;
                return element;
            }
        }else {
            LinearNode<T> current = head;
            while (current.getNext() != head) {
                int comp = ((Comparable<T>) element).compareTo(current.getElement());
                if (comp == 0) {
                    current.setNext(current.getNext().getNext());
                    head = current;
                    modCount++;
                    count--;
                    return element;
                }
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
        return head.getElement();
    }

    /**
     * Returns a reference to the last element in this list.
     *
     * @return a reference to the last element in this list
     */
    @Override
    public T last() {
        return first();
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
        return (head == null);
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
    public Iterator<T> iterator() {
        return new CircularLinkedIterator();
    }

    private class CircularLinkedIterator implements Iterator<T>{

        int expectedModCount;
        LinearNode<T> current;
        boolean firstPass = true;

        public CircularLinkedIterator() {
            expectedModCount = modCount;
            current = new LinearNode<>();
            current.setNext(head);
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
            if(expectedModCount!=modCount){
                throw new ConcurrentModificationException();
            }
            if(current.getNext() == head){
                return false;
            }
            return true;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public T next() throws NoSuchElementException {
            if(expectedModCount!=modCount){
                throw new ConcurrentModificationException();
            }
            current = current.getNext();
            if(current==null){
                throw new NoSuchElementException();
            }
            return current.getElement();
        }
    }

    public void add(T elem){
        LinearNode<T> newNode = new LinearNode<>(elem);
        if(isEmpty()){
            newNode.setNext(newNode);

        }else if(size()==1) {
            newNode.setNext(head);
            head.setNext(newNode);
        }
        else {
            LinearNode<T> current = head;
            while (current.getNext() != head) {
                current = current.getNext();
            }
            current.setNext(newNode);
            newNode.setNext(head);
        }
        head = newNode;
        count++;
        modCount++;
    }

    public static void main(String[] args) {

        CircularLinkedList<Integer> circle = new CircularLinkedList<>();

        circle.add(1);
        circle.add(2);
        circle.add(3);
        System.out.println(circle.remove(1));
        System.out.println(circle.remove(2));
        System.out.println(circle.remove(3));



    }
}
