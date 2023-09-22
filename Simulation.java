import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Simulation {
    // Variable declarations
    private static int[] experiment1Dimensions = new int[5];
    private static int[] experiment1PMR = new int[3];
    private static int[] experiment2Reps = new int[5];
    private static int[] experiment2DPM = new int[3];
    private static int[] experiment3Protocols = { 4, 4, 8, 8 };
    private static int[] experiment3DMR = new int[3];
    private static List<Double> resultsExp1 = new ArrayList<>();
    private static List<Double> resultsExp2 = new ArrayList<>();
    private static List<Double> resultsExp3 = new ArrayList<>();

    // I think matt worked on this functionality.
    // TODO: have matt integrate what he wrote into this file.

    // private static void simulationRun(int experimentNumber, String protocol, int
    // dimensions, int moves, int reps) {
    // List<Integer> rawData = new ArrayList<>();

    // for (int i = 0; i < reps; i++) {
    // int result = 0;
    // if (protocol.equals("protocol4")) {
    // result = protocol4(dimensions, moves, reps);
    // } else if (protocol.equals("protocol8")) {
    // result = protocol8(dimensions, moves, reps);
    // }
    // rawData.add(result);
    // }

    // // Calculate lo, hi, and avg from rawData and add to the appropriate list
    // double lo = Collections.min(rawData);
    // double hi = Collections.max(rawData);
    // double avg =
    // rawData.stream().mapToDouble(Integer::doubleValue).average().orElse(0.0);

    // if (experimentNumber == 1) {
    // resultsExp1.add(lo);
    // resultsExp1.add(hi);
    // resultsExp1.add(avg);
    // } else if (experimentNumber == 2) {
    // resultsExp2.add(lo);
    // resultsExp2.add(hi);
    // resultsExp2.add(avg);
    // } else if (experimentNumber == 3) {
    // resultsExp3.add(lo);
    // resultsExp3.add(hi);
    // resultsExp3.add(avg);
    // }
    // }

    // Function to parse input file
    private static void parseInput() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("inputfile.txt"));
        String line;

        List<String> errors = new ArrayList<>();

        // Check and parse each line of input file
        for (int i = 1; i <= 6; i++) {
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

    public static void main(String[] args) throws IOException {
        System.out.println("This is a description of the program.\n");

        parseInput();
        // at this point, all six arrays above defined in the top are populated with
        // info necessary to run experiments.

        // // Experiment 1
        // for (int dimensions : experiment1Dimensions) {
        // simulationRun(1, "protocol4", dimensions, experiment1PMR[1],
        // experiment1PMR[2]);
        // }

        // // Experiment 2
        // for (int reps : experiment2Reps) {
        // simulationRun(2, "protocol8", experiment2DPM[0], experiment2DPM[1], reps);
        // }

        // // Experiment 3
        // for (int protocol : experiment3Protocols) {
        // simulationRun(3, "protocol4", experiment3DMR[0], experiment3DMR[1],
        // experiment3DMR[2]);
        // }

        // TODO: marielle needs to implement this.
        // Generate table and output.txt
        // You can implement this part based on your requirements
    }
}
