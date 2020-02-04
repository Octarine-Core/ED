package HauntedHouse;


import Graphs.MatrixWeightedDiGraph;
import Graphs.MatrixWeightedGraph;
import Graphs.NetworkADT;
import ListsAndIterators.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.activation.UnsupportedDataTypeException;
import java.io.*;
import java.util.Iterator;
import java.util.function.DoubleBinaryOperator;

public class Map extends MatrixWeightedDiGraph<String> {
    private String name;
    Double healthPoints;
    private String mapPath;


    Map(String path) {

        super();

        mapPath = path;
        //Sets up a filereader, and a JSON parser to read the mapfile
        JSONParser parser = new JSONParser();

        FileReader fileReader;
        try {
            fileReader = new FileReader(mapPath);
        } catch (FileNotFoundException e) {
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
        healthPoints =  ((Long)jsonObject.get("pontos")).doubleValue();

        ArrayUnorderedList<Room> rooms = new ArrayUnorderedList<>();

        JSONArray jsonArray = (JSONArray) jsonObject.get("mapa");
        if(jsonArray == null)return;
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
                    addVertex(connection);
                }
                addEdge(departingRoom, connection, rooms.find(connection).ghost);
                addEdge(connection, departingRoom, rooms.find(departingRoom).ghost);
            }
        };
    };

    public void saveScore(String name, double hp, int difficulty){
        JSONParser parser = new JSONParser();

        FileReader fileReader;
        try {
            fileReader = new FileReader(mapPath);
        } catch (FileNotFoundException e) {
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

        JSONObject score = new JSONObject();
        score.put("name", name);
        score.put("score", hp);
        score.put("difficulty", difficulty);


        JSONArray scores = ((JSONArray)jsonObject.get("scores"));
        if(scores == null){
            scores = new JSONArray();
        }
        scores.add(score);
        System.out.print(scores);
        jsonObject.put("scores", scores);

        try (
            Writer out = new FileWriter(mapPath);
        ){
            out.write(jsonObject.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public OrderedListADT<Score> getScores(){

        JSONParser parser = new JSONParser();

        FileReader fileReader;
        try {
            fileReader = new FileReader(mapPath);
        } catch (FileNotFoundException e) {
            return null;
        }

        JSONObject jsonObject;
        //Throws error if it goes wrong
        try {
            jsonObject = (JSONObject) parser.parse(fileReader);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

        OrderedListADT<Score> scoresList = new ArrayOrderedList<>();

        JSONArray scores = ((JSONArray)jsonObject.get("scores"));
        if(scores == null){
            return null;
        };

        for (Object o :
                scores) {
            JSONObject obj = (JSONObject) o;
            try {
                Score score = (new Score((String) obj.get("name"), (double) obj.get("score"), ((Long)obj.get("difficulty")).intValue()));
                scoresList.add(score);
            }catch (NullPointerException | UnsupportedDataTypeException e){

            }
        }


        return scoresList;

    }
}

