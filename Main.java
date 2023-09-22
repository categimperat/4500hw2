import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

class Person {
    int xCoordinate;
    int yCoordinate;

    public Person(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

}

public class Main {
    private static List<Double> resultsExp1 = new ArrayList<>();
    private static List<Double> resultsExp2 = new ArrayList<>();
    private static List<Double> resultsExp3 = new ArrayList<>();
    private static int[] experiment1Dimensions = new int[5];
    private static int[] experiment1PMR = new int[3];
    private static int[] experiment2Reps = new int[5];
    private static int[] experiment2DPM = new int[3];
    private static int[] experiment3Protocols = { 4, 4, 8, 8 };
    private static int[] experiment3DMR = new int[3];

    private static void parseInput() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("inputfile.txt"));

        String line;
        List<String> errors = new ArrayList<>();

        // // check number of lines in file
        // // credit:
        // // https://stackoverflow.com/questions/1277880/how-can-i-get-the-count-of-line-in-a-file-in-an-efficient-way
        // BufferedReader sizeCheck = new BufferedReader(new FileReader("inputfile.txt"));
        // int lines = 0;
        // while (sizeCheck.readLine() != null)
        //     lines++;
        // if (lines > 6) {
        //     errors.add("Input file contains too many lines.");
        // }
        // sizeCheck.close();

        try (Stream<String> fileStream = Files.lines(Paths.get("inputfile.txt"))) {
        int noOfLines = (int) fileStream.count();
        System.out.println(noOfLines);
    }

