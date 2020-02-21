package HauntedHouse;
import ListsAndIterators.ArrayOrderedList;
import ListsAndIterators.ArrayUnorderedList;
import ListsAndIterators.OrderedListADT;
import ListsAndIterators.UnorderedListADT;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

import static Graphs.Utils.*;

public class Main {

    static String selectedMap;
    static Map map;
    public static void main(String[] args) {
        //mainMenu();
        mainMenu();
    }

    /**
     * Shows in the console all menu options available to the user such as
     * the capability of loading a map, getting info about the game or exit the same.
     */
    static void mainMenu(){
        char option;
        Scanner scanner = new Scanner(System.in);
        char input='q';//initialized with any value at all
        print("Welcome to the game! :D");
        try { Thread.sleep(1000);} catch (InterruptedException e) {System.exit(0);}
        do{
            do {
                clearScreen();
                if(input == '`'){

                    print("Please select one of the options...\n");
                }
                print(
                        "MAIN MENU \n" +
                                "[A]bout the game\n" +
                                "[L]oad a map from computer\n" +
                                "[P]rint this message again\n" +
                                "[E]xit"
                );
                System.out.print("#: ");
                input = Character.toLowerCase(scanner.next().charAt(0));

                switch (input){
                    case 'a':
                        aboutGame();
                        input='p';
                        break;

                    case 'l':
                        mapSelectionMenu();
                        input='p';
                        break;

                    case 'p':
                        break;

                    case 'e':
                        break;
                    default:
                        input='`';
                }
            }while (input == 'p' || input == '`');
        }while (input!='e');
    }

    /**
     * Shows some data about the game.
     */
    static void aboutGame(){
        print("about game");
        Scanner scanner = new Scanner(System.in);

        System.out.print("write anything to continue #: ");
        scanner.next();

    }

