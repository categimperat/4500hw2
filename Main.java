import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
                // It uses an if statement and take a modulus 2 of the move counter to rotate between each of the players so they alternate turns.
            } else {
                move(person2, protocol, Dimension);
            }
            if (person1.xCoordinate == person2.xCoordinate && person1.yCoordinate == person2.yCoordinate)
                // Then after the player has moved it checks if there has been a meeting, if
                // there has it breaks out of the loop.

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

    private static List experiment(int repetitions, int Dimension, int maxMoves, int protocol) {
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < repetitions; i++) {
            Person person1 = new Person(0, 0);
            Person person2 = new Person(Dimension, Dimension);
            int result = playGame(person1, person2, Dimension, maxMoves, protocol);
            data.add(result);
        }

        return data;
    }

    public static void main(String[] args) {
        int Dimension = 100;
        int maxMoves = 10_000;
        int protocol = 8;
        int repetitions = 100;
        List<Integer> data = new ArrayList<>();

        data = experiment(repetitions, Dimension, maxMoves, protocol);
        double low = Collections.min(data);
        double high = Collections.max(data);
        double average = data.stream().mapToDouble(Integer::doubleValue).average().orElse(0.0);
        resultsExp1.add(low);
        resultsExp1.add(high);
        resultsExp1.add(average);

        data = experiment(repetitions, Dimension, maxMoves, protocol);
        low = Collections.min(data);
        high = Collections.max(data);
        average = data.stream().mapToDouble(Integer::doubleValue).average().orElse(0.0);
        resultsExp2.add(low);
        resultsExp2.add(high);
        resultsExp2.add(average);

        data = experiment(repetitions, Dimension, maxMoves, protocol);
        low = Collections.min(data);
        high = Collections.max(data);
        average = data.stream().mapToDouble(Integer::doubleValue).average().orElse(0.0);
        resultsExp3.add(low);
        resultsExp3.add(high);
        resultsExp3.add(average);

    }

}