        // Check and parse each line of input file
        for (

                int i = 1; i <= 6; i++) {
            line = br.readLine();
            String[] values;

            // regex matching logic credit:
            // https://www.geeksforgeeks.org/how-to-check-if-string-contains-only-digits-in-java/
            String regex = "[0-9]+";
            Pattern p = Pattern.compile(regex);

            // check every line to see if it is null. if true, this indicates a file with <6
            // lines. if false, split line between commas.
            if (line == null) {
                errors.add("Incomplete input file.");
                break;
            } else {
                // if line contains whitespace, add that to errors. then, eliminate all
                // whitespace and keep analyzing file for errors.
                if (line.contains(" ")) {
                    errors.add("Line " + i + " contains whitespace.");
                    line = line.replaceAll(" ", "");
                }
                values = line.split(",");
            }

            if (i == 1 && values.length == 5) {
                // Check and store experiment 1 dimensions
                for (int j = 0; j < values.length; j++) {
                    Matcher m = p.matcher(values[j]);
                    if ((!m.matches())) {
                        errors.add("Line " + i + " contains an illegal character.");
                        break;
                    } else {
                        if (Integer.parseInt(values[j]) > 100) {
                            errors.add("Line " + i + " contains a dimension greater than 100.");
                        } else {
                            if (j == 0) {
                                experiment1Dimensions[j] = Integer.parseInt(values[j]);
                            } else if ((Integer.parseInt(values[j]) < Integer.parseInt(values[j - 1]))) {
                                errors.add("Values in line " + i + " not ordered in ascending order.");
                            } else {
                                experiment1Dimensions[j] = Integer.parseInt(values[j]);
                            }
                        }
                    }
                }

            } else if (i == 2 && values.length == 3) {
                // Check and store experiment 1 PMR
                for (int j = 0; j < values.length; j++) {
                    Matcher m = p.matcher(values[j]);
                    if (!m.matches()) {
                        errors.add("Line " + i + " contains an illegal character.");
                        break;
                    } else {
                        switch (j) {
                            case 0:
                                if ((Integer.parseInt(values[j]) != 4) && (Integer.parseInt(values[j]) != 8)) {
                                    errors.add("Line " + i + " contains an incorrect protocol code.");
                                    break;
                                } else {
                                    experiment1PMR[j] = Integer.parseInt(values[j]);
                                }
                            case 1:
                                if (Integer.parseInt(values[j]) > 1000000) {
                                    errors.add("Line " + i + " contains a number of moves greater than 1000000.");
                                    break;
                                } else {
                                    experiment1PMR[j] = Integer.parseInt(values[j]);
                                }
                            case 2:
                                if (Integer.parseInt(values[j]) > 100000) {
                                    errors.add("Line " + i + " contains a number of repetitions greater than 100000.");
                                    break;
                                } else {
                                    experiment1PMR[j] = Integer.parseInt(values[j]);
                                }
                        }
                    }
                }

            } else if (i == 3 && values.length == 5) {
                // Check and store experiment 2 reps
                for (int j = 0; j < values.length; j++) {
                    Matcher m = p.matcher(values[j]);
                    if ((!m.matches())) {
                        errors.add("Line " + i + " contains an illegal character.");
                        break;
                    } else {
                        if (Integer.parseInt(values[j]) > 100000) {
                            errors.add("Line " + i + " contains a number of repetitions greater than 100000.");
                        } else {
                            if (j == 0) {
                                experiment2Reps[j] = Integer.parseInt(values[j]);
                            } else if ((Integer.parseInt(values[j]) < Integer.parseInt(values[j - 1]))) {
                                errors.add("Values in line " + i + " not ordered in ascending order.");
                            } else {
                                experiment2Reps[j] = Integer.parseInt(values[j]);
                            }
                        }
                    }
                }

            } else if (i == 4 && values.length == 3) {
                // Check and store experiment 2 DPM
                for (int j = 0; j < values.length; j++) {
                    Matcher m = p.matcher(values[j]);
                    if (!m.matches()) {
                        errors.add("Line " + i + " contains an illegal character.");
                        break;
                    } else {
                        switch (j) {
                            case 1:
                                if ((Integer.parseInt(values[j]) != 4) && (Integer.parseInt(values[j]) != 8)) {
                                    errors.add("Line " + i + " contains an incorrect protocol code.");
                                    break;
                                } else {
                                    experiment2DPM[j] = Integer.parseInt(values[j]);
                                }
                            case 2:
                                if (Integer.parseInt(values[j]) > 1000000) {
                                    errors.add("Line " + i + " contains a number of moves greater than 1000000.");
                                    break;
                                } else {
                                    experiment2DPM[j] = Integer.parseInt(values[j]);
                                }
                            case 0:
                                if (Integer.parseInt(values[j]) > 100) {
                                    errors.add("Line " + i + " contains a dimension greater than 100.");
                                    break;
                                } else {
                                    experiment2DPM[j] = Integer.parseInt(values[j]);
                                }
                        }
                    }
                }

            } else if (i == 5 && values.length == 4) {
                // check if exp 3 protocols are correctly specified
                for (int j = 0; j < values.length; j++) {
                    Matcher m = p.matcher(values[j]);
                    if ((!m.matches())) {
                        errors.add("Line " + i + " contains an illegal character.");
                        break;
                    } else {
                        if (!line.equals("4,4,8,8")) {
                            errors.add("Line " + i + " contains an illegal character.");
                        }
                    }
                }

            } else if (i == 6 && values.length == 3) {
                // Check and store experiment 3 DMR
                for (int j = 0; j < values.length; j++) {
                    Matcher m = p.matcher(values[j]);
                    if (!m.matches()) {
                        errors.add("Line " + i + " contains an illegal character.");
                        break;
                    } else {
                        switch (j) {
                            case 0:
                                if (Integer.parseInt(values[j]) > 100) {
                                    errors.add("Line " + i + " contains a dimension greater than 100.");
                                    break;
                                } else {
                                    experiment3DMR[j] = Integer.parseInt(values[j]);
                                }
                            case 1:
                                if (Integer.parseInt(values[j]) > 1000000) {
                                    errors.add("Line " + i + " contains a number of moves greater than 1000000.");
                                    break;
                                } else {
                                    experiment3DMR[j] = Integer.parseInt(values[j]);
                                }
                            case 2:
                                if (Integer.parseInt(values[j]) > 100000) {
                                    errors.add("Line " + i + " contains a number of repetitions greater than 100000.");
                                    break;
                                } else {
                                    experiment3DMR[j] = Integer.parseInt(values[j]);
                                }
                        }
                    }
                }

            } else {
                errors.add("Line " + i + " is the wrong length.");
            }
        }

        br.close();

