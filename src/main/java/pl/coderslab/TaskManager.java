package pl.coderslab;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {

    public static void main(String[] args) {
        String tasksPath = "tasks.csv";
        String[][] allTasks = tasks(tasksPath);
        if (allTasks == null){
            return;
        }
        printMenu();
        System.out.println();
    }

    public static String[][] tasks(String pathToTasks) {
        File file = new File(pathToTasks);
        String singleLine = null;
        String [] tempArray = null;
        String [][] results = null;
        int counter = 0;
        try {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()){
                singleLine = scan.nextLine().strip().replace(", ", ";");
                if (counter < 1){
                    tempArray = new String[1];
                    tempArray[0] = singleLine;
                    counter++;
                } else {
                    tempArray = Arrays.copyOf(tempArray, tempArray.length + 1);
                    tempArray[tempArray.length - 1] = singleLine;
                }
            }
            results = new String[tempArray.length][1];
            for (int i = 0; i < results.length; i++){
                results[i] = tempArray[i].split(";");
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());

        }
        return results;
    }

    public static String[] createMenu(){
        return new String[]{
                ConsoleColors.BLUE_BOLD_BRIGHT + "Please select an option:",
                ConsoleColors.YELLOW_BOLD_BRIGHT + "add",
                ConsoleColors.RED_BOLD_BRIGHT + "remove",
                ConsoleColors.GREEN_BOLD_BRIGHT + "list",
                ConsoleColors.WHITE_BOLD_BRIGHT + "exit"
        };

    }

    public static void printMenu(){
        String [] menu = createMenu();
        int counter = 0;
        System.out.println();
        for (String element: menu){
            if (counter == 0) {
                System.out.println(element);
                System.out.println();
            } else {
                System.out.println(element);
            }
            counter ++;
        }
    }
}
