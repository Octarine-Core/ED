package HauntedHouse;
import ListsAndIterators.ArrayOrderedList;
import ListsAndIterators.ArrayUnorderedList;
import ListsAndIterators.UnorderedListADT;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    static void aboutGame(){
        print("about game");
        Scanner scanner = new Scanner(System.in);

        System.out.print("write anything to continue #: ");
        scanner.next();

    }

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
            option = scanner.nextInt();
        }while (option<0 || option > maps.length);
        if(option==0){
            mainMenu();
            return;
        }
        selectedMap = maps[option-1];
        map = new Map("./src/main/resources/" + selectedMap);
        gameModeMenu();
    }

    public static void gameModeMenu(){
        boolean done = false;
        do {
            clearScreen();
            print("Loaded map is " + selectedMap + "" + "\n\n" +
                    "Select the game mode: \n\n" +
                    "[A]utomatic: The game will play itself for you\n" +
                    "[M]anual: You will play manually, and can see the entire map\n" +
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

    public static void autoPlay(int difficulty){
        clearScreen();
        print("The shortest path to the exit is:" +
                "\n");
        Iterator iter = map.iteratorShortestPath("entrada", "exterior");
        while (iter.hasNext()){
            System.out.print(iter.next());
            if(iter.hasNext()){
                System.out.print(" -->  ");
            }
        }
        print("\n");
        Double shortestPathWeight = map.shortestPathWeight("entrada", "exterior");
        print("You started with " + map.healthPoints + " HP, took " + shortestPathWeight*difficulty
        + " points of damage, leaving you with a total final score of " + (map.healthPoints - shortestPathWeight));
        Scanner scanner = new Scanner(System.in);
        System.out.print("#: ");
        scanner.next();

    }

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

    public static void gameLoop(int difficulty){

        String position = "entrada";
        Double hp = map.healthPoints;
        boolean done = false;
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
            print("You are in " + position + " there are connections to: \n");
            UnorderedListADT<String> neighbours = map.getNeighbours(position);
            for (String neighbour :
                    neighbours) {
                print(neighbour);
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

                if(!inputIsValid && neighbours.contains(option)){
                    clearScreen();
                    print("You moved from " + position + " to " + option);
                    Double ghost = map.getEdge(position, option);
                    if(ghost != 0.0){
                        print(" .-.\n" +
                                "(o o) boo!\n" +
                                "| O \\\n" +
                                " \\   \\\n" +
                                "  `~~~'");
                        print("You took " + (ghost*difficulty) + " points of damage");
                    };

                    hp -= (ghost*difficulty);
                    inputIsValid = true;
                    position = option;
                }


            } while (!inputIsValid);


        }while (!done);
    }

    private static void listScores(){
        clearScreen();
        Scanner scanner = new Scanner(System.in);
        print("HALL OF FAME:  ");
        if(map.getScores() == null){
            print("No scores yet!");
        }else {
            Iterator<Score> reverserIter = ((ArrayOrderedList)map.getScores()).reverseIterator();

            while (reverserIter.hasNext()){
                Score score = reverserIter.next();
                if(score != null){
                    print("Name: " + score.name + " Difficulty: " + score.difficulty + " Score: "+ score.score);
                }
            }
        }

        System.out.print("#: ");
        scanner.next();
    }

    private static void scoreScreen(double score, int difficulty){
        Scanner scanner = new Scanner(System.in);
        print("Please write your name.");
        System.out.print("#: ");
        String name = scanner.nextLine();
        map.saveScore(name, score, difficulty);
        listScores();
    }

}