    /**
     * Clears console by placing new-lines.
     */
    public static void clearScreen() {
        //doesnt work for some reason
        /*
        try {
            Process process = new ProcessBuilder("cmd", "/c", "cls").inheritIO().start();
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        String s = "";
        for (int i = 0; i < 30; i++) {
            s += "\n";
        }
        print(s);

    }

    /**
     * Shows all maps that are available to play or none
     * if the folder is empty.
     */
    public static void mapSelectionMenu(){
        clearScreen();
        String[] maps = listFilesInFolder(new File("./src/main/resources/"));
        int indx = 1;
        if(maps.length == 0){
            print("No maps on the resources folder :(");
            return;
        };
        print("Select a map: \n");
        for (String mapName :
                maps) {
            print(String.valueOf(indx) + ".  " + mapName);
            indx++;
        }
        int option;
        do{
            System.out.print("#: ");
            Scanner scanner = new Scanner(System.in);
            try{
                option = scanner.nextInt();
            }catch (InputMismatchException e){
                print("Wrong input.");
                option=-1;
            }
        }while (option<0 || option > maps.length);
        if(option==0){
            mainMenu();
            return;
        }
        selectedMap = maps[option-1];
        try {
            map = new Map("./src/main/resources/" + selectedMap);
            gameModeMenu();
        } catch (InvalidMapFormatException e) {
            print("Invalid Map format, error is: " + e.toString());
        }

    }

    /**
     * Shows all the mode options available to the user such as auto-play and manual.
     * Also able to show the scoreboard.
     */
    public static void gameModeMenu(){

        boolean done = false;
        do {
            clearScreen();
            print("Loaded map is " + selectedMap + "" + "\n\n" +
                    "Select the game mode: \n\n" +
                    "[A]utomatic: The game will play itself for you\n" +
                    "[M]anual: You will play manually\n" +
                    "[H]all of fame (scoreboard)\n" +
                    "[E]xit");
            Scanner scanner = new Scanner(System.in);
            char option;
            do {
                System.out.print("#: ");
                option = Character.toLowerCase(scanner.next().charAt(0));
                switch (option) {
                    case 'a':
                        difficultySelection(true);
                        done = true;
                        break;
                    case 'm':
                        difficultySelection(false);
                        done = true;
                        break;
                    case 'h':
                        listScores();
                        break;
                    case 'e':
                        done = true;
                        break;
                    default:
                        option = 'p';
                }
            } while (option == 'p');
        }while (!done);
    }

    /**
     * Shows all the difficulty types available to the user.
     * @param auto true if auto-play is choosen; false otherwise.
     */
    public static void difficultySelection(boolean auto){

        print("Select difficulty level: ");
        print("[E]asy\t" +
                "[M]edium\t" +
                "[H]ard\t");
        Scanner scanner = new Scanner(System.in);
        char option;
        do {
            System.out.print("#: ");
            option = Character.toLowerCase(scanner.next().charAt(0));
            switch (option){
                case 'e':
                    if(auto)autoPlay(1);
                    else gameLoop(1);
                    break;
                case 'm':
                    if(auto)autoPlay(2);
                    else gameLoop(2);
                    break;
                case 'h':
                    if(auto)autoPlay(3);
                    else gameLoop(3);
                    break;
                default:
                    option = 'p';
                    break;
            }
        }while (option == 'p');

    }

    /**
     * Shows the shortest path to exit, initial health,
     * damage received and final score for an auto-play game mode.
     * @param difficulty the difficulty choosen by the user.
     */
    public static void autoPlay(int difficulty){
        if(map.healthPoints - map.shortestPathWeight("entrada", "exterior") * difficulty <= 0){
            print("This map cant be loaded, it doesnt have a path that leaves you alive!");
            System.out.print("#: ");
            Scanner scanner = new Scanner(System.in);
            scanner.next();
            return;
        };

        clearScreen();
        print("The shortest path to the exit is:" +
                "\n");
        Iterator iter = map.iteratorShortestPath("entrada", "exterior");
        String outPut = "";
        boolean gotShield = false;
        double dmgTaken = 0;
        while (iter.hasNext()){
            String room = (String)iter.next();
            outPut +=  "| " + room;

            if(room.equals(map.shieldRoom)){
                map.shieldRoom = null;
                outPut += " (found shield: " + map.initialShieldHp + " durability.)";
                gotShield = true;
            }

            Double ghostDamage = map.getIncomingEdges(room).first()*difficulty;
            if(ghostDamage != 0.0){
                outPut += " (ghost dealt " + ghostDamage + " damage. ";
                double realDamage = ghostDamage;
                if(gotShield){
                    if(map.shieldHp<=ghostDamage){
                        gotShield = false;
                        outPut += " Shield broke. ";
                        realDamage = ghostDamage - map.shieldHp;
                        map.shieldHp = 0;
                    }else {
                        outPut += "Shield took " + ghostDamage + "dmg, ";
                        map.shieldHp-=ghostDamage;
                        realDamage = 0;
                    }
                    outPut += "You took " + realDamage;
                }
                dmgTaken += realDamage;
                outPut += ")";
            }
            outPut += " |";

            if(iter.hasNext()){
                outPut +=("   -->   ");
            }

        }
        print(outPut);

        print("You started with " + map.healthPoints + " HP, took " + dmgTaken
        + " points of damage, leaving you with a total final score of " + (map.healthPoints - dmgTaken));
        Scanner scanner = new Scanner(System.in);
        System.out.print("#: ");
        scanner.next();

    }

    /**
     * Obtains all map files on specified folder.
     * @param folder where the maps are located.
     * @return all files found on the specified folder.
     */
    public static String[] listFilesInFolder(final File folder) {
        File filesArr[] = folder.listFiles();
        String fileNames[] = new String[filesArr.length];
        int nameCounter = 0;
        for (int i = 0; i < filesArr.length ;i++) {
           if (!filesArr[i].isDirectory()) {
               fileNames[nameCounter] = filesArr[i].getName();
               nameCounter++;
           }
        }
        String newFileNames[] = new String[nameCounter];
        for (int i = 0; i < nameCounter; i++) {
            newFileNames[i]=fileNames[i];
        }
        return newFileNames;
        }

    /**
     * Initializes a new manual game mode where the player makes all the
     * moves he desires.
     * @param difficulty of the game choosen by the player.
     */
    public static void gameLoop(int difficulty){

        String position = "entrada";
        Double hp = map.healthPoints;
        boolean hasShield = false;
        boolean done = false;
        if(map.healthPoints <= map.shortestPathWeight("entrada", "exterior") * difficulty || map.shortestPathWeight( "entrada", "exterior") == -1){
            print("This map cant be loaded. It doesnt have a path that leaves you alive!");
            return;
        }
        do {
            if(hp<=0.0){
                clearScreen();
                print("Game over, you died!\n" +
                        "       @@@@@@@@@@@@@@@@@@\n" +
                        "     @@@@@@@@@@@@@@@@@@@@@@@\n" +
                        "   @@@@@@@@@@@@@@@@@@@@@@@@@@@\n" +
                        "  @@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n" +
                        " @@@@@@@@@@@@@@@/      \\@@@/   @\n" +
                        "@@@@@@@@@@@@@@@@\\      @@  @___@\n" +
                        "@@@@@@@@@@@@@ @@@@@@@@@@  | \\@@@@@\n" +
                        "@@@@@@@@@@@@@ @@@@@@@@@\\__@_/@@@@@\n" +
                        " @@@@@@@@@@@@@@@/,/,/./'/_|.\\'\\,\\\n" +
                        "   @@@@@@@@@@@@@|  | | | | | | | |\n" +
                        "                 \\_|_|_|_|_|_|_|_|");
                System.out.println("press anything to exit... #: ");
                Scanner scanner = new Scanner(System.in);
                scanner.next();

                scoreScreen(hp, difficulty);

                break;
            }
            else if(position.equals("exterior")){
                clearScreen();
                print("                    ==                     ==\n" +
                        "                 <^\\()/^>               <^\\()/^>\n" +
                        "                  \\/  \\/                 \\/  \\/\n" +
                        "                   /__\\      .  '  .      /__\\ \n" +
                        "      ==            /\\    .     |     .    /\\            ==\n" +
                        "   <^\\()/^>       !_\\/       '  |  '       \\/_!       <^\\()/^>\n" +
                        "    \\/  \\/     !_/I_||  .  '   \\'/   '  .  ||_I\\_!     \\/  \\/\n" +
                        "     /__\\     /I_/| ||      -== + ==-      || |\\_I\\     /__\\\n" +
                        "     /_ \\   !//|  | ||  '  .   /.\\   .  '  || |  |\\\\!   /_ \\\n" +
                        "    (-   ) /I/ |  | ||       .  |  .       || |  | \\I\\ (=   )\n" +
                        "     \\__/!//|  |  | ||    '     |     '    || |  |  |\\\\!\\__/\n" +
                        "     /  \\I/ |  |  | ||       '  .  '    *  || |  |  | \\I/  \\\n" +
                        "    {_ __}  |  |  | ||                     || |  |  |  {____}\n" +
                        " _!__|= ||  |  |  | ||   *      +          || |  |  |  ||  |__!_\n" +
                        " _I__|  ||__|__|__|_||          A          ||_|__|__|__||- |__I_\n" +
                        " -|--|- ||--|--|--|-||       __/_\\__  *    ||-|--|--|--||= |--|-\n" +
                        "  |  |  ||  |  |  | ||      /\\-'o'-/\\      || |  |  |  ||  |  |\n" +
                        "  |  |= ||  |  |  | ||     _||:<_>:||_     || |  |  |  ||= |  |\n" +
                        "  |  |- ||  |  |  | || *  /\\_/=====\\_/\\  * || |  |  |  ||= |  |\n" +
                        "  |  |- ||  |  |  | ||  __|:_:_[I]_:_:|__  || |  |  |  ||- |  | \n" +
                        " _|__|  ||__|__|__|_||:::::::::::::::::::::||_|__|__|__||  |__|_\n" +
                        " -|--|= ||--|--|--|-||:::::::::::::::::::::||-|--|--|--||- |--|-\n" +
                        "  jgs|- ||  |  |  | ||:::::::::::::::::::::|| |  |  |  ||= |  | \n" +
                        "~~~~~~~~~^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^~~~~~~~~~");
                print("You escaped with " + hp + " healtpoints remaining!");;
                Scanner scanner = new Scanner(System.in);
                System.out.print("#: ");
                scanner.next();
                scoreScreen(hp, difficulty);
                break;
            }
            print("Your hp is " + hp);
            if(hasShield)print("You have a shield with "+ map.shieldHp + "durability points.");
            print("You are in " + position + " there are connections to: \n");
            UnorderedListADT<String> neighbours = map.getNeighbours(position);
            String neighbourArr[] = new String[neighbours.size()];
            int k = 0;
            for (String neighbour :
                    neighbours) {
                neighbourArr[k] = neighbour;
                print((k+1) +".   " + neighbourArr[k]);
                k++;
            }

            Scanner scanner = new Scanner(System.in);

            boolean inputIsValid;
            do {


                print("\nType the name of the room to move to. \n");
                System.out.print("\"quit\" to quit, \"connections\" to list connections again\t#: ");
                String option = scanner.nextLine();
                inputIsValid = false;

                switch (option) {
                    case "quit":
                        done = true;
                        inputIsValid = true;
                        break;

                    case "connections":
                        inputIsValid = true;
                        break;

                    default:
                        break;
                }

                Integer parsedOption = parseNumeric(option);

                if(parsedOption != null){
                    if(parsedOption-1 >= 0 && parsedOption-1 < neighbourArr.length){
                        clearScreen();
                        print("You moved from " + position + " to " + neighbourArr[parsedOption-1]);
                        Double ghost = map.getEdge(position, neighbourArr[parsedOption-1]);
                        if(ghost != 0.0){
                            print(" .-.\n" +
                                    "(o o) boo!\n" +
                                    "| O \\\n" +
                                    " \\   \\\n" +
                                    "  `~~~'");
                            print("The Ghost dealt " + (ghost*difficulty) + " points of damage");
                            double realDamage;
                            if(hasShield){
                                realDamage = applyShieldDamage(ghost*difficulty);
                                if(realDamage > 0)hasShield=false;
                                print("You took "+ realDamage + "damage.");
                            }
                            else {
                                print("You took all of its damage");
                                realDamage = ghost*difficulty;
                            }
                            hp -= (realDamage);
                        }
                        if(neighbourArr[parsedOption-1].equals(map.shieldRoom)){
                            map.shieldRoom=null;
                            hasShield=true;
                            print("\\_________________/\n" +
                                    "|       | |       |\n" +
                                    "|       | |       |\n" +
                                    "|       | |       |\n" +
                                    "|_______| |_______|\n" +
                                    "|_______   _______|\n" +
                                    "|       | |       |\n" +
                                    "|       | |       |\n" +
                                    " \\      | |      /\n" +
                                    "  \\     | |     /\n" +
                                    "   \\    | |    /\n" +
                                    "    \\   | |   /\n" +
                                    "     \\  | |  /\n" +
                                    "      \\ | | /\n" +
                                    "       \\| |/\n" +
                                    "        \\_/");
                        }
                        inputIsValid = true;
                        position = neighbourArr[parsedOption-1];

                    }
                }
            } while (!inputIsValid);
        }while (!done);
    }

    private static Integer parseNumeric(String numStr){
        if (numStr == null) {
            return null;
        }
        Integer num;
        try {
            num = Integer.parseInt(numStr);
        } catch (NumberFormatException nfe) {
            return null;
        }
        return num;
    }

    /**
     * Shows all scores from all users that completed the selected
     * map ordered from highest to lowest.
     */
    private static void listScores(){
        clearScreen();
        Scanner scanner = new Scanner(System.in);
        print("HALL OF FAME:  ");

        ArrayOrderedList<Score> scores;
        try {
            scores = map.getScores();
            if(scores == null){
                print("No scores yet!");
            }else {
                Iterator<Score> reverserIter = scores.reverseIterator();
                while (reverserIter.hasNext()){
                    Score score = reverserIter.next();
                    if(score != null){
                        print("Name: " + score.name + " Difficulty: " + score.difficulty + " Score: "+ score.score);
                    }
                }
            }
        } catch (InvalidMapFormatException e) {
            print("THERE WAS AN EXCEPTION: "+ e.toString());
        }

        System.out.print("#: ");
        scanner.next();
    }

    private static double applyShieldDamage(double incomingDamage){
        double damageTaken = 0;
        if(incomingDamage>=map.shieldHp){
            damageTaken = incomingDamage-map.shieldHp;
            map.shieldHp = 0;
            print("" +
                    "...................:=@@@@   @@@@@@@@@@#*:..................\n" +
                    "..................=@@@@@@@  @@@@@@@@@@@@@+.................\n" +
                    "................*@@@@@@@@@  @@@@@@@@@@@@@@@:...............\n" +
                    "...........-@@@@@@@@@@@@@  @@@@@@@@@@@@@@@@@@@@#...........\n" +
                    "...........-@@@@@@@@@@@@@@@   #@@@@@@@@@@@@@@@@#...........\n" +
                    "...........-@@@@@@@@@@@@@@@@@@  @@@@@@@@@@@@@@@#...........\n" +
                    "...........-@@@@@@@@@@@@@@@@@@  @@@@@@@@@@@@@@@#...........\n" +
                    "............#@@@@@@@@@@@@@@@@  @@@@@@@@@@@@@@@@*...........\n" +
                    "............=@@@@@@@@@@@@@@@  @@@@@@@@@@@@@@@@@+...........\n" +
                    "............:@@@@@@@@@@@@@@  @@@@@@@@@@@@@@@@@@-...........\n" +
                    ".............@@@@@@@@@@@@@@@   @@@@@@@@@@@@@@@=............\n" +
                    ".............+@@@@@@@@@@@@@@@@@   @@@@@@@@@@@@:............\n" +
                    "..............#@@@@@@@@@@@@@@@@@  @@@@@@@@@@@=.............\n" +
                    "..............:@@@@@@@@@@@@@@@@  @@@@@@@@@@@@..............\n" +
                    "...............+@@@@@@@@@@@@@@  @@@@@@@@@@@@-..............\n" +
                    "................:@@@@@@@@@@@@  @@@@@@@@@@@@-...............\n" +
                    ".................-@@@@@@@@@@@@@  =@@@@@@@#.................\n" +
                    "...................+@@@@@@@@@@@@@  +@@@@:..................\n" +
                    ".....................+@@@@@@@@@@@@@@+-:....................\n" +
                    "........................+@@@@@@@@@#:.......................\n" +
                    "                   YOUR SHIELD BROKE :C");
        }
        else {
            print("Shield Blocked " + incomingDamage + " damage.");
            map.shieldHp-=incomingDamage;
        }
        return damageTaken;
    }

    /**
     * Saves the score, difficulty and name of the player after a manual
     * game ends.
     * @param score of the player.
     * @param difficulty of the level choosen by the player.
     */
    private static void scoreScreen(double score, int difficulty){
        Scanner scanner = new Scanner(System.in);
        print("Please write your name.");
        System.out.print("#: ");
        String name = scanner.nextLine();
        map.saveScore(name, score, difficulty);
        listScores();
    }

}
