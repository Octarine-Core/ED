package Revisoes;

import Stacks.ArrayStack;

public class ArraySmackStack<T> extends ArrayStack<T> implements SmackStackADT<T> {
    @Override
    public T smack() {

        if(isEmpty()){
            return null;
        }
        if (top == 1){
            return pop();
        }
        T data = stack[0];
        for(int i = 1; i<top;i++){
            stack[i-1] = stack[i];
        }
        top--;
        return data;
    }
}
