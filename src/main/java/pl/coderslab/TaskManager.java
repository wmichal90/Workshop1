package pl.coderslab;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TaskManager {

    public static void main(String[] args) {
        String tasksPath = "tasks.csv";
        String [][] finalListOfTasks = null;
        String[][] allTasks = tasks(tasksPath);
        if (allTasks == null){
            allTasks = runChosenOption("add", null, tasksPath);
        }
        while (true){

            printMenu();
            String chosenOption = chooseOption();
//            System.out.print("Chosen option is: " + chosenOption);
            System.out.println();
            if (chosenOption.equals("add") || chosenOption.equals("remove") || chosenOption.equals("list") ){
                allTasks = runChosenOption(chosenOption, allTasks, tasksPath);
            } else if (chosenOption.equals("exit")) {
                finalListOfTasks = runChosenOption(chosenOption, allTasks, tasksPath);
                return;

            } else {
                runChosenOption(chosenOption, allTasks, tasksPath);
            }



        }
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
            if (tempArray != null){

                results = new String[tempArray.length][1];
                for (int i = 0; i < results.length; i++){
                    results[i] = tempArray[i].split(";");
                }
            } else {
                return null;
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());

        }
        return results;
    }

    public static String chooseOption(){
        System.out.println();
        System.out.print("My choice is: ");
        Scanner input = new Scanner(System.in);
        return input.nextLine().strip();
    }

    public static String[][] runChosenOption(String input, String[][] listOfTasks, String filePath){
        return switch (input) {
            case "add" -> addTask(listOfTasks);
            case "remove" -> removeTask(listOfTasks, filePath);
            case "list" -> listTasks(listOfTasks);
            case "exit" -> exitProgram(listOfTasks, filePath);
            default -> {
                System.out.println("Please select a correct option!");
                yield listOfTasks;
            }
        };
    }

    public static String[][] addTask(String[][] listOfTasks){
        String [] singleTask = new String[3];
        Scanner readInput = new Scanner(System.in);
        System.out.print("Please add task description: ");
        singleTask[0] = readInput.nextLine().strip();
//        System.out.println();
        System.out.print("Please add task due date: ");
        singleTask[1] = readInput.nextLine().strip();
//        System.out.println();
        while (true){
            System.out.print("Is your task important: true/false: ");
            String taskImportance = readInput.nextLine().strip();
            if (taskImportance.equals("true") || taskImportance.equals("false")){
                singleTask[2] = taskImportance;
                break;
            }
        }
        if (listOfTasks != null){

            listOfTasks = Arrays.copyOf(listOfTasks, listOfTasks.length + 1);
            listOfTasks[listOfTasks.length - 1] = singleTask;
        } else{
            listOfTasks = new String[1][singleTask.length];
            listOfTasks[0] = singleTask;
        }
        return listOfTasks;

    }

    public static String[][] removeTask(String[][] listOfTasks, String tasksPath){
        if (listOfTasks.length > 0) {
            String[][] shrinkedListOfTasks = new String[listOfTasks.length - 1][1];
            int counter = 0;
            int index = 0;
            String userInput = null;
            Scanner readInput = new Scanner(System.in);


            while (true) {
                System.out.print("Please select a number to remove: ");
                userInput = readInput.nextLine().strip();
                if (StringUtils.isNumeric(userInput)) {
                    index = Integer.parseInt(userInput);
                    if (index >= listOfTasks.length) {
                        System.out.println("Please provide an integer number in the range: [0, " + (listOfTasks.length - 1) + "]");
                    } else {
                        for (int i = 0; i < listOfTasks.length; i++) {
                            if (i != index) {
                                shrinkedListOfTasks[counter] = listOfTasks[i];
                                counter++;
                            }
                        }
                        break;
                    }
                }
            }

            System.out.println("Value was successfully removed!");
            return shrinkedListOfTasks;
        }else {
            System.out.println(ConsoleColors.YELLOW + "There are no tasks in tasks manager. Please create one!");
            return runChosenOption("add", null, tasksPath);
        }
    }

    public static String[][] listTasks(String[][] listOfTasks){

        for (int i = 0; i < listOfTasks.length; i++) {
            System.out.println(i + ": " + String.join(" ", listOfTasks[i]));
        }

//        System.out.println("listTasks() function doing nothing!");
        return listOfTasks;
    }

    public static String[][] exitProgram(String[][] listOfTasks, String tasksPath)  {
        File file = new File(tasksPath);
        try (FileWriter fileWriter = new FileWriter(file)){
            for (String [] task: listOfTasks){
                fileWriter.append(String.join(", ", task)).append("\n");
            }
        } catch (IOException exc){
            System.out.println(exc.getMessage());
            System.out.println("Unable to save the tasks list properly!");
            return listOfTasks;
        }
        System.out.println(ConsoleColors.RED + "Bye, Bye");
        return listOfTasks;
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
//        int counter = 0;
        System.out.println();
        for (String element: menu){
            System.out.println(element);
//            if (counter == 0) {
//                System.out.println();
//            }
//            counter ++;
        }
    }
}
