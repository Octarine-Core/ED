package Trees;

public class LinkedBinarySearchTree<T extends Comparable<? super T>> extends LinkedBinaryTree<T> implements BinarySearchTreeADT<T>{
    @Override
    public void addElement(T element) {
        LinkedBinaryNode<T> current = root;
        LinkedBinaryNode<T> insertee = new LinkedBinaryNode<>(element);
        while (true){
            if(element.compareTo(current.getElement()) >= 0){
                if (current.getRight()!=null){
                    current = current.getRight();
                }else {
                    current.setRight(insertee);
                    break;
                }
            }
            else {
                if(current.getLeft()!=null){
                    current = current.getLeft();
                }
                else {
                    current.setLeft(insertee);
                    break;
                }
            }
        }
    }
    @Override
    public T removeElement(T targetElement) {
        return null;
    }
    @Override
    public void removeAllOccurrences(T targetElement) {

    }
    @Override
    public T removeMin() {
        return null;
    }
    @Override
    public T removeMax() {
        return null;
    }
    @Override
    public T findMin() {

    }
    @Override
    public T findMax() {
        return null;
    }
    private static void leftRotation(LinkedBinaryNode X){
        LinkedBinaryNode Z = X.getRight();
        LinkedBinaryNode pivotNode = Z.getLeft();
        X.setRight(pivotNode);
        Z.setLeft(X);
    }
}
