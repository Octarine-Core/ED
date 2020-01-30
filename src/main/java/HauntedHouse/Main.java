package HauntedHouse;
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
    static String position;
    static int currentHP;

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
        clearScreen();
        print("Loaded map is " + selectedMap +"" + "\n\n" +
                "Select the game mode: \n\n" +
                "[A]utomatic: The game will play itself for you)\n" +
                "[M]anual: You will play manually, and can see the entire map\n" +
                "[B]lind: You will play manually, but can only see into the rooms connected to your room");
        Scanner scanner = new Scanner(System.in);
        System.out.print("#: ");
        char option = Character.toLowerCase(scanner.next().charAt(0));
        switch (option){
            case 'a':
                autoPlay();
                break;
            default:
                return;
        }
    }

    public static void autoPlay(){
        clearScreen();
        print("The shortest path to the exit is:" +
                "\n");
        Iterator iter = map.iteratorShortestPath("entrada", "exterior");
        while (iter.hasNext()){
            print(iter.next());
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("#: ");
        char option = Character.toLowerCase(scanner.next().charAt(0));

        switch (option){
            default:
                return;
        }
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

     public static void gameLoop(Boolean blind){

        String position = "entrada";
        int hp = map.
    }

}
