package Stacks;

public class LinearNode<T> {
    private T element;
    private LinearNode<T> next;

    /**
     * Creates a node storing the specified element.
     * @param elem element to be stored */
    public LinearNode(T elem) {
        next = null;
        element = elem;
    }

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public LinearNode<T> getNext() {
        return next;
    }

    public void setNext(LinearNode<T> next) {
        this.next = next;
    }
}
