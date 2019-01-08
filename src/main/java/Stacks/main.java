package Stacks;

public class main {
    public static void main(String args[]){

        StackADT<Integer> novoStack= new ArrayStack<>();
        novoStack.push(2);
        novoStack.push(5);
        novoStack.push(7);
        System.out.println(novoStack.pop());
        System.out.println(novoStack.pop());
        System.out.println(novoStack.pop());
        System.out.println(novoStack.peek());

    }
}
