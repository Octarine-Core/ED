package LinkedListsStacksAndQueues;

public class LinkedQueue<T> implements QueueADT<T> {

    private LinearNode<T> front;
    private LinearNode<T> rear;
    private int count;

    /**
     * Adds one element to the rear of this queue.
     *
     * @param element the element to be added to
     *                the rear of this queue
     */
    public void enqueue(T element) {
        LinearNode<T> newNode = new LinearNode<>(element);
        if (isEmpty()) {
            front = rear = newNode;
        } else {
            rear.setNext(newNode);
            rear = newNode;
        }
        count++;
    }

    /**
     * Removes and returns the element at the front of
     * this queue.
     *
     * @return the element at the front of this queue
     */
    public T dequeue() {
        if (!isEmpty()) {
            LinearNode<T> node = front;
            front = front.getNext();
            count--;
            return node.getElement();
        } else {
            return null;
        }
    }

    /**
     * Returns without removing the element at the front of
     * this queue.
     *
     * @return the first element in this queue
     */
    public T first() {
        try {
            return front.getElement();

        } catch (NullPointerException e) {
            System.err.println("meme");
            return null;
        }
    }

    /**
     * Returns true if this queue contains no elements.
     *
     * @return true if this queue is empty
     */
    public boolean isEmpty() {
        if (front == null) {
            return true;
        }
        return false;
    }

    /**
     * Returns the number of elements in this queue.
     *
     * @return the integer representation of the size
     * of this queue)
     */
    public int size() {
        return count;
    }

    public LinkedQueue() {
        count = 0;
    }

    public LinkedQueue(T element) {
        count = 0;
        enqueue(element);
    }

    public LinkedQueue(T[] elements) {
        count = 0;
        for (int i = 0; i < elements.length; i++) enqueue(elements[i]);
    }

}