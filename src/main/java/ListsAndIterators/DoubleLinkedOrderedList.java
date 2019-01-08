package ListsAndIterators;

import javax.activation.UnsupportedDataTypeException;
import java.util.Iterator;

public class DoubleLinkedOrderedList<T> extends DoubleLinkedList<T> implements OrderedListADT<T> {

    @Override
    public void add(T element)throws UnsupportedDataTypeException {
        if(!(element instanceof Comparable)){
            throw new UnsupportedDataTypeException();
        }
        DoubleLinkedNode<T> current;
        current=first;
        DoubleLinkedNode<T> newNode = new DoubleLinkedNode<>(element);
        if(current == null){
            current = first = last = newNode;
            count++;
        }
        else {
            while (current!=null) {
                if (current == last) {
                    current.setNext(newNode);
                    newNode.setPrevious(current);
                    last = newNode;
                    count++;
                    break;
                }else if (((Comparable) element).compareTo(current.getData()) < 0) {
                    newNode.setNext(current);
                    current.getPrevious().setNext(newNode);
                    newNode.setPrevious(current.getPrevious());
                    current.setPrevious(newNode);
                    count++;
                    break;
                }
                current = current.getNext();
            }
        }
    }

    public static void main(String[] args) throws UnsupportedDataTypeException {

        DoubleLinkedOrderedList<Integer> A = new DoubleLinkedOrderedList<>();

        A.add(1);
        A.add(2);
        A.add(4);
        A.add(4);
        A.add(4);
        A.add(3);
        A.add(1);
        A.add(1);

        Iterator iterA = A.iterator();

        while (iterA.hasNext()){
            System.out.println(iterA.next());
        }
    }
}