package Stacks;

import java.util.EmptyStackException;

public class LinkedStack<T> implements StackADT<T> {

    protected LinearNode<T> top;
    protected int count;

    public LinkedStack(){
        top = null;
        count = 0;
    }

    public LinkedStack(T data){
        top = new LinearNode<>(data);
        count++;
    }


    /**
     * Adds one element to the top of this stack.
     *
     * @param element element to be pushed onto stack
     */
    public void push(T element) {
        LinearNode newNode = new LinearNode<T>(element);
        newNode.setNext(top); // se top nao existir, referencia para null?
        top = newNode;

    }

    /**
     * Removes and returns the top element from this stack.
     *
     * @return T element removed from the top of the stack
     * @throws EmptyStackException if the Stack is empty
     */
    public T pop() {
        if(isEmpty()) throw new EmptyStackException();
        LinearNode<T> node = top;
        top = node.getNext();
        node.setNext(null);
        return node.getElement();

    }

    /**
     * Returns without removing the top element of this stack.
     *
     * @return T element on top of the stack
     */
    public T peek() {
        if (isEmpty()) throw new EmptyStackException();
        return top.getElement();
    }

    /**
     * Returns true if this stack contains no elements.
     *
     * @return boolean whether or not this stack is empty
     */
    public boolean isEmpty() {
        if(top == null){
            return true;
        }
        return false;
    }

    /**
     * Returns the number of elements in this stack.
     *
     * @return int number of elements in this stack
     */
    public int size() {
        return count;
    }
}
