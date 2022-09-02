import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Question {

    EnigmaConsoleCreator enigmaCC = new EnigmaConsoleCreator();

    private static int idNumber = 0;
    private int id = 0;
    private String category, text, ch1,ch2,ch3,ch4,answer;
    private int diff;
    private String[] questionWords;
    private String[] choices;
    private String[] wrongChoices;
    private String answerString;
    public int valueOfQuestion;
    private int numberOfRightAnswers;
    private int numberOfWrongAnswers;



    public Question(String category, String text, String ch1, String ch2, String ch3, String ch4, String answer, int diff) {
        this.category = category;
        this.text = text;
        this.ch1 = "A) " + ch1;
        this.ch2 = "B) " + ch2;
        this.ch3 = "C) " + ch3;
        this.ch4 = "D) " + ch4;
        this.answer = answer;
        this.diff = diff;

        this.id = idNumber;
        idNumber++;

        choices = new String[]{this.ch1, this.ch2, this.ch3, this.ch4};

        for(int i = 0; i<4; i++){
            if(String.valueOf(choices[i].charAt(0)).equals(answer)){
                this.answerString = choices[i];
                break;
            }
        }


        if(diff == 1){

            valueOfQuestion = 20000;
        }

        else if(diff == 2){

            valueOfQuestion = 100000;
        }

        else if(diff == 3){

            valueOfQuestion = 250000;
        }

        else if(diff == 4){

            valueOfQuestion = 500000;
        }

        else if(diff == 5){

            valueOfQuestion = 1000000;
        }
    }



    public String[] getChoices() {
        return choices;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    public int getNumberOfRightAnswers() {
        return numberOfRightAnswers;
    }

    public void setNumberOfRightAnswers(int numberOfRightAnswers) {
        this.numberOfRightAnswers = numberOfRightAnswers;
    }

    public int getNumberOfWrongAnswers() {
        return numberOfWrongAnswers;
    }

    public void setNumberOfWrongAnswers(int numberOfWrongAnswers) {
        this.numberOfWrongAnswers = numberOfWrongAnswers;
    }


    public String getAnswerString() {
        return answerString;
    }

    public void setAnswerString(String answerString) {
        this.answerString = answerString;
    }

    public int getValueOfQuestion() {
        return valueOfQuestion;
    }

    public void setValueOfQuestion(int valueOfQuestion) {
        this.valueOfQuestion = valueOfQuestion;
    }


    public String doubleDipLifeLine(String answerInput, String eliminatedBefore, Participant currentContestant){

        System.out.println();

        Scanner scanner = new Scanner(System.in);

        for(int i = 0; i<2; i++){

            if(Objects.equals(answerInput, getAnswer())){
                break;
            }else{
                if(i == 0){

                    System.out.println("Wrong answer!");

                    System.out.println("------------------------------------------------");
                    System.out.println("Money: $" + currentContestant.getPrize());

                    if(!currentContestant.isFiftyPercentageUsed()){
                        System.out.println("50%");
                    }else{
                        System.out.println("-");
                    }

                    if(!currentContestant.isDoubleDipUsed()){
                        System.out.println("Double Dip");
                    }else{
                        System.out.println("-");
                    }

                    System.out.println(getText());

                    for(int j = 0; j<4;j++){
                        if((answerInput.charAt(0) == choices[j].charAt(0)) || choices[j].charAt(0) == eliminatedBefore.charAt(0) || choices[j].charAt(0) == eliminatedBefore.charAt(1) ){
                            System.out.print(choices[j].charAt(0));
                            System.out.print(choices[j].charAt(1));
                            System.out.println(choices[j].charAt(2));
                        }else{
                            System.out.println(choices[j]);
                        }
                    }

                    System.out.print("Please make your second choice..:");
                    answerInput = scanner.nextLine();
                    answerInput = answerInput.toUpperCase(Locale.ENGLISH);
                }
            }
        }

        return answerInput;
    }


    public String fiftyPercentageLifeLine(Participant currentContestant){

        String eliminatedBefore = "";
        int randomIndex1 = 0;
        int randomIndex2 = 0;

        boolean same = true;

        Random random = new Random();

        while(same){

            randomIndex1 = random.nextInt(4);
            randomIndex2 = random.nextInt(4);

            same = (randomIndex1 == randomIndex2) || (Objects.equals(choices[randomIndex1],getAnswerString() )) || (Objects.equals(choices[randomIndex2], getAnswerString())) ;
        }

        eliminatedBefore += choices[randomIndex1].charAt(0);
        eliminatedBefore += choices[randomIndex2].charAt(0);

        System.out.println("Money: $" + currentContestant.getPrize());

        if(!currentContestant.isFiftyPercentageUsed()){
            System.out.println("50%");
        }else{
            System.out.println("-");
        }

        if(!currentContestant.isDoubleDipUsed()){
            System.out.println("Double Dip");
        }else{
            System.out.println("-");
        }

        System.out.println(getText());

        for(int i = 0; i<4;i++){
            if((i == randomIndex1) || (i == randomIndex2)){
                System.out.print(choices[i].charAt(0));
                System.out.print(choices[i].charAt(1));
                System.out.println(choices[i].charAt(2));
            }else{
                System.out.println(choices[i]);
            }
        }


        return eliminatedBefore;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCh1() {
        return ch1;
    }

    public void setCh1(String ch1) {
        this.ch1 = ch1;
    }

    public String getCh2() {
        return ch2;
    }

    public void setCh2(String ch2) {
        this.ch2 = ch2;
    }

    public String getCh3() {
        return ch3;
    }

    public void setCh3(String ch3) {
        this.ch3 = ch3;
    }

    public String getCh4() {
        return ch4;
    }

    public void setCh4(String ch4) {
        this.ch4 = ch4;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getDiff() {
        return diff;
    }

    public void setDiff(int diff) {
        this.diff = diff;
    }

    public String[] getQuestionWords() {
        return questionWords;
    }

    public void setQuestionWords(String[] questionWords) {
        this.questionWords = questionWords;
    }


}
