package ListsAndIterators;

public class ArrayUnorderedList<T> extends ArrayList<T> implements UnorderedListADT<T> {
    public ArrayUnorderedList() {
        array = (T[])new Object[100];
        last = 0;
    }

    @Override
    public void addToFront(T element) {
        if(array.length >= last-2){
            expandCapacity();
        }
        for(int i = last; i>0;i--){
            array[i]=array[i-1];
        }
        array[0]=element;
        last++;
    }

    @Override
    public void addToRear(T element) {
        if(array.length >= last-2){
            expandCapacity();
        }
        array[last] = element;
        last++;
    }

    @Override
    public void addAfter(T element, T target) {
        throw new UnsupportedOperationException();

    }

    public T find(Object element){
        for (int i = 0; i < size(); i++) {
            if(array[i].equals(element)){
                return array[i];
            }
        }
        return null;
    }
}
