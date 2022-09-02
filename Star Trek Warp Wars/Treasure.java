import java.lang.reflect.Array;
import java.util.*;

public class Treasure {

    boolean isStatic = true;
    public String value = null;
    public int treasurePointForPlayer;
    public int treasurePointForComputer;
    public boolean caught = false;
    public int trapTime = 0;



    public int[] target = new int[2];
    public LinkedList closeTargetRota = new LinkedList();

    public int xCom;
    public int yCom;

    public Treasure(String value) {
        this.value = value;

        if(Objects.equals(value, "4") || Objects.equals(value, "5")) {
            this.isStatic = false;
        }

        if(Objects.equals(value, "1")){
            treasurePointForPlayer = 1;
            treasurePointForComputer = treasurePointForPlayer * 2;
        }
        else if(Objects.equals(value, "2")){
            treasurePointForPlayer = 5;
            treasurePointForComputer = treasurePointForPlayer * 2;
        }
        else if(Objects.equals(value, "3")){
            treasurePointForPlayer = 15;
            treasurePointForComputer = treasurePointForPlayer * 2;
        }
        else if(Objects.equals(value, "4")){
            treasurePointForPlayer = 50;
            treasurePointForComputer = treasurePointForPlayer * 2;
        }
        else if(Objects.equals(value, "5")){
            treasurePointForPlayer = 150;
            treasurePointForComputer = treasurePointForPlayer * 2;
        }
        else if(Objects.equals(value, "=")){
            treasurePointForPlayer = 0;
            treasurePointForComputer = 300;
        }
        else if(Objects.equals(value, "*")){
            treasurePointForPlayer = 0;
            treasurePointForComputer = 300;
        }
        else if(Objects.equals(value, "C")){
            treasurePointForPlayer = 300;
            treasurePointForComputer = 0;
        }
    }


    public void moveNumbers(Object[][] arr, int i, int j, String number) throws InterruptedException {
        Random random = new Random();

        int randomDirection = random.nextInt(4);

        if (randomDirection == 0) { //left
            if (Objects.equals(arr[i][j - 1], " ")) {
                arr[i][j] = " ";
                arr[i][j - 1] = this;
            }
        } else if (randomDirection == 1) { //right
            if (Objects.equals(arr[i][j + 1], " ")) {
                arr[i][j] = " ";
                arr[i][j + 1] = this;
            }
        } else if (randomDirection == 2) { //up
            if (Objects.equals(arr[i - 1][j], " ")) {
                arr[i][j] = " ";
                arr[i - 1][j] = this;
            }
        } else if (randomDirection == 3) { //down
            if (Objects.equals(arr[i + 1][j], " ")) {
                arr[i][j] = " ";
                arr[i + 1][j] = this;
            }
        }
    }




