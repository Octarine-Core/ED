package HauntedHouse;

import ListsAndIterators.ArrayUnorderedList;

/**
 * Represents a room.
 */
public class Room {
    String name;
    int ghost;

    /**
     * Constructor for an implicit Room object.
     * @param name player's name.
     * @param ghost ghost's damage.
     */
    public Room(String name, int ghost) {
        this.name = name;
        this.ghost = ghost;
    }

    /**
     * Override of equals method.
     * Checks if another object is equal to this one.
     * @param room the reference Object with which to compare.
     * @return true if this object is the same as the room Object; false otherwise.
     */
    @Override
    public boolean equals(Object room){
        if(room instanceof String){
           return room.equals(name);
        }
        if(room instanceof Room){
            return name.equals(((Room) room).name);
        }
        return false;
    }
}
