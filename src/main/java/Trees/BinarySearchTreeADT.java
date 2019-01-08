package Trees;

public interface BinarySearchTreeADT<T extends Comparable<? super T>> extends BinaryTreeADT<T> {
    public void addElement(T element);

    public T removeElement(T targetElement);

    public void removeAllOccurrences(T targetElement);

    public T removeMin();

    public T removeMax();

    public T findMin();

    public T findMax();

}