    public void moveComputer(Object[][] arr, Computer computer, Player player) throws InterruptedException {

        int direction = 5;
        if (!closeTargetRota.isEmpty()) {
            direction = (int) closeTargetRota.get(closeTargetRota.size()-1);
            closeTargetRota.remove(closeTargetRota.size()-1);

            if (direction == 0) {//left
                try{
                    if (Objects.equals(arr[yCom][xCom - 1], " ")) {
                        arr[yCom][xCom] = " ";
                        arr[yCom][xCom - 1] = this;
                        if (xCom - 1 != -1) {
                            xCom--;
                        }
                    } else if (Objects.equals(((Treasure) arr[yCom][xCom-1]).value, "1")
                            || Objects.equals(((Treasure) arr[yCom][xCom-1]).value, "2") || Objects.equals(((Treasure) arr[yCom][xCom-1]).value, "3") ||
                            Objects.equals(((Treasure) arr[yCom][xCom-1]).value, "4") || Objects.equals(((Treasure) arr[yCom][xCom-1]).value, "5") ||
                            Objects.equals(((Treasure) arr[yCom][xCom-1]).value, "*") || Objects.equals(((Treasure) arr[yCom][xCom-1]).value, "=")) {
                        arr[yCom][xCom] = " ";
                        Computer.score = Computer.score + ((Treasure) arr[yCom][xCom-1]).treasurePointForComputer;
                        arr[yCom][xCom - 1] = this;
                        closeTargetRota.clear();
                        if (xCom - 1 != -1) {
                            xCom--;
                        }
                    }
                }catch (ClassCastException err){
                    if(Objects.equals((arr[yCom][xCom-1]), 'P')){
                        arr[yCom][xCom - 1] = (arr[yCom][xCom]);
                        arr[yCom][xCom] = " ";
                        player.lives--;
                        Movement.playerLostItsLife = true;
                        if (xCom - 1 != -1) {
                            xCom--;
                        }
                    }

                    else if (Objects.equals(arr[yCom][xCom - 1], "#")) {
                        closeTargetRota.clear();
                        closeTargetRota.add(3);
                        closeTargetRota.add(3);
                        closeTargetRota.add(3);
                        closeTargetRota.add(3);
                        closeTargetRota.add(3);
                        closeTargetRota.add(1);
                        closeTargetRota.add(1);
                        closeTargetRota.add(2);
                        closeTargetRota.add(2);
                        moveComputer(arr,computer,player);
                        closeTargetRota.clear();
                        chooseTargetForComputer(arr);
                    }
                }

            } else if (direction == 1) {//right
                try{
                    if (Objects.equals(arr[yCom][xCom + 1], " ")) {
                        arr[yCom][xCom] = " ";
                        arr[yCom][xCom + 1] = this;
                        if (xCom + 1 != 55) {
                            xCom++;
                        }
                    } else if (Objects.equals(((Treasure) arr[yCom][xCom+1]).value, "1")
                            || Objects.equals(((Treasure) arr[yCom][xCom+1]).value, "2") || Objects.equals(((Treasure) arr[yCom][xCom+1]).value, "3") ||
                            Objects.equals(((Treasure) arr[yCom][xCom+1]).value, "4") || Objects.equals(((Treasure) arr[yCom][xCom+1]).value, "5") ||
                            Objects.equals(((Treasure) arr[yCom][xCom+1]).value, "*") || Objects.equals(((Treasure) arr[yCom][xCom+1]).value, "=")) {
                        arr[yCom][xCom] = " ";
                        Computer.score = Computer.score + ((Treasure) arr[yCom][xCom+1]).treasurePointForComputer;
                        arr[yCom][xCom + 1] = this;
                        closeTargetRota.clear();
                        if (xCom + 1 != 55) {
                            xCom++;
                        }
                    }

                }catch (ClassCastException err){

                    if(Objects.equals((arr[yCom][xCom+1]), 'P')){
                        arr[yCom][xCom + 1] = (arr[yCom][xCom]);
                        arr[yCom][xCom] = " ";
                        player.lives--;
                        Movement.playerLostItsLife = true;
                        if (xCom + 1 != 55) {
                            xCom++;
                        }
                    }

                    else if (Objects.equals(arr[yCom][xCom + 1], "#")) {
                        closeTargetRota.clear();
                        closeTargetRota.add(2);
                        closeTargetRota.add(2);
                        closeTargetRota.add(2);
                        closeTargetRota.add(2);
                        closeTargetRota.add(2);
                        closeTargetRota.add(0);
                        closeTargetRota.add(0);
                        closeTargetRota.add(3);
                        closeTargetRota.add(3);
                        closeTargetRota.add(3);
                        moveComputer(arr,computer,player);
                        closeTargetRota.clear();
                        chooseTargetForComputer(arr);
                    }
                }
            }

            else if (direction == 2) {//up
                try{
                    if (Objects.equals(arr[yCom-1][xCom], " ")) {
                        arr[yCom][xCom] = " ";
                        arr[yCom-1][xCom] = this;
                        if (yCom - 1 != -1) {
                            yCom--;
                        }
                    } else if (Objects.equals(((Treasure) arr[yCom-1][xCom]).value, "1")
                            || Objects.equals(((Treasure) arr[yCom-1][xCom]).value, "2") || Objects.equals(((Treasure) arr[yCom-1][xCom]).value, "3") ||
                            Objects.equals(((Treasure) arr[yCom-1][xCom]).value, "4") || Objects.equals(((Treasure) arr[yCom-1][xCom]).value, "5") ||
                            Objects.equals(((Treasure) arr[yCom-1][xCom]).value, "*") || Objects.equals(((Treasure) arr[yCom-1][xCom]).value, "=")) {
                        arr[yCom][xCom] = " ";
                        Computer.score = Computer.score + ((Treasure) arr[yCom-1][xCom]).treasurePointForComputer;
                        arr[yCom-1][xCom] = this;
                        closeTargetRota.clear();
                        if (yCom - 1 != -1) {
                            yCom--;
                        }
                    }

                }catch (ClassCastException err){

                    if(Objects.equals((arr[yCom-1][xCom]), 'P')){
                        arr[yCom-1][xCom] = (arr[yCom][xCom]);
                        arr[yCom][xCom] = " ";
                        player.lives--;
                        Movement.playerLostItsLife = true;
                        if (yCom - 1 != -1) {
                            yCom--;
                        }

                    }

                   else if (Objects.equals(arr[yCom-1][xCom], "#")) {
                        closeTargetRota.clear();
                        closeTargetRota.add(0);
                        closeTargetRota.add(0);
                        closeTargetRota.add(0);
                        closeTargetRota.add(0);
                        closeTargetRota.add(0);
                        closeTargetRota.add(3);
                        closeTargetRota.add(3);
                        closeTargetRota.add(3);
                        closeTargetRota.add(1);
                        closeTargetRota.add(1);
                        moveComputer(arr,computer,player);
                        closeTargetRota.clear();
                        chooseTargetForComputer(arr);
                    }

                }
            }

           else if (direction == 3) {//down
                try{
                    if (Objects.equals(arr[yCom+1][xCom], " ")) {
                        arr[yCom][xCom] = " ";
                        arr[yCom+1][xCom] = this;
                        if (yCom + 1 != 23) {
                            yCom++;
                        }
                    } else if (Objects.equals(((Treasure) arr[yCom+1][xCom]).value, "1")
                            || Objects.equals(((Treasure) arr[yCom+1][xCom]).value, "2") || Objects.equals(((Treasure) arr[yCom+1][xCom]).value, "3") ||
                            Objects.equals(((Treasure) arr[yCom+1][xCom]).value, "4") || Objects.equals(((Treasure) arr[yCom+1][xCom]).value, "5") ||
                            Objects.equals(((Treasure) arr[yCom+1][xCom]).value, "*") || Objects.equals(((Treasure) arr[yCom+1][xCom]).value, "=")) {
                        arr[yCom][xCom] = " ";
                        Computer.score = Computer.score + ((Treasure) arr[yCom+1][xCom]).treasurePointForComputer;
                        arr[yCom+1][xCom] = this;
                        closeTargetRota.clear();
                        if (yCom + 1 != 23) {
                            yCom++;
                        }
                    }
                }catch (ClassCastException err){

                    if(Objects.equals((arr[yCom+1][xCom]), 'P')){
                        arr[yCom+1][xCom] = (arr[yCom][xCom]);
                        arr[yCom][xCom] = " ";
                        player.lives--;
                        Movement.playerLostItsLife = true;
                        if (yCom + 1 != 23) {
                            yCom++;
                        }
                    }

                    else if (Objects.equals(arr[yCom+1][xCom], "#")) {
                        closeTargetRota.clear();
                        closeTargetRota.add(1);
                        closeTargetRota.add(1);
                        closeTargetRota.add(1);
                        closeTargetRota.add(1);
                        closeTargetRota.add(1);
                        closeTargetRota.add(2);
                        closeTargetRota.add(2);
                        closeTargetRota.add(0);
                        closeTargetRota.add(0);
                        moveComputer(arr,computer,player);
                        closeTargetRota.clear();
                        chooseTargetForComputer(arr);
                    }
                }

            }

        }
    }


