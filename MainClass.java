import java.util.Random;

public class MainClass {
    class Person {
        int xCoordinate;
        int yCoordinate;
        int Dimensions;

        public Person(int xCoordinate, int yCoordinate) {
            this.xCoordinate = xCoordinate;
            this.yCoordinate = yCoordinate;
        }
    }



    // Function to move the person, protocol 4 or 8
    private int move(Person person, int protocol, int Dimension) {
        Person temp = new Person(person.xCoordinate, person.yCoordinate);
        Random rand = new Random();
        int direction;
        if(protocol == 4)
            direction = rand.nextInt(4);
        if(protocol == 8)
            direction = rand.nextInt(8);

        switch (direction) {
            case 0:     //north
                if (person.yCoordinate < Dimension)
                    person.yCoordinate++;
                break;
            case 1:     //east
                if (person.xCoordinate < Dimension)
                    person.xCoordinate++;
                break;
            case 2:     //south
                if (person.yCoordinate > 0)
                    person.yCoordinate--;
                break;
            case 3:     //west
                if (person.xCoordinate > 0)
                    person.xCoordinate--;
                break;
            case 4:     //northeast
                if(person.yCoordinate < Dimension && person.xCoordinate < Dimension) {
                    person.yCoordinate++;
                    person.xCoordinate++;
                }
                break;
            case 5:     //northwest
                if(person.yCoordinate < Dimension && person.xCoordinate > 0){
                    person.yCoordinate++;
                    person.xCoordinate--;
                }
                break;
            case 6:     //southeast
                if(person.yCoordinate > 0 && person.xCoordinate < Dimension) {
                    person.yCoordinate--;
                    person.xCoordinate++;
                }
                break;
            case 7:     //southwest
                if(person.yCoordinate > 0 && person.xCoordinate > 0){
                    person.yCoordinate--;
                    person.xCoordinate--;
                }
                break;
        }
        if(protocol == 8 && person.xCoordinate == temp.xCoordinate && person.yCoordinate == temp.yCoordinate)
            move(person, protocol, Dimension);
        return 0;
    }
}
