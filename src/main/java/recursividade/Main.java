package recursividade;

import LinkedListsStacksAndQueues.LinearNode;
import ListsAndIterators.DoubleLinkedNode;

public class Main<T> {
    /*
    Escrever um método recursivo que imprima
todos
os elementos de uma lista simplesmente ligada,
do primeiro ao último elemento
.
Deverá ser possível usar o
método
para qualquer
lista ligada à sua
escolh
a
. Testar a solução desenvolvida
     */
    public static Object printListRecursively(LinearNode<?> node){

        if(node.getNext()==null){
            return node.getElement();
        }
        return node.getElement().toString() + printListRecursively(node.getNext());
    }

    public static Object printDoubleListFromHead(DoubleLinkedNode<?> node){
        if(node.getNext()==null){
            return node.getData();
        }
        return (node.getData()+(String)printDoubleListFromHead(node.getNext()));

    }
    public static Object printDoubleListFromTail(DoubleLinkedNode<?> node){
        if(node.getPrevious()==null){
            return node.getData();
        }
        return (node.getData()+(String)printDoubleListFromTail(node.getPrevious()));
    }


    public static void main(String[] args) {



        LinearNode<String> node1 = new LinearNode<>("Hey");
        LinearNode<String> node2 = new LinearNode<>("There");
        LinearNode<String> node3 = new LinearNode<>("Friend");

        node1.setNext(node2);
        node2.setNext(node3);

        System.out.println(printListRecursively(node1));
    }
}
