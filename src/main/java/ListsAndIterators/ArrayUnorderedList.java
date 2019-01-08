package ListsAndIterators;

public class ArrayUnorderedList<T> extends ArrayList<T> implements UnorderedListADT<T> {
    public ArrayUnorderedList() {
    }

    @Override
    public void addToFront(T element) {

    }

    @Override
    public void addToRear(T element) {
        array[last] = element;
        last++;
    }

    @Override
    public void addAfter(T element, T target) {

    }
}
