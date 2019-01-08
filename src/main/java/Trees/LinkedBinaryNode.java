package Trees;

public class LinkedBinaryNode<T>{
    private T element;
    private LinkedBinaryNode<T> left;
    private LinkedBinaryNode<T> right;

    public LinkedBinaryNode(T element) {
        this.element = element;
    }

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public LinkedBinaryNode<T> getLeft() {
        return left;
    }

    public void setLeft(LinkedBinaryNode<T> left) {
        this.left = left;
    }

    public LinkedBinaryNode<T> getRight() {
        return right;
    }

    public void setRight(LinkedBinaryNode<T> right) {
        this.right = right;
    }

    public int numChildren() {
        int children = 0;
        if (left != null)
            children = 1 + left.numChildren();
        if (right != null)
            children = children + 1 + right.numChildren();
        return children;
    }
}