    public void chooseTargetForComputer(Object[][] arr) throws InterruptedException {

        int px = 0;
        int py = 0;

        double minDistance = 100000;
        double distance;
        for (int yOtherObjects = 0; yOtherObjects < 23; yOtherObjects++) {
            for (int xOtherObjects = 0; xOtherObjects < 55; xOtherObjects++) {
                try{

                    if ( Objects.equals(((Treasure)arr[yOtherObjects][xOtherObjects]).value, "1")
                            || Objects.equals(((Treasure)arr[yOtherObjects][xOtherObjects]).value, "2") || Objects.equals(((Treasure)arr[yOtherObjects][xOtherObjects]).value, "3")
                            || Objects.equals(((Treasure)arr[yOtherObjects][xOtherObjects]).value, "*") || Objects.equals(((Treasure)arr[yOtherObjects][xOtherObjects]).value, "=")
                    ||Objects.equals((arr[yOtherObjects][xOtherObjects]), 'P')) {
                        distance = Math.sqrt((xCom - xOtherObjects) * (xCom - xOtherObjects) + (yCom - yOtherObjects) * (yCom - yOtherObjects));
                        if (distance < minDistance) {
                            minDistance = distance;
                            px = xOtherObjects;
                            py = yOtherObjects;
                        }
                    }
                }catch(ClassCastException ignored){
                }
            }
        }

        target[0] = py;
        target[1] = px;

        for (int i = 0; i < Math.abs(px - xCom); i++) {
            if (px - xCom > 0) {
                closeTargetRota.add(1);
            }
            if (px - xCom < 0) {
                closeTargetRota.add(0);
            }
        }

        for (int i = 0; i < Math.abs(py - yCom); i++) {
            if (py - yCom > 0) {
                closeTargetRota.add(3);
            }
            if (py - yCom < 0) {
                closeTargetRota.add(2);
            }

        }

        Collections.shuffle(closeTargetRota);


    }

