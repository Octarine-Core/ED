package HauntedHouse;

import ListsAndIterators.ArrayUnorderedList;

public class Room {
    String name;
    int ghost;

    public Room(String name, int ghost) {
        this.name = name;
        this.ghost = ghost;
    }

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