        // print any problematic lines, if there are any.
        if (!errors.isEmpty()) {
            System.out.println(errors.size() + " error(s) found:");
            for (String error : errors) {
                System.out.println(error);
            }
            System.exit(1);
        } else {
            System.out.println("No errors found.");
        }
    }

    // Function to move the person, protocol 4 or 8
    private static void move(Person person, int protocol, int Dimension) {
        Person temp = new Person(person.xCoordinate, person.yCoordinate);
        Random rand = new Random();
        int direction = 0;
        if (protocol == 4)
            direction = rand.nextInt(4);
        if (protocol == 8)
            direction = rand.nextInt(8);

        switch (direction) {
            case 0: // north
                if (person.yCoordinate < Dimension)
                    person.yCoordinate++;
                break;
            case 1: // east
                if (person.xCoordinate < Dimension)
                    person.xCoordinate++;
                break;
            case 2: // south
                if (person.yCoordinate > 0)
                    person.yCoordinate--;
                break;
            case 3: // west
                if (person.xCoordinate > 0)
                    person.xCoordinate--;
                break;
            case 4: // northeast
                if (person.yCoordinate < Dimension && person.xCoordinate < Dimension) {
                    person.yCoordinate++;
                    person.xCoordinate++;
                }
                break;
            case 5: // northwest
                if (person.yCoordinate < Dimension && person.xCoordinate > 0) {
                    person.yCoordinate++;
                    person.xCoordinate--;
                }
                break;
            case 6: // southeast
                if (person.yCoordinate > 0 && person.xCoordinate < Dimension) {
                    person.yCoordinate--;
                    person.xCoordinate++;
                }
                break;
            case 7: // southwest
                if (person.yCoordinate > 0 && person.xCoordinate > 0) {
                    person.yCoordinate--;
                    person.xCoordinate--;
                }
                break;
        }
        if (protocol == 8 && person.xCoordinate == temp.xCoordinate && person.yCoordinate == temp.yCoordinate) {
            move(person, protocol, Dimension);
        }
    }

    // This function plays the game.
    // This function actually executes the moves and gives the output.
    private static int playGame(Person person1, Person person2, int Dimension, int maxMoves, int protocol) {

        int counter = 0;

        while (counter < maxMoves) {
            if (counter % 2 == 0) {
                move(person1, protocol, Dimension);
                // It uses an if statement and take a modulus 2 of the move counter to rotate
                // between each of the players so they alternate turns.
            } else {
                move(person2, protocol, Dimension);
            }
            if (person1.xCoordinate == person2.xCoordinate && person1.yCoordinate == person2.yCoordinate)
                // Then after the player has moved it checks if there has been a meeting, if
                // there has, it breaks out of the loop.

                break;
            counter++; // If there isn't a meeting then it adds to the counter and repeats the loop.
        }

        // This section checks if the meeting occurred or not and prints the appropriate
        // message.

        if (counter < maxMoves)
            return ++counter;
        else
            return counter;
    }

    // This function runs the experiment and logs the outcome into a dad list.
    private static List<Integer> experiment(int repetitions, int Dimension, int maxMoves, int protocol) {
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < repetitions; i++) {
            Person person1 = new Person(0, 0);
            Person person2 = new Person(Dimension, Dimension);
            int result = playGame(person1, person2, Dimension, maxMoves, protocol);
            data.add(result);
        }

        return data;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("The program takes an input file which describes the parameters of 3 different experiments."
                +
                "The program takes the input file and parses each of the parameters for the experiments, then it runs" +
                "each experiment according to those parameters.  It will take all the results, calculate the high, low,"
                +
                " and average values of each experiment, then it will log those results in an output file.");
        double low;
        double high;
        double average;
        parseInput();
        List<Integer> data = new ArrayList<>();

        // Running experiment 1
        for (int i = 0; i < 5; i++) {
            data = experiment(experiment1PMR[2], experiment1Dimensions[i], experiment1PMR[1], experiment1PMR[0]);
            low = Collections.min(data);
            high = Collections.max(data);
            average = data.stream().mapToDouble(Integer::doubleValue).average().orElse(0.0);
            resultsExp1.add(low);
            resultsExp1.add(high);
            resultsExp1.add(average);
        }

        // Running Experiment 2
        for (int i = 0; i < 5; i++) {
            data = experiment(experiment2Reps[i], experiment2DPM[0], experiment2DPM[2], experiment2DPM[1]);
            low = Collections.min(data);
            high = Collections.max(data);
            average = data.stream().mapToDouble(Integer::doubleValue).average().orElse(0.0);
            resultsExp2.add(low);
            resultsExp2.add(high);
            resultsExp2.add(average);
        }

        // Running Experiment 3
        for (int i = 0; i < 4; i++) {
            data = experiment(experiment3DMR[2], experiment3DMR[0], experiment3DMR[1], experiment3Protocols[i]);
            low = Collections.min(data);
            high = Collections.max(data);
            average = data.stream().mapToDouble(Integer::doubleValue).average().orElse(0.0);
            resultsExp3.add(low);
            resultsExp3.add(high);
            resultsExp3.add(average);
        }

    }

}
