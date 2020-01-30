package HauntedHouse;


import Graphs.MatrixWeightedDiGraph;
import Graphs.MatrixWeightedGraph;
import Graphs.NetworkADT;
import ListsAndIterators.ArraySet;
import ListsAndIterators.ArrayUnorderedList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class Map extends MatrixWeightedDiGraph<String> {
    private String name;
    private int healthPoints;
    private MatrixWeightedGraph<String> map;
    private String mapPath;

    Map(String path) {
        super();
        //Sets up a filereader, and a JSON parser to read the mapfile
        JSONParser parser = new JSONParser();

        FileReader fileReader;
        try {
            fileReader = new FileReader(path);
        } catch (FileNotFoundException e) {
            fileReader = null;
            return;
        }

        JSONObject jsonObject;
        //Throws error if it goes wrong
        try {
            jsonObject = (JSONObject) parser.parse(fileReader);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }

        name = (String) jsonObject.get("nome");
        healthPoints =  ((Long)jsonObject.get("pontos")).intValue();



        ArrayUnorderedList<Room> rooms = new ArrayUnorderedList<>();

        JSONArray jsonArray = (JSONArray) jsonObject.get("mapa");
        Iterator iter = jsonArray.iterator();
        //adds the vertices
        while (iter.hasNext()){
            JSONObject obj = (JSONObject)iter.next();
            String currentRoom = (String) obj.get("aposento");
            System.out.println(currentRoom);
            addVertex(currentRoom);
            Room newRoom = new Room(currentRoom, ((Long)obj.get("fantasma")).intValue());
            rooms.addToRear(newRoom);
        }
        addVertex("exterior");
        addVertex("entrada");
        rooms.addToRear(new Room("entrada", 0));
        rooms.addToRear(new Room("exterior", 0));

        for (Room r :
                rooms) {
            System.out.println(r.name + "-  " + r.ghost);
        }
        Iterator vertexIter = verticesIterator();


        iter = jsonArray.iterator();

        //adds the edges
        while (iter.hasNext()){
            JSONObject obj = (JSONObject)iter.next();

            JSONArray arr = (JSONArray)obj.get("ligacoes");
            for (Object o: arr){
                String connection = (String)o;
                String departingRoom = (String) obj.get("aposento");
                System.out.println(departingRoom + "  " + connection);
                if(rooms.find(connection)==null){
                    rooms.addToRear(new Room(connection, 0));
                }
                addEdge(departingRoom, connection, rooms.find(connection).ghost);
                addEdge(connection, departingRoom, rooms.find(departingRoom).ghost);
            }
        };
    };
}

