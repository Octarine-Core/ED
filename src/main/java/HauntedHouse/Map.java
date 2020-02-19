package HauntedHouse;


import Graphs.MatrixWeightedDiGraph;
import Graphs.MatrixWeightedGraph;
import Graphs.NetworkADT;
import ListsAndIterators.*;
import com.sun.org.apache.xml.internal.security.Init;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.activation.UnsupportedDataTypeException;
import java.io.*;
import java.util.Iterator;
import java.util.Random;
import java.util.function.DoubleBinaryOperator;

/**
 * Represents a map.
 */
public class Map extends MatrixWeightedDiGraph<String> {
    private String name;
    Double healthPoints;
    private String mapPath;
    public String shieldRoom;
    public double initialShieldHp;
    public double shieldHp;
    private double biggestGhost = 0;

    /**
     * Constructor for an implicit Map object.
     * @param path to where the map data is stored.
     * @throws InvalidMapFormatException if data from map is incorrectly formatted.
     */
    Map(String path) throws InvalidMapFormatException {

        super();

        mapPath = path;
        //Sets up a filereader, and a JSON parser to read the mapfile
        JSONParser parser = new JSONParser();

        FileReader fileReader;
        try {
            fileReader = new FileReader(mapPath);
        } catch (FileNotFoundException e) {
            throw new InvalidMapFormatException("File not found: "+ e);
        }

        JSONObject jsonObject;
        //Throws error if it goes wrong
        try {
            jsonObject = (JSONObject) parser.parse(fileReader);
        } catch (IOException e) {
            e.printStackTrace();
            throw new InvalidMapFormatException("Could not read File: "+ e);
        } catch (ParseException e) {
            throw new InvalidMapFormatException("JSON was poorly formatted: "+ e);
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
            addVertex(currentRoom);
            Room newRoom = new Room(currentRoom, ((Long)obj.get("fantasma")).intValue());
            if(newRoom.ghost > biggestGhost) biggestGhost = newRoom.ghost;
            rooms.addToRear(newRoom);
        }
        //addVertex("exterior");
        //addVertex("entrada");
        //rooms.addToRear(new Room("entrada", 0));
        //rooms.addToRear(new Room("exterior", 0));

        iter = jsonArray.iterator();

        //adds the edges
        while (iter.hasNext()){
            JSONObject obj = (JSONObject)iter.next();

            JSONArray arr = (JSONArray)obj.get("ligacoes");
            for (Object o: arr){
                String connection = (String)o;
                String departingRoom = (String) obj.get("aposento");
                if(rooms.find(connection)==null){
                    rooms.addToRear(new Room(connection, 0));
                    addVertex(connection);
                }
                addEdge(departingRoom, connection, rooms.find(connection).ghost);
                addEdge(connection, departingRoom, rooms.find(departingRoom).ghost);
            }
        }

        addShield();

    };

    /**
     * Adds a shield to a room with no ghost in it (except for entrada and exterior)
     * @throws InvalidMapFormatException if there is no entrada or exterior
     */
    private void addShield() throws InvalidMapFormatException {
        UnorderedListADT<String> ghostlessRooms = new ArrayUnorderedList<>();
        Iterator<String> roomIter = this.verticesIterator();

        while (roomIter.hasNext()){
            String current = roomIter.next();
            if(this.getIncomingEdges(current).contains(0.0)){
                ghostlessRooms.addToRear(current);
                System.out.println(current);
            }
        }

        if(ghostlessRooms.size() != 2){
            try {
                ghostlessRooms.remove("entrada");
                ghostlessRooms.remove("exterior");
            } catch (ElementNotFoundException e) {
                throw new InvalidMapFormatException("NO ENTRANCE OR EXTERIOR");
            }

            Random rand = new Random();
            int randInt = rand.nextInt(ghostlessRooms.size()) + 1;

            Iterator<String> ghostlessIter = ghostlessRooms.iterator();
            int i = 0;
            while (ghostlessIter.hasNext()){
                if(i < randInt){
                    shieldRoom = ghostlessIter.next();
                    i++;
                }else {
                    break;
                }
            }

            System.out.println("Shield added to "+ shieldRoom);
            initialShieldHp = rand.nextInt(((int)biggestGhost )) + 1;
            shieldHp = initialShieldHp;
        }
    }

    /**
     * Saves the score to the same PATH that the map was loaded from
     *
     * @param name players name
     * @param hp remaining hp
     * @param difficulty selected difficulty
     */
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

    /**
     * Gets the ordered scoredBoard
     * @return OrderedList of Scores, compared by dificulty first and then by score.
     */
    public ArrayOrderedList<Score> getScores() throws InvalidMapFormatException {

        JSONParser parser = new JSONParser();

        FileReader fileReader;
        JSONObject jsonObject;
        try {
            fileReader = new FileReader(mapPath);
            jsonObject = (JSONObject) parser.parse(fileReader);
        } catch ( IOException | ParseException e) {
            throw new InvalidMapFormatException("COUDLNT PARSE FILE: " + e.toString());
        }
        ArrayOrderedList<Score> scoresList = new ArrayOrderedList<>();
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
                throw new InvalidMapFormatException("COULDNT READ SCORE FROM FILE: " + e.toString());
            }
        }
        if(scoresList.isEmpty())return null;
        return scoresList;
    }
}