    public static void trapDevice(Object[][] arr) {

        for (int i = 0; i < 23; i++) {
            for (int j = 0; j < 55; j++) {
                try {
                    if (Objects.equals(((Treasure) arr[i][j]).value, "=")) {
                        try {
                            if (Objects.equals(((Treasure) arr[i + 1][j]).value, "C")) {
                                ((Treasure) arr[i + 1][j]).caught = true;
                            }
                        } catch (ClassCastException ignored) {

                        }

                        try {
                            if (Objects.equals(((Treasure) arr[i][j + 1]).value, "C")) {
                                ((Treasure) arr[i][j + 1]).caught = true;
                            }
                        } catch (ClassCastException ignored) {

                        }

                        try {
                            if (Objects.equals(((Treasure) arr[i - 1][j]).value, "C")) {
                                ((Treasure) arr[i - 1][j]).caught = true;
                            }
                        } catch (ClassCastException ignored) {

                        }

                        try {
                            if (Objects.equals(((Treasure) arr[i][j - 1]).value, "C")) {
                                ((Treasure) arr[i][j - 1]).caught = true;
                            }
                        } catch (ClassCastException ignored) {

                        }

                        try {
                            if (Objects.equals(((Treasure) arr[i + 1][j + 1]).value, "C")) {
                                ((Treasure) arr[i + 1][j + 1]).caught = true;
                            }
                        } catch (ClassCastException ignored) {

                        }

                        try {
                            if (Objects.equals(((Treasure) arr[i - 1][j - 1]).value, "C")) {
                                ((Treasure) arr[i - 1][j - 1]).caught = true;
                            }
                        } catch (ClassCastException ignored) {

                        }

                        try {
                            if (Objects.equals(((Treasure) arr[i + 1][j - 1]).value, "C")) {
                                ((Treasure) arr[i + 1][j - 1]).caught = true;
                            }
                        } catch (ClassCastException ignored) {

                        }

                        try {
                            if (Objects.equals(((Treasure) arr[i - 1][j + 1]).value, "C")) {
                                ((Treasure) arr[i - 1][j + 1]).caught = true;
                            }
                        } catch (ClassCastException ignored) {

                        }


                        //--------------

                        try {
                            if (!(((Treasure) arr[i + 1][j])).isStatic) {
                                ((Treasure) arr[i + 1][j]).caught = true;
                            }
                        } catch (ClassCastException ignored) {

                        }

                        try {
                            if (!(((Treasure) arr[i][j + 1])).isStatic) {
                                ((Treasure) arr[i][j + 1]).caught = true;
                            }
                        } catch (ClassCastException ignored) {

                        }

                        try {
                            if (!(((Treasure) arr[i - 1][j])).isStatic) {
                                ((Treasure) arr[i - 1][j]).caught = true;
                            }
                        } catch (ClassCastException ignored) {

                        }

                        try {
                            if (!(((Treasure) arr[i][j - 1])).isStatic) {
                                ((Treasure) arr[i][j - 1]).caught = true;
                            }
                        } catch (ClassCastException ignored) {

                        }

                        try {
                            if (!(((Treasure) arr[i + 1][j + 1])).isStatic) {
                                ((Treasure) arr[i + 1][j + 1]).caught = true;
                            }
                        } catch (ClassCastException ignored) {

                        }

                        try {
                            if (!(((Treasure) arr[i - 1][j - 1])).isStatic) {
                                ((Treasure) arr[i - 1][j - 1]).caught = true;
                            }
                        } catch (ClassCastException ignored) {

                        }

                        try {
                            if (!(((Treasure) arr[i + 1][j - 1])).isStatic) {
                                ((Treasure) arr[i + 1][j - 1]).caught = true;
                            }
                        } catch (ClassCastException ignored) {

                        }

                        try {
                            if (!(((Treasure) arr[i - 1][j + 1])).isStatic) {
                                ((Treasure) arr[i - 1][j + 1]).caught = true;
                            }
                        } catch (ClassCastException ignored) {

                        }


                    }

                } catch (ClassCastException ignored) {

                }
            }
        }
    }


