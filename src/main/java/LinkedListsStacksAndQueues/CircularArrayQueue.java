package LinkedListsStacksAndQueues;

public class CircularArrayQueue<T> implements QueueADT<T> {

    private T[] array;

    private int front;
    private int rear;

    private int count;

    private final int DEFAULT_SIZE = 100;

    /**
     * Adds one element to the rear of this queue.
     *
     * @param element the element to be added to
     *                the rear of this queue
     */
    public void enqueue(T element) {
        if(array.length == count){
            expandCapacity();
        }

        array[rear] = element;
        rear = (rear+1) % array.length;
        count++;
    }

    /**
     * Removes and returns the element at the front of
     * this queue.
     *
     * @return the element at the front of this queue
     */
    public T dequeue() {
        if(!isEmpty()){
            T element = array[front];
            array[front] =null;
            front = (1 + front) % array.length;
            count--;
            return element;
        }
        return null;
    }

    /**
     * Returns without removing the element at the front of
     * this queue.
     *
     * @return the first element in this queue
     */
    public T first() {
        return array[front];
    }

    /**
     * Returns true if this queue contains no elements.
     *
     * @return true if this queue is empty
     */
    public boolean isEmpty() {
        return count == 0;

    }

    /**
     * Returns the number of elements in this queue.
     *
     * @return the integer representation of the size
     * of this queue
     */
    public int size() {
        return count;
    }

    private void expandCapacity() {
        T[] newArray = (T[]) new Object[array.length+1];

        for (int i = 0; i<array.length; i++) newArray[i] = array[i];
        array = newArray;
    }

    public CircularArrayQueue() {
        count = 0;
        array = (T[])new Object[DEFAULT_SIZE];
    }

    public CircularArrayQueue(T element) {
        count = 0;
        array = (T[])new Object[DEFAULT_SIZE];
        enqueue(element);
    }
}