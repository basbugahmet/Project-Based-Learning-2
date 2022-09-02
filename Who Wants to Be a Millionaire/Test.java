import enigma.core.Enigma;

import java.io.*;
import java.util.*;


public class Test {


    public static void main(String[] args) throws InterruptedException, IOException {


        EnigmaConsoleCreator enigmaCC = new EnigmaConsoleCreator();




        Question[] allQuestions = new Question[0];
        String[][] categories = new String[0][];
        String[][] categoriesMostRight = new String[0][];
        String[][] categoriesMostWrong = new String[0][];
        String[][] cityStats = new String[0][];
        String[] dict = new String[count("dictionary.txt")];
        int[] difficulties = new int[6];
        String[] allWords = new String[1000];
        String[] allStopWords = new String[count("stop_words.txt")];
        Participant[] allParticipants = new Participant[0];
        Participant currentContestant = null;


        File file = new File("output.txt");

        readStopWords(allStopWords);
        readDictionary(dict);


        System.out.println("*****Menu*****");
        System.out.println("1)Load questions");
        System.out.println("2)Load participants");
        System.out.println("3)Start competition");
        System.out.println("4)Show statistics");
        System.out.println("5)Exit");

        Scanner scanner = new Scanner(System.in);

        boolean participantsLoaded = false;
        boolean questionsLoaded = false;

        while (true) {

            System.out.println();
            System.out.print("Enter your choice: ");
            int menuInput = scanner.nextInt();

            scanner.nextLine();

            System.out.println();


            if (menuInput == 1) {
                System.out.print("Please enter the txt name: ");
                String txtInput = scanner.nextLine();
                allQuestions = new Question[countForQuestions(txtInput)];
                categories = new String[count(txtInput)][2];
                categoriesMostRight = new String[count(txtInput)][2];
                categoriesMostWrong = new String[count(txtInput)][2];
                readLineForQuestions(txtInput, allQuestions, allWords, allStopWords, dict);

                System.out.println();

                for (int i = 0; i < allQuestions.length; i++) {
                    boolean found = false;
                    for (int j = 0; j < allQuestions.length; j++) {
                        if (Objects.equals(categories[j][0], allQuestions[i].getCategory())) {
                            categories[j][1] = String.valueOf(Integer.parseInt(categories[j][1]) + 1);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        for (int k = 0; k < allQuestions.length; k++) {
                            if (categories[k][0] == null) {
                                categories[k][0] = allQuestions[i].getCategory();
                                categories[k][1] = String.valueOf(1);
                                questionsLoaded = true;
                                break;
                            }
                        }
                    }
                }


                System.out.println("Category        The number of questions");
                System.out.println("--------        -----------------------");

                for (int i = 0; i < categories.length; i++) {
                    String[] category = categories[i];
                    if (category[0] != null) {
                        System.out.println(category[0] + " ".repeat(27 - category[0].length()) + category[1]);
                    }
                }

                System.out.println();


                for (int i = 0; i < allQuestions.length; i++) {
                    difficulties[allQuestions[i].getDiff()] += 1;
                }

                System.out.println("Difficulty       The number of questions");
                System.out.println("--------        ------------------------");

                for (int i = 1; i < difficulties.length; i++) {
                    System.out.println(i + " ".repeat(27) + difficulties[i]);
                }

                System.out.println();
            }

            if (menuInput == 2) {
                System.out.print("Please enter the txt name: ");
                String txtInput = scanner.nextLine();
                allParticipants = new Participant[count(txtInput)];
                cityStats = new String[count(txtInput)][2];
                readLineForParticipants(txtInput, allParticipants);
                System.out.println("The file is loaded.");
                participantsLoaded = true;

            }


            boolean isEliminated = false;
            boolean withdrawed = false;
            int tour = 1;
            if (menuInput == 3) {

                if (participantsLoaded) {
                    boolean contestantSelected = false;
                    while(!contestantSelected){
                        Random random = new Random();
                        int randomContestant = random.nextInt(allParticipants.length);
                        currentContestant = allParticipants[randomContestant];
                        if(!currentContestant.getDidCompete()){
                            currentContestant.setDidCompete(true);
                            System.out.println("Contestant: " + currentContestant.name);
                            contestantSelected = true;
                        }

                    }

                } else {
                    System.out.println("Firstly, you need to import participants!");
                    Thread.sleep(2000);
                    System.exit(0);

                }


                while (!isEliminated && tour < 6) {



                    boolean isAnsweredCorrectly = false;
                    Question currentQuestion = null;

                    Random random = new Random();
                    String[] selectedWords = new String[allWords.length];

                    for (int i = 0; i < 1000; i++) {
                        int r = random.nextInt(allWords.length);
                        boolean found = false;
                        for (int j = 0; j < selectedWords.length; j++) {
                            if (allWords[r] != null) {

                                if (allWords[r].equals(selectedWords[j])) {
                                    found = true;
                                    break;
                                }
                            }
                        }
                        if (!found) {


                            boolean loop1Ended = false;
                            boolean loop2Ended = false;


                            for (int k = 0; k < allQuestions.length; k++) {
                                for (int l = 0; l < allQuestions[k].getQuestionWords().length; l++) {
                                    if (Objects.equals(allQuestions[k].getQuestionWords()[l], allWords[r]) && allQuestions[k].getDiff() == tour) {
                                        for (int o = 0; o < selectedWords.length; o++) {
                                            if (selectedWords[o] == null) {
                                                selectedWords[o] = allWords[r];
                                                loop1Ended = true;
                                                break;
                                            }
                                        }
                                    }
                                    if (loop1Ended) {
                                        loop2Ended = true;
                                        break;
                                    }

                                }

                                if (loop2Ended) {
                                    break;
                                }
                            }

                        }

                    }

                    System.out.println("-------------------------------------------------------------");
                    System.out.println();

                    System.out.println("---------------------------------------");
                    System.out.println("WORD CLOUD");
                    System.out.println("----------");
                    for (int u = 0; u < selectedWords.length; u++) {
                        if (selectedWords[u] != null) {
                            System.out.print(selectedWords[u] + "\t");
                            if ((u + 1) % 4 == 0)
                                System.out.println();
                        }
                    }

                    System.out.println();

                    System.out.println("---------------------------------------");

                    System.out.print("Please enter a word: ");
                    String input = scanner.nextLine();

                    boolean cont = false;
                    boolean found = false;

                    for (int a = 0; a < selectedWords.length; a++) {

                        if (Objects.equals(selectedWords[a], input)) {

                            found = true;
                            for (int d = 0; d < allQuestions.length; d++) {
                                currentQuestion = allQuestions[d];

                                for (int e = 0; e < currentQuestion.getQuestionWords().length; e++) {

                                    if (Objects.equals(currentQuestion.getQuestionWords()[e], input) && currentQuestion.getDiff() == tour) { //kelime yanlis soru secme


                                        System.out.println();

                                        System.out.println("---------------------");
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

                                        System.out.println("---------------------");


                                        System.out.println(currentQuestion.getText());
                                        System.out.println(currentQuestion.getCh1());
                                        System.out.println(currentQuestion.getCh2());
                                        System.out.println(currentQuestion.getCh3());
                                        System.out.println(currentQuestion.getCh4());

                                        Time time = new Time();
                                        time.start();

                                        int y = enigmaCC.cn.getTextWindow().getCursorY();


                                        System.out.print("Enter your choice (E:Exit): ");



                                        String answerInput = scanner.nextLine();
                                        answerInput = answerInput.toUpperCase(Locale.ENGLISH);

                                        if(time.isTimeFinished)
                                        {
                                            cont=false;
                                            isEliminated=true;

                                            if(tour == 1 || tour == 2){
                                                currentContestant.setPrize(0);
                                            }

                                            else if(tour == 3  || tour == 4){
                                                currentContestant.setPrize(100000);
                                            }

                                            else if(tour == 5){
                                                currentContestant.setPrize(500000);
                                            }


                                            System.out.println("You won $" + currentContestant.getPrize());

                                            break;
                                        }

                                        else if(answerInput.equals("50%") ) {

                                            if(!currentContestant.isFiftyPercentageUsed()) {

                                                currentContestant.setFiftyPercentageUsed(true);
                                                String eliminatedBefore = currentQuestion.fiftyPercentageLifeLine(currentContestant);


                                                System.out.print("Enter your choice (E:Exit): ");
                                                answerInput = scanner.nextLine();
                                                answerInput = answerInput.toUpperCase(Locale.ENGLISH);



                                                if(time.isTimeFinished)
                                                {
                                                    cont=false;
                                                    isEliminated=true;

                                                    if(tour == 1 || tour == 2){
                                                        currentContestant.setPrize(0);
                                                    }

                                                    else if(tour == 3  || tour == 4){
                                                        currentContestant.setPrize(100000);
                                                    }

                                                    else if(tour == 5){
                                                        currentContestant.setPrize(500000);
                                                    }


                                                    System.out.println("You won $" + currentContestant.getPrize());

                                                    break;
                                                }


                                                else if(answerInput.equals("DOUBLE DIP")){

                                                    if(!currentContestant.isDoubleDipUsed()) {


                                                        System.out.println("Double Dip lifeline is being used ...");
                                                        currentContestant.setDoubleDipUsed(true);
                                                        System.out.println();

                                                        System.out.println("-------------------------------------------------------------");

                                                        System.out.println("---------------------");
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

                                                        System.out.println("---------------------");

                                                        System.out.println(currentQuestion.getText());


                                                        if(Objects.equals((eliminatedBefore.charAt(0)), currentQuestion.getCh1().charAt(0))  || Objects.equals((eliminatedBefore.charAt(1)), currentQuestion.getCh1().charAt(0)) ){
                                                            System.out.println("A) ");
                                                        }else{
                                                            System.out.println(currentQuestion.getCh1());
                                                        }

                                                        if(Objects.equals((eliminatedBefore.charAt(0)), currentQuestion.getCh2().charAt(0))  || Objects.equals((eliminatedBefore.charAt(1)), currentQuestion.getCh2().charAt(0)) ){
                                                            System.out.println("B) ");
                                                        }else{
                                                            System.out.println(currentQuestion.getCh2());
                                                        }


                                                        if(Objects.equals((eliminatedBefore.charAt(0)), currentQuestion.getCh3().charAt(0))  || Objects.equals((eliminatedBefore.charAt(1)), currentQuestion.getCh3().charAt(0)) ){
                                                            System.out.println("C) ");
                                                        }else{
                                                            System.out.println(currentQuestion.getCh3());
                                                        }


                                                        if(Objects.equals((eliminatedBefore.charAt(0)), currentQuestion.getCh4().charAt(0))  || Objects.equals((eliminatedBefore.charAt(1)), currentQuestion.getCh4().charAt(0)) ){
                                                            System.out.println("D) ");
                                                        }else{
                                                            System.out.println(currentQuestion.getCh4());
                                                        }






                                                        System.out.print("Make your first choice (E:Exit): ");
                                                        answerInput = scanner.nextLine();
                                                        answerInput = answerInput.toUpperCase(Locale.ENGLISH);

                                                        answerInput = currentQuestion.doubleDipLifeLine(answerInput,eliminatedBefore,currentContestant);


                                                        if(time.isTimeFinished)
                                                        {

                                                            cont=false;
                                                            isEliminated=true;

                                                            if(tour == 1 || tour == 2){
                                                                currentContestant.setPrize(0);
                                                            }

                                                            else if(tour == 3  || tour == 4){
                                                                currentContestant.setPrize(100000);
                                                            }

                                                            else if(tour == 5){
                                                                currentContestant.setPrize(500000);
                                                            }


                                                            System.out.println("You won $" + currentContestant.getPrize());

                                                            break;
                                                        }



                                                        else if (Objects.equals(answerInput, currentQuestion.getAnswer())) {
                                                            time.pause=true;
                                                            isAnsweredCorrectly = true;
                                                            currentContestant.getAgeStats();
                                                            currentQuestion.setNumberOfRightAnswers(currentQuestion.getNumberOfRightAnswers()+1);
                                                            System.out.println("Correct Answer!");
                                                            currentContestant.setPrize(currentQuestion.getValueOfQuestion());
                                                            System.out.println();
                                                            System.out.println("New question is coming...");
                                                            System.out.println();
                                                            tour++;
                                                            cont = true;
                                                            break;
                                                        } else if (Objects.equals(answerInput, "E")) {
                                                            time.pause=true;
                                                            withdrawed = true;
                                                            System.out.println("Exiting...");
                                                            System.out.println("You won $" + currentContestant.getPrize());
                                                        } else {
                                                            time.pause=true;
                                                            currentQuestion.setNumberOfWrongAnswers(currentQuestion.getNumberOfWrongAnswers()+1);

                                                            if(tour == 1 || tour == 2){
                                                                currentContestant.setPrize(0);
                                                            }

                                                            else if(tour == 3  || tour == 4){
                                                                currentContestant.setPrize(100000);
                                                            }

                                                            else if(tour == 5){
                                                                currentContestant.setPrize(500000);
                                                            }


                                                            System.out.println("You eliminated! Answer was " + currentQuestion.getAnswer());
                                                            System.out.println("You won $" + currentContestant.getPrize());
                                                            isEliminated = true;
                                                            cont = true;
                                                            break;

                                                        }

                                                    }else{

                                                        System.out.println("You have already used your double dip lifeline!");
                                                        System.out.print("Enter your choice (E:Exit): ");
                                                        answerInput = scanner.nextLine();
                                                        answerInput = answerInput.toUpperCase(Locale.ENGLISH);

                                                    }

                                                }


                                                else if (Objects.equals(answerInput, currentQuestion.getAnswer())) {
                                                    time.pause=true;
                                                    isAnsweredCorrectly = true;
                                                    currentContestant.getAgeStats();
                                                    currentQuestion.setNumberOfRightAnswers(currentQuestion.getNumberOfRightAnswers()+1);
                                                    System.out.println("Correct Answer!");
                                                    currentContestant.setPrize(currentQuestion.getValueOfQuestion());
                                                    System.out.println();
                                                    System.out.println("New question is coming...");
                                                    System.out.println();
                                                    tour++;
                                                    cont = true;
                                                    break;
                                                } else if (Objects.equals(answerInput, "E")) {
                                                    time.pause=true;
                                                    withdrawed = true;
                                                    System.out.println("Exiting...");
                                                    System.out.println("You won $" + currentContestant.getPrize());
                                                } else {
                                                    time.pause=true;
                                                    currentQuestion.setNumberOfWrongAnswers(currentQuestion.getNumberOfWrongAnswers()+1);

                                                    if(tour == 1 || tour == 2){
                                                        currentContestant.setPrize(0);
                                                    }

                                                    else if(tour == 3  || tour == 4){
                                                        currentContestant.setPrize(100000);
                                                    }

                                                    else if(tour == 5){
                                                        currentContestant.setPrize(500000);
                                                    }

                                                    System.out.println("You eliminated! Answer was " + currentQuestion.getAnswer());
                                                    System.out.println("You won $" + currentContestant.getPrize());
                                                    isEliminated = true;
                                                    cont = true;
                                                    break;

                                                }

                                            }else{
                                                System.out.println("You have already used your 50% lifeline!");
                                                System.out.print("Enter your choice (E:Exit): ");
                                                answerInput = scanner.nextLine();
                                                answerInput = answerInput.toUpperCase(Locale.ENGLISH);

                                            }

                                        }


                                        else if(answerInput.equals("DOUBLE DIP")){

                                            String eliminatedBefore = "  ";
                                            if(!currentContestant.isDoubleDipUsed()) {


                                                System.out.println("Double Dip lifeline is being used ...");
                                                currentContestant.setDoubleDipUsed(true);
                                                System.out.println();

                                                System.out.println("-------------------------------------------------------------");

                                                System.out.println("---------------------");
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
                                                System.out.println("---------------------");

                                                System.out.println(currentQuestion.getText());
                                                System.out.println(currentQuestion.getCh1());
                                                System.out.println(currentQuestion.getCh2());
                                                System.out.println(currentQuestion.getCh3());
                                                System.out.println(currentQuestion.getCh4());

                                                System.out.print("Make your first choice (E:Exit): ");
                                                answerInput = scanner.nextLine();
                                                answerInput = answerInput.toUpperCase(Locale.ENGLISH);

                                                answerInput = currentQuestion.doubleDipLifeLine(answerInput, eliminatedBefore,currentContestant);

                                                if(time.isTimeFinished)
                                                {
                                                    cont=false;
                                                    isEliminated=true;

                                                    if(tour == 1 || tour == 2){
                                                        currentContestant.setPrize(0);
                                                    }

                                                    else if(tour == 3  || tour == 4){
                                                        currentContestant.setPrize(100000);
                                                    }

                                                    else if(tour == 5){
                                                        currentContestant.setPrize(500000);
                                                    }


                                                    System.out.println("You won $" + currentContestant.getPrize());

                                                    break;
                                                }


                                                else if (Objects.equals(answerInput, currentQuestion.getAnswer())) {
                                                    time.pause=true;
                                                    isAnsweredCorrectly = true;
                                                    currentContestant.getAgeStats();
                                                    currentQuestion.setNumberOfRightAnswers(currentQuestion.getNumberOfRightAnswers()+1);
                                                    System.out.println("Correct Answer!");
                                                    currentContestant.setPrize(currentQuestion.getValueOfQuestion());
                                                    System.out.println();
                                                    System.out.println("New question is coming...");
                                                    System.out.println();
                                                    tour++;
                                                    cont = true;
                                                    break;
                                                } else if (Objects.equals(answerInput, "E")) {
                                                    time.pause=true;
                                                    withdrawed = true;
                                                    System.out.println("Exiting...");
                                                    System.out.println("You won $" + currentContestant.getPrize());
                                                } else {
                                                    currentQuestion.setNumberOfWrongAnswers(currentQuestion.getNumberOfWrongAnswers()+1);
                                                    time.pause=true;

                                                    if(tour == 1 || tour == 2){
                                                        currentContestant.setPrize(0);
                                                    }

                                                    else if(tour == 3  || tour == 4){
                                                        currentContestant.setPrize(100000);
                                                    }

                                                    else if(tour == 5){
                                                        currentContestant.setPrize(500000);
                                                    }

                                                    System.out.println("You eliminated! Answer was " + currentQuestion.getAnswer());
                                                    System.out.println("You won $" + currentContestant.getPrize());

                                                    isEliminated = true;
                                                    cont = true;
                                                    break;

                                                }

                                            }else{

                                                System.out.println("You have already used your double dip lifeline!");
                                                System.out.print("Enter your choice (E:Exit): ");
                                                answerInput = scanner.nextLine();
                                                answerInput = answerInput.toUpperCase(Locale.ENGLISH);

                                            }

                                        }


                                        else if (Objects.equals(answerInput, currentQuestion.getAnswer())) {
                                            time.pause=true;
                                            isAnsweredCorrectly = true;
                                            currentContestant.getAgeStats();
                                            currentQuestion.setNumberOfRightAnswers(currentQuestion.getNumberOfRightAnswers()+1);
                                            System.out.println("Correct Answer!");
                                            currentContestant.setPrize(currentQuestion.getValueOfQuestion());
                                            System.out.println();
                                            System.out.println("New question is coming...");
                                            System.out.println();
                                            tour++;
                                            cont = true;
                                            break;
                                        }

                                        else if(Objects.equals(answerInput, "E")){
                                            time.pause=true;
                                            withdrawed = true;
                                            System.out.println("Exiting...");
                                            System.out.println("You won $" + currentContestant.getPrize());
                                        }
                                        else {
                                            time.pause=true;
                                            currentQuestion.setNumberOfWrongAnswers(currentQuestion.getNumberOfWrongAnswers()+1);

                                            if(tour == 1 || tour == 2){
                                                currentContestant.setPrize(0);
                                            }

                                            else if(tour == 3  || tour == 4){
                                                currentContestant.setPrize(100000);
                                            }

                                            else if(tour == 5){
                                                currentContestant.setPrize(500000);
                                            }

                                            System.out.println("You eliminated! Answer was " + currentQuestion.getAnswer());
                                            System.out.println("You won $" + currentContestant.getPrize());

                                            isEliminated = true;
                                            cont = true;
                                            break;

                                        }


                                    }

                                }

                                if (cont) {
                                    break;
                                }

                            }
                        }

                    }
                    if (!found) {
                        System.out.println("This word does not exist!");
                    }

                    if(tour == 6) {
                        System.out.println("Congratulations!, you are now a millionaire.");

                    }


                    try{
                        if(!file.exists()){
                            file.createNewFile();
                        }
                        PrintWriter out = new PrintWriter(new FileWriter(file, true));
                        if(currentQuestion != null){
                            out.append(currentQuestion.getId() + "," + currentContestant.getId() + "," +isAnsweredCorrectly + "\n");
                            out.close();
                        }
                    }catch(IOException e){
                        System.out.println("error");
                    }




                    if(isEliminated || withdrawed || tour == 6){

                        System.out.println("Would you like to play again? (Y/N)");
                        String playAgain = scanner.nextLine();
                        playAgain = playAgain.toUpperCase(Locale.ENGLISH);

                        if(playAgain.equals("Y")){
                            tour = 1;
                            isEliminated = false;
                            withdrawed = false;
                            currentContestant.setPrize(0);
                            currentContestant.setDoubleDipUsed(false);
                            currentContestant.setFiftyPercentageUsed(false);
                        }

                        if(playAgain.equals("N")){
                            System.out.println();
                        }

                    }


                }

            }
            if (menuInput == 4) {

                Participant mostSuccessful = null;
                int mostPrize = 0;
                for(int i = 0; i< allParticipants.length; i++){
                    if(allParticipants[i].getPrize() > mostPrize){
                        mostPrize = allParticipants[i].getPrize();
                        mostSuccessful = allParticipants[i];
                    }
                }

                System.out.println();
                if(mostSuccessful != null){
                    System.out.println("The most successful participant is " + mostSuccessful.getName());
                }



                for (int i = 0; i < allQuestions.length; i++) {
                    boolean found = false;
                    for (int j = 0; j < allQuestions.length; j++) {
                        if (Objects.equals(categoriesMostRight[j][0], allQuestions[i].getCategory())) {
                            categoriesMostRight[j][1] = String.valueOf(Integer.parseInt(categoriesMostRight[j][1]) + allQuestions[i].getNumberOfRightAnswers());
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        for (int k = 0; k < allQuestions.length; k++) {
                            if (categoriesMostRight[k][0] == null) {
                                categoriesMostRight[k][0] = allQuestions[i].getCategory();
                                categoriesMostRight[k][1] = String.valueOf(allQuestions[i].getNumberOfRightAnswers());
                                questionsLoaded = true;
                                break;
                            }
                        }
                    }
                }


                int numOfRight = 0;
                String mostRightCategory = null;

                for(int i = 0; i < categoriesMostRight.length; i++){
                    if(categoriesMostRight[i][1] != null){
                        if(Integer.parseInt(categoriesMostRight[i][1]) > numOfRight){
                            numOfRight = Integer.parseInt(categoriesMostRight[i][1]);
                            mostRightCategory = categoriesMostRight[i][0];
                        }
                    }
                }

                System.out.println();
                System.out.println("The category most correctly answered is " + mostRightCategory);



                for (int i = 0; i < allQuestions.length; i++) {
                    boolean found = false;
                    for (int j = 0; j < allQuestions.length; j++) {
                        if (Objects.equals(categoriesMostWrong[j][0], allQuestions[i].getCategory())) {
                            categoriesMostWrong[j][1] = String.valueOf(Integer.parseInt(categoriesMostWrong[j][1]) + allQuestions[i].getNumberOfWrongAnswers());
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        for (int k = 0; k < allQuestions.length; k++) {
                            if (categoriesMostWrong[k][0] == null) {
                                categoriesMostWrong[k][0] = allQuestions[i].getCategory();
                                categoriesMostWrong[k][1] = String.valueOf(allQuestions[i].getNumberOfWrongAnswers());
                                questionsLoaded = true;
                                break;
                            }
                        }
                    }
                }


                int numOfWrong = 0;
                String mostWrongCategory = null;

                for(int i = 0; i < categoriesMostWrong.length; i++){
                    if(categoriesMostWrong[i][1] != null){
                        if(Integer.parseInt(categoriesMostWrong[i][1]) > numOfWrong){
                            numOfWrong = Integer.parseInt(categoriesMostWrong[i][1]);
                            mostWrongCategory = categoriesMostWrong[i][0];
                        }
                    }
                }

                System.out.println();
                System.out.println("The category most badly answered is " + mostWrongCategory);

                System.out.println();
                System.out.println("How many questions did contestants in each age group answer correctly ?");
                System.out.println("Participants who are under 30 years old ..:" + Participant.getStatsOfLessThan30());
                System.out.println("Participants who are greater than 30 and under 50 years old ..:" + Participant.getStatsOfGreaterThan30AndLessThan50());
                System.out.println("Participants who are greater than 50 years old ..:" + Participant.getStatsOfGreaterThan50());



                for (int i = 0; i < allParticipants.length; i++) {
                    boolean found = false;
                    for (int j = 0; j < allParticipants.length; j++) {
                        if (Objects.equals(cityStats[j][0], allParticipants[i].getCity())) {
                            cityStats[j][1] = String.valueOf(Integer.parseInt(cityStats[j][1]) + 1);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        for (int k = 0; k < allParticipants.length; k++) {
                            if (cityStats[k][0] == null) {
                                cityStats[k][0] = allParticipants[i].getCity();
                                cityStats[k][1] = String.valueOf(1);
                                questionsLoaded = true;
                                break;
                            }
                        }
                    }
                }

                int numOfPart = 0;
                String mostPartCity = null;

                for(int i = 0; i < cityStats.length; i++){
                    if(cityStats[i][1] != null){
                        if(Integer.parseInt(cityStats[i][1]) > numOfPart){
                            numOfPart = Integer.parseInt(cityStats[i][1]);
                            mostPartCity= cityStats[i][0];
                        }
                    }
                }

                System.out.println();
                System.out.println("The city with the highest number of participants is " + mostPartCity);


            }
            if (menuInput == 5) {
                System.out.println("Exiting...");
                System.exit(0);
            }

            if(menuInput != 1 && menuInput != 2 && menuInput != 3 && menuInput != 4 && menuInput != 5){
                System.out.println("Invalid Input!");
            }

        }

    }

    private static void seperateSentenceForQuestions (String str, Question[]allQuestion, String[]allWords, String[]
            allStopWords, String[] dict){

        String DELIMITERS = "[-+=" +
                " " +        //space
                "\r\n " +    //carriage return line fit
                "1234567890" + //numbers
                "’'\"" +       // apostrophe
                "(){}<>\\[\\]" + // brackets
                ":" +        // colon
                "," +        // comma
                "‒–—―" +     // dashes
                "…" +        // ellipsis
                "!" +        // exclamation mark
                "." +        // full stop/period
                "«»" +       // guillemets
                "-‐" +       // hyphen
                "?" +        // question mark
                "‘’“”" +     // quotation marks
                ";" +        // semicolon
                "/" +        // slash/stroke
                "⁄" +        // solidus
                "␠" +        // space?
                "·" +        // interpunct
                "&" +        // ampersand
                "@" +        // at sign
                "*" +        // asterisk
                "\\" +       // backslash
                "•" +        // bullet
                "^" +        // caret
                "¤¢$€£¥₩₪" + // currency
                "†‡" +       // dagger
                "°" +        // degree
                "¡" +        // inverted exclamation point
                "¿" +        // inverted question mark
                "¬" +        // negation
                "#" +        // number sign (hashtag)
                "№" +        // numero sign ()
                "%‰‱" +      // percent and related signs
                "¶" +        // pilcrow
                "′" +        // prime
                "§" +        // section sign
                "~" +        // tilde/swung dash
                "¨" +        // umlaut/diaeresis
                "_" +        // underscore/understrike
                "|¦" +       // vertical/pipe/broken bar
                "⁂" +        // asterism
                "☞" +        // index/fist
                "∴" +        // therefore sign
                "‽" +        // interrobang
                "※" +          // reference mark
                "]";

        String category, text, ch1, ch2, ch3, ch4, answer;
        int diff;

        Scanner input = new Scanner(str);

        input.useDelimiter("#");

        while (input.hasNext()) {
            category = input.next();
            text = input.next();
            ch1 = input.next();
            ch2 = input.next();
            ch3 = input.next();
            ch4 = input.next();
            answer = input.next();




            String diffString = String.valueOf(input.next().charAt(0));

            diff = Integer.parseInt(diffString);


            Question question = new Question(category, text, ch1, ch2, ch3, ch4, answer, diff);


            for (int i = 0; i < allQuestion.length; i++) {
                if (allQuestion[i] == null) {
                    allQuestion[i] = question;
                    break;
                }

            }

            String lowerCaseVersion = question.getText().toLowerCase(Locale.ENGLISH);
            String[] splitted = lowerCaseVersion.split(DELIMITERS);

            for (int i = 0; i < splitted.length; i++) {
                boolean isAStopWord = false;
                for (int j = 0; j < allStopWords.length; j++) {
                    if (Objects.equals(splitted[i], allStopWords[j])) {
                        isAStopWord = true;
                    }
                }
                if (!isAStopWord) {
                    for (int index = 0; index < allWords.length; index++) {
                        if (allWords[index] == null) {
                            if (!Objects.equals(splitted[i], "")) {
                                String word = spellCheck(dict, splitted[i]);
                                //allWords[index] = splitted[i];
                                String oldWord = splitted[i];
                                if(!Objects.equals(word, oldWord)){
                                    System.out.println();
                                    System.out.println("Old Version: " + oldWord + " New Version: " + word);
                                }
                                splitted[i] = word;
                                allWords[index] = word;
                                question.setText(question.getText().replace(oldWord,word));
                                break;
                            }
                        }
                    }
                }
            }

            question.setQuestionWords(splitted);


        }
        input.close();

    }

    private static void seperateSentenceForParticipants (String str, Participant[]allParticipants){

        String name, birthdate, phone, address;

        Scanner input = new Scanner(str);
        input.useDelimiter("#");


        while (input.hasNext()) {
            name = input.next();
            birthdate = input.next();
            phone = input.next();
            address = input.next();


            Participant participant = new Participant(name, birthdate, phone, address);

            for (int i = 0; i < allParticipants.length; i++) {
                if (allParticipants[i] == null) {
                    allParticipants[i] = participant;
                    break;
                }

            }
        }

    }


    private static String spellCheck(String[] dict, String word){
        for(int i = 0; i<dict.length; i++){
            String difference = "";
            if(dict[i].length() == word.length()){
                int numberOfSameLetters = 0;
                for(int j = 0; j<word.length(); j++){
                    if(Objects.equals(word.charAt(j),dict[i].charAt(j))){
                        numberOfSameLetters++;
                    }else{
                        difference = difference + j;
                    }
                }

                if(numberOfSameLetters == word.length()-1){
                    boolean isInTheDictionary = false;
                    for(int a = 0; a<dict.length ; a++){
                        if(word.equals(dict[a])){
                            isInTheDictionary = true;
                            break;
                        }
                    }
                    if(!isInTheDictionary){
                        return dict[i];
                    }
                }


                else if(difference.length() == 2){

                    int firstWrongIndex = Integer.parseInt(String.valueOf(difference.charAt(0))); //5
                    int secondWrongIndex = Integer.parseInt(String.valueOf(difference.charAt(1))); //8


                    String newWord = "";
                    for(int index = 0; index<word.length();index++){

                        if(index != firstWrongIndex && index != secondWrongIndex){
                            newWord += word.charAt(index);
                        }
                        else if(index == firstWrongIndex){
                            newWord += word.charAt(secondWrongIndex);
                        }

                        else if(index == secondWrongIndex){
                            newWord += word.charAt(firstWrongIndex);
                        }

                    }




                    if(newWord.toString().equals(dict[i])){
                        boolean isInTheDictionary = false;
                        for(int a = 0; a<dict.length ; a++){
                            if(word.equals(dict[a])){
                                isInTheDictionary = true;
                                break;
                            }
                        }
                        if(!isInTheDictionary){
                            return dict[i];
                        }
                    }

                }


            }
        }



        return word;
    }





    private static void readDictionary (String[] dict){
        File file = new File("dictionary.txt");
        Scanner input = null;
        try {
            input = new Scanner(file);
            int i = 0;
            while (input.hasNextLine()) {
                String str = input.nextLine();
                dict[i] = str;
                i++;
            }

        } catch (IOException exp) {
            exp.printStackTrace();
        }

        input.close();
    }




    private static void readLineForQuestions (String txtName, Question[]allQuestions, String[]allWords, String[]
            allStopWords, String[] dict){
        File file = new File(txtName);
        Scanner input = null;
        try {
            input = new Scanner(file);
            while (input.hasNextLine()) {
                String str = input.nextLine();
                String[] qFormat = str.split("#");
                if(qFormat.length == 8){
                    seperateSentenceForQuestions(str, allQuestions, allWords, allStopWords,dict);
                }else{
                    System.out.println("Invalid Question Format \n" + str);
                    System.out.println();
                }
            }

        } catch (IOException exp) {
            exp.printStackTrace();
        }

        input.close();

    }


    private static void readLineForParticipants (String txtName, Participant[]allParticipants){
        File file = new File(txtName);
        Scanner input = null;
        try {
            input = new Scanner(file);
            while (input.hasNextLine()) {
                String str = input.nextLine();
                seperateSentenceForParticipants(str, allParticipants);
            }

        } catch (IOException exp) {
            exp.printStackTrace();
        }

        input.close();

    }

    private static void readStopWords (String[]allStopWords){
        File file = new File("stop_words.txt");
        Scanner input = null;
        try {
            input = new Scanner(file);
            int i = 0;
            while (input.hasNextLine()) {
                String str = input.nextLine();
                allStopWords[i] = str;
                i++;
            }

        } catch (IOException exp) {
            exp.printStackTrace();
        }

        input.close();
    }

    private static int count (String txtName){
        int number = 0;
        File file = new File(txtName);
        Scanner input = null;
        try {
            input = new Scanner(file);
            while (input.hasNextLine()) {
                String str = input.nextLine();
                number++;
            }

        } catch (IOException exp) {
            exp.printStackTrace();
        }

        input.close();

        return number;
    }

    private static int countForQuestions (String txtName){
        int number = 0;
        File file = new File(txtName);
        Scanner input = null;
        try {
            input = new Scanner(file);
            while (input.hasNextLine()) {
                String str = input.nextLine();
                String[] qFormat = str.split("#");
                if(qFormat.length == 8){
                    number++;
                }
            }

        } catch (IOException exp) {
            exp.printStackTrace();
        }

        input.close();

        return number;
    }





}