    public static void warpDevice(Object[][] arr) {

        for (int i = 0; i < 23; i++) {
            for (int j = 0; j < 55; j++) {
                try {
                    if (Objects.equals(((Treasure) arr[i][j]).value, "*")) {
                        try {
                            if (Objects.equals(((Treasure) arr[i + 1][j]).value, "C")) {
                                arr[i + 1][j] = " ";
                            }
                        } catch (ClassCastException ignored) {

                        }

                        try {
                            if (Objects.equals(((Treasure) arr[i][j + 1]).value, "C")) {
                                arr[i][j + 1] = " ";
                            }
                        } catch (ClassCastException ignored) {

                        }

                        try {
                            if (Objects.equals(((Treasure) arr[i - 1][j]).value, "C")) {
                                arr[i - 1][j] = " ";
                            }
                        } catch (ClassCastException ignored) {

                        }

                        try {
                            if (Objects.equals(((Treasure) arr[i][j - 1]).value, "C")) {
                                arr[i][j - 1] = " ";
                            }
                        } catch (ClassCastException ignored) {

                        }

                        try {
                            if (Objects.equals(((Treasure) arr[i + 1][j + 1]).value, "C")) {
                                arr[i + 1][j + 1] = " ";
                            }
                        } catch (ClassCastException ignored) {

                        }

                        try {
                            if (Objects.equals(((Treasure) arr[i - 1][j - 1]).value, "C")) {
                                arr[i - 1][j - 1] = " ";
                            }
                        } catch (ClassCastException ignored) {

                        }

                        try {
                            if (Objects.equals(((Treasure) arr[i + 1][j - 1]).value, "C")) {
                                arr[i + 1][j - 1] = " ";
                            }
                        } catch (ClassCastException ignored) {

                        }

                        try {
                            if (Objects.equals(((Treasure) arr[i - 1][j + 1]).value, "C")) {
                                arr[i - 1][j + 1] = " ";
                            }
                        } catch (ClassCastException ignored) {

                        }


                        //--------------

                        try {
                            if (isItNumber(((Treasure) arr[i + 1][j]).value)) {
                                arr[i + 1][j] = " ";
                            }
                        } catch (ClassCastException ignored) {

                        }

                        try {
                            if (isItNumber(((Treasure) arr[i][j+1]).value)) {
                                arr[i][j + 1] = " ";
                            }
                        } catch (ClassCastException ignored) {

                        }

                        try {
                            if (isItNumber(((Treasure) arr[i - 1][j]).value)) {
                                arr[i - 1][j] = " ";
                            }
                        } catch (ClassCastException ignored) {

                        }

                        try {
                            if (isItNumber(((Treasure) arr[i][j-1]).value)) {
                                arr[i][j - 1] = " ";
                            }
                        } catch (ClassCastException ignored) {

                        }

                        try {
                            if (isItNumber(((Treasure) arr[i + 1][j+1]).value)) {
                                arr[i + 1][j + 1] = " ";
                            }
                        } catch (ClassCastException ignored) {

                        }

                        try {
                            if (isItNumber(((Treasure) arr[i - 1][j-1]).value)) {
                                arr[i - 1][j - 1] = " ";
                            }
                        } catch (ClassCastException ignored) {

                        }

                        try {
                            if (isItNumber(((Treasure) arr[i + 1][j-1]).value)) {
                                arr[i + 1][j - 1] = " ";
                            }
                        } catch (ClassCastException ignored) {

                        }

                        try {
                            if (isItNumber(((Treasure) arr[i - 1][j+1]).value)) {
                                arr[i - 1][j + 1] = " ";
                            }
                        } catch (ClassCastException ignored) {

                        }


                    }

                } catch (ClassCastException ignored) {

                }
            }
        }
    }


