package ListsAndIterators;

//Array-based implementation of a set. no duplicate elements allowed.
public class ArraySet<T> extends ArrayList<T> {
    public boolean add(T element){
        if (contains(element)) return true;
        else {
            if(size==array.length-1){
                expandCapacity();
            }
            array[size] = element;
            size++;
            return false;
        }
    }
}
