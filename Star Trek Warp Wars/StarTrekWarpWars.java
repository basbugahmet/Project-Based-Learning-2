import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class StarTrekWarpWars {

    public StarTrekWarpWars() throws Exception {


        Queue inputQueue = new Queue(1000);
        Object[][] arr = new Object[23][55];
        readLine("maze.txt", arr);

        for (int i = 0; i < inputQueue.getCapacityOfQueue(); i++) {
            inputQueue.enqueue(randomGenerator());
        }

        Movement myGame = new Movement(arr, inputQueue);

    }

    private static void readLine(String txtName, Object[][] arr) {
        File file = new File(txtName);
        Scanner input = null;
        int line = 0;
        try {
            input = new Scanner(file);
            while (input.hasNextLine()) {
                String str = input.nextLine();
                String[] splitted = str.split("");
                for(int i = 0;i<splitted.length;i++){
                    arr[line][i] = splitted[i];
                }
                line++;
            }

        } catch (IOException exp) {
            exp.printStackTrace();
        }

        input.close();

    }

    public static Object randomGenerator() {

        Random random = new Random();
        double chance = random.nextDouble();

        if (chance < 0.025) {
            return new Treasure("*");
        } else if (chance < 0.075) {
            return new Treasure("=");
        }else if (chance < 0.125) {
            return new Treasure("C");
        }
        else if (chance < 0.225) {
            return new Treasure("5");
        } else if (chance < 0.350) {
            return new Treasure("4");
        } else if (chance < 0.50) {
            return new Treasure("3");
        } else if (chance < 0.7) {
            return new Treasure("2");
        } else if (chance < 1) {
            return new Treasure("1");
        }

        return null;
    }

}
