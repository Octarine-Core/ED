package Revisoes;

import Stacks.LinearNode;
import Stacks.LinkedStack;

public class LinkedSmackStack<T> extends LinkedStack<T> implements SmackStackADT<T> {

    @Override
    public T smack() {
        T data;

        if(isEmpty()){
            return null;
        }
        if(count == 1){
            return pop();
        }
        LinearNode<T> current = top;
        while (current.getNext().getNext()!=null){
            current = current.getNext();
        }
        data = current.getNext().getElement();
        current.getNext().setNext(null);
        return data;
    }
}
