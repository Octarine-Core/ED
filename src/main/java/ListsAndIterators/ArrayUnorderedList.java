package ListsAndIterators;

public class ArrayUnorderedList<T> extends ArrayList<T> implements UnorderedListADT<T> {
    public ArrayUnorderedList() {
        array = (T[])new Object[100];
        size = 0;
    }

    @Override
    public void addToFront(T element) {
        if(array.length-1 == size){
            expandCapacity();
        }
        for(int i = size; i>0;i--){
            array[i]=array[i-1];
        }
        array[0]=element;
        size++;
    }

    @Override
    public void addToRear(T element) {
        if(array.length-1 == size){
            expandCapacity();
        }
        array[size] = element;
        size++;
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
