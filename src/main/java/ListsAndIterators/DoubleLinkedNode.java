package ListsAndIterators;

public class DoubleLinkedNode<T> {

    private T data;
    private DoubleLinkedNode next;
    private DoubleLinkedNode previous;

    public DoubleLinkedNode(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public DoubleLinkedNode<T> getNext() {
        return next;
    }

    public void setNext(DoubleLinkedNode next) {
        this.next = next;
    }

    public DoubleLinkedNode<T> getPrevious() {
        return previous;
    }

    public void setPrevious(DoubleLinkedNode<T> previous) {
        this.previous = previous;
    }
}
