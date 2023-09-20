import java.io.*;
import java.util.*;

    public class Simulation {
        // Variable declarations
        private static int[] experiment1Dimensions;
        private static int[] experiment1PMR;
        private static int[] experiment2Reps;
        private static int[] experiment2DPM;
        private static int[] experiment3Protocols = {4, 4, 8, 8};
        private static int[] experiment3DMR;
        private static List<Double> resultsExp1 = new ArrayList<>();
        private static List<Double> resultsExp2 = new ArrayList<>();
        private static List<Double> resultsExp3 = new ArrayList<>();


        private static void simulationRun(int experimentNumber, String protocol, int dimensions, int moves, int reps) {
            List<Integer> rawData = new ArrayList<>();

            for (int i = 0; i < reps; i++) {
                int result = 0;
                if (protocol.equals("protocol4")) {
                    result = protocol4(dimensions, moves, reps);
                } else if (protocol.equals("protocol8")) {
                    result = protocol8(dimensions, moves, reps);
                }
                rawData.add(result);
            }

            // Calculate lo, hi, and avg from rawData and add to the appropriate list
            double lo = Collections.min(rawData);
            double hi = Collections.max(rawData);
            double avg = rawData.stream().mapToDouble(Integer::doubleValue).average().orElse(0.0);

            if (experimentNumber == 1) {
                resultsExp1.add(lo);
                resultsExp1.add(hi);
                resultsExp1.add(avg);
            } else if (experimentNumber == 2) {
                resultsExp2.add(lo);
                resultsExp2.add(hi);
                resultsExp2.add(avg);
            } else if (experimentNumber == 3) {
                resultsExp3.add(lo);
                resultsExp3.add(hi);
                resultsExp3.add(avg);
            }
        }

        // Function to parse input file
        private static void parseInput() throws IOException {
            BufferedReader br = new BufferedReader(new FileReader("inputfile.txt"));
            String line;

            List<String> errors = new ArrayList<>();

            // Check and parse each line of input file
            for (int i = 1; i <= 6; i++) {
                line = br.readLine();
                String[] values = line.split(",");

                if (i == 1 && values.length == 5) {
                    // Check and store experiment 1 dimensions
                    // Add your validation logic here
                } else if (i == 2 && values.length == 3) {
                    // Check and store experiment 1 PMR
                    // Add your validation logic here
                } else if (i == 3 && values.length == 5) {
                    // Check and store experiment 2 reps
                    // Add your validation logic here
                } else if (i == 4 && values.length == 3) {
                    // Check and store experiment 2 DPM
                    // Add your validation logic here
                } else if (i == 5 && line.equals("4,4,8,8")) {
                    // Line 5 is valid
                } else if (i == 6 && values.length == 3) {
                    // Check and store experiment 3 DMR
                    // Add your validation logic here
                } else {
                    errors.add("Line " + i + " is invalid.");
                }
            }

            br.close();

            if (!errors.isEmpty()) {
                System.out.println(errors.size() + " errors found:");
                for (String error : errors) {
                    System.out.println(error);
                }
                System.exit(1);
            }
        }

        //public static void main(String[] args) throws IOException {
            System.out.println("Description of the program");

            // Parse input file
            parseInput();

            // Experiment 1
            for (int dimensions : experiment1Dimensions) {
                simulationRun(1, "protocol4", dimensions, experiment1PMR[1], experiment1PMR[2]);
            }

            // Experiment 2
            for (int reps : experiment2Reps) {
                simulationRun(2, "protocol8", experiment2DPM[0], experiment2DPM[1], reps);
            }

            // Experiment 3
            for (int protocol : experiment3Protocols) {
                simulationRun(3, "protocol4", experiment3DMR[0], experiment3DMR[1], experiment3DMR[2]);
            }

            // Generate table and output.txt
            // You can implement this part based on your requirements
        }
    }
