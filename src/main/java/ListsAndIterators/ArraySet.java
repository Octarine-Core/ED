package ListsAndIterators;

//Array-based implementation of a set. no duplicate elements allowed.
public class ArraySet<T> extends ArrayList<T> {
    public boolean add(T element){
        if (contains(element)) return true;
        else {
            if(last>=array.length-2){
                expandCapacity();
            }
            array[last] = element;
            last++;
            return false;
        }
    }
}
