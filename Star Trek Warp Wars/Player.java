import java.util.Objects;

public class Player {

    Stack backpack = new Stack(8);
    int energy = 50;
    int score = 0;
    int lives = 5;


    public void addElementToBackpack(Treasure element) {
        score = score + element.treasurePointForPlayer;
        Treasure lastElementBefore;
        if(backpack.size() <=8) {

            if (!backpack.isEmpty()) {
                lastElementBefore = (Treasure) backpack.peek();
                backpack.push(element);
                checkBackpack(lastElementBefore);
            } else {
                backpack.push(element);
            }

        }
    }

    //
    public void checkBackpack(Treasure lastElementBefore) {
        if (Objects.equals(lastElementBefore.value, ((Treasure)backpack.peek()).value) && isItNumber(lastElementBefore.value)) {
            backpack.pop();
            backpack.pop();
            evaluate(lastElementBefore);
        } else if (isItNumber(lastElementBefore.value) && isItNumber(((Treasure) backpack.peek()).value)) {
            backpack.pop();
            backpack.pop();
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

    public void evaluate(Treasure number) {

        if(Objects.equals(number.value, "1")){

        }else if(Objects.equals(number.value, "2")){
            this.energy +=30;
        }
        else if(Objects.equals(number.value, "3")){
            addElementToBackpack(new Treasure("="));
        }

        else if(Objects.equals(number.value, "4")){
            this.energy +=240;
        }
        else if(Objects.equals(number.value, "5")){
            addElementToBackpack(new Treasure("*"));
        }
    }


}
