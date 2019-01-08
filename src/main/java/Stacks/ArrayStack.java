package Stacks;

import java.util.EmptyStackException;

public class ArrayStack<T> implements StackADT<T> {

    private final int DEFAULT_CAPACITY = 100;
    protected int top;
    protected T[] stack;

    public ArrayStack() {
        top = 0;
        stack = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    /**
     * Creates an empty stack using the specified capacity.
     *
     * @param initialCapacity represents the specified capacity
     */
    public ArrayStack(int initialCapacity) {
        top = 0;
        stack = (T[]) (new Object[initialCapacity]);
    }

    /**
     * Adds the specified element to the top of this stack,
     * expanding the capacity of the stack array if necessary.
     *
     * @param element generic element to be pushed onto stack
     */


    @Override
    public void push(T element) {
        if (size() == stack.length)
            expandCapacity();
        stack[top] = element;
        top++;
    }

    @Override
    public T pop() throws EmptyStackException {
        try {
            T element = stack[top - 1];
            top--;
            return element;
        }catch (ArrayIndexOutOfBoundsException e){
            throw new EmptyStackException();
        }
    }

    @Override
    public T peek() {
        return stack[top - 1];
    }

    @Override
    public boolean isEmpty() {
        if (top != 0) {
            return false;
        }
        return true;
    }

    @Override
    public int size() {
        return top;
    }

    private void expandCapacity() {

        T[] newStack = (T[]) new Object[size() + 1];
        for (int i = 0; i < size(); i++) {
            newStack[i] = stack[i];
        }
        stack = newStack;
    }

}