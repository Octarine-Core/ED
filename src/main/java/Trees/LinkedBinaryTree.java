package Trees;
import LinkedListsStacksAndQueues.LinkedQueue;
import ListsAndIterators.ArrayUnorderedList;

import java.util.Iterator;

public class LinkedBinaryTree<T> implements BinaryTreeADT<T> {

    LinkedBinaryNode<T> root;
    int count;

    /**
     * Returns a reference to the root element
     *
     * @return a reference to the root
     */
    @Override
    public T getRoot() {
        return root.getElement();
    }

    /**
     * Returns true if this binary tree is empty and false otherwise.
     *
     * @return true if this binary tree is empty
     */
    @Override
    public boolean isEmpty() {
        return (count==0);
    }

    /**
     * Returns the number of elements in this binary tree.
     *
     * @return the integer number of elements in this tree
     */
    @Override
    public int size() {
        return count;
    }

    /**
     * Returns true if the binary tree contains an element that
     * matches the specified element and false otherwise.
     *
     * @param targetElement the element being sought in the tree
     * @return true if the tree contains the target element
     */
    @Override
    public boolean contains(T targetElement) {
        try {
            return (find(targetElement) == targetElement);
        } catch (ElementNotFoundException e) {
            return false;
        }
    }

    /**
     * Returns a reference to the specified element if it is found in
     * this binary tree.  Throws an exception if the specified element
     * is not found.
     *
     * @param targetElement the element being sought in the tree
     * @return a reference to the specified element
     */
    @Override
    public T find(T targetElement) throws ElementNotFoundException {
        LinkedBinaryNode<T> current = findAgain(targetElement, root);
        if (current == null)
            throw new ElementNotFoundException("binary tree");
        return (current.getElement());
    }

    /**
     * Returns a reference to the specified target element if it is
     * found in this binary tree.
     *
     * @param targetElement  the element being sought in this tree
     * @param next           the element to begin searching from
     */
    private LinkedBinaryNode<T> findAgain(T targetElement, LinkedBinaryNode<T> next) {
        if (next == null)
            return null;
        if (next.getElement().equals(targetElement))
            return next;
        LinkedBinaryNode<T> temp = findAgain(targetElement, next.getLeft());
        if (temp == null)
            temp = findAgain(targetElement, next.getRight());
        return temp;
    }

    /**
     * Performs an inorder traversal on this binary tree by calling an
     * overloaded, recursive inorder method that starts with the root.
     *
     * @return an iterator over the elements of this binary tree
     */
    @Override
    public Iterator<T> iteratorInOrder() {
        ArrayUnorderedList<T> list = new ArrayUnorderedList<>();
        inorder(root, list);
        return list.iterator();
    }


    /**
     * Performs a recursive inorder traversal.
     *
     * @param node      the node to be used as the root
     *                  for this traversal
     * @param tempList  the temporary list for use in this traversal
     */
    private void inorder (LinkedBinaryNode<T> node,
                            ArrayUnorderedList<T> tempList) {
        if (node != null) {
            inorder (node.getLeft(), tempList);
            tempList.addToRear(node.getElement());
            inorder (node.getRight(), tempList);
        }
    }

    /**
     * Performs a preorder traversal on this binary tree by calling an
     * overloaded, recursive preorder method that starts
     * with the root.
     *
     * @return an iterator over the elements of this binary tree
     */
    @Override
    public Iterator<T> iteratorPreOrder() {
        ArrayUnorderedList<T> list = new ArrayUnorderedList<>();
        preorder(root, list);
        return list.iterator();
    }

    private void preorder (LinkedBinaryNode<T> node, ArrayUnorderedList<T> tempList) {
        if (node != null) {
            tempList.addToRear(node.getElement());
            preorder (node.getLeft(), tempList);
            preorder (node.getRight(), tempList);
        }
    }

    /**
     * Performs a postorder traversal on this binary tree by
     * calling an overloaded, recursive postorder
     * method that starts with the root.
     *
     * @return an iterator over the elements of this binary tree
     */
    @Override
    public Iterator<T> iteratorPostOrder() {
        ArrayUnorderedList<T> list = new ArrayUnorderedList<>();
        postorder(root, list);
        return list.iterator();
    }

    private void postorder (LinkedBinaryNode<T> node, ArrayUnorderedList<T> tempList) {
        if (node != null) {
            postorder (node.getLeft(), tempList);
            postorder (node.getRight(), tempList);
            tempList.addToRear(node.getElement());
        }
    }

    /**
     * Performs a levelorder traversal on the binary tree,
     * using a queue.
     *
     * @return an iterator over the elements of this binary tree
     */
    @Override
    public Iterator<T> iteratorLevelOrder() {
        ArrayUnorderedList<T> list = new ArrayUnorderedList<>();
        levelorder(root, list);
        return list.iterator();
    }


    private void levelorder (LinkedBinaryNode<T> node, ArrayUnorderedList<T> tempList) {
        LinkedQueue<LinkedBinaryNode<T>> temp = new LinkedQueue<>();
        if (node != null) {
                temp.enqueue(node);
                while (temp.size()!=0){
                    LinkedBinaryNode<T> temp2 = temp.dequeue();
                    if(temp2!=null){
                        tempList.addToRear(temp2.getElement());
                        temp.enqueue(temp2.getLeft());
                        temp.enqueue(temp2.getRight());
                    }
                }
        }
    }


}