    public static void deActiveWarp(Object[][] arr) {

        for (int i = 0; i < 23; i++) {
            for (int j = 0; j < 55; j++) {
                try {
                    if (Objects.equals(((Treasure) arr[i][j]).value, "*")) {

                        if (((Treasure) arr[i][j]).trapTime >= 25000) {
                            arr[i][j] = " ";

                        }
                    }
                }catch(ClassCastException ignored){

                }
            }
        }
    }



    public static void deActiveTrap(Object[][] arr) {

        for (int i = 0; i < 23; i++) {
            for (int j = 0; j < 55; j++) {
                try {
                    if (Objects.equals(((Treasure) arr[i][j]).value, "=")) {

                        if(((Treasure) arr[i][j]).trapTime>=25000) {
                            arr[i][j] = " ";
                            makeCaughtFalse(i,j,arr);
                        }

                    }

                } catch (ClassCastException ignored) {

                }
            }
        }
    }

    public static void makeCaughtFalse(int i, int j, Object[][] arr){
        try {
            if (Objects.equals(((Treasure) arr[i + 1][j]).value, "C")) {
                ((Treasure) arr[i + 1][j]).caught = false;
            }
        } catch (ClassCastException ignored) {

        }

        try {
            if (Objects.equals(((Treasure) arr[i][j + 1]).value, "C")) {
                ((Treasure) arr[i][j + 1]).caught = false;
            }
        } catch (ClassCastException ignored) {

        }

        try {
            if (Objects.equals(((Treasure) arr[i - 1][j]).value, "C")) {
                ((Treasure) arr[i - 1][j]).caught = false;
            }
        } catch (ClassCastException ignored) {

        }

        try {
            if (Objects.equals(((Treasure) arr[i][j - 1]).value, "C")) {
                ((Treasure) arr[i][j - 1]).caught = false;
            }
        } catch (ClassCastException ignored) {

        }

        try {
            if (Objects.equals(((Treasure) arr[i + 1][j + 1]).value, "C")) {
                ((Treasure) arr[i + 1][j + 1]).caught = false;
            }
        } catch (ClassCastException ignored) {

        }

        try {
            if (Objects.equals(((Treasure) arr[i - 1][j - 1]).value, "C")) {
                ((Treasure) arr[i - 1][j - 1]).caught = false;
            }
        } catch (ClassCastException ignored) {

        }

        try {
            if (Objects.equals(((Treasure) arr[i + 1][j - 1]).value, "C")) {
                ((Treasure) arr[i + 1][j - 1]).caught = false;
            }
        } catch (ClassCastException ignored) {

        }

        try {
            if (Objects.equals(((Treasure) arr[i - 1][j + 1]).value, "C")) {
                ((Treasure) arr[i - 1][j + 1]).caught = false;
            }
        } catch (ClassCastException ignored) {

        }


        //--------------

        try {
            if (!(((Treasure) arr[i + 1][j])).isStatic) {
                ((Treasure) arr[i + 1][j]).caught = false;
            }
        } catch (ClassCastException ignored) {

        }

        try {
            if (!(((Treasure) arr[i][j + 1])).isStatic) {
                ((Treasure) arr[i][j + 1]).caught = false;
            }
        } catch (ClassCastException ignored) {

        }

        try {
            if (!(((Treasure) arr[i - 1][j])).isStatic) {
                ((Treasure) arr[i - 1][j]).caught = false;
            }
        } catch (ClassCastException ignored) {

        }

        try {
            if (!(((Treasure) arr[i][j - 1])).isStatic) {
                ((Treasure) arr[i][j - 1]).caught = false;
            }
        } catch (ClassCastException ignored) {

        }

        try {
            if (!(((Treasure) arr[i + 1][j + 1])).isStatic) {
                ((Treasure) arr[i + 1][j + 1]).caught = false;
            }
        } catch (ClassCastException ignored) {

        }

        try {
            if (!(((Treasure) arr[i - 1][j - 1])).isStatic) {
                ((Treasure) arr[i - 1][j - 1]).caught = false;
            }
        } catch (ClassCastException ignored) {

        }

        try {
            if (!(((Treasure) arr[i + 1][j - 1])).isStatic) {
                ((Treasure) arr[i + 1][j - 1]).caught = false;
            }
        } catch (ClassCastException ignored) {

        }

        try {
            if (!(((Treasure) arr[i - 1][j + 1])).isStatic) {
                ((Treasure) arr[i - 1][j + 1]).caught = false;
            }
        } catch (ClassCastException ignored) {

        }
    }


    public static boolean isItNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }




}