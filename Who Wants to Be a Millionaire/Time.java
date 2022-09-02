import java.time.LocalTime;
import java.util.ArrayList;

public class Time extends Thread{
    public int leftTime;
    public int startSecond;
    public boolean isTimeFinished;
    public boolean pause=false;
    int[] printed;
    LocalTime myObj;
    int currentSecond;

    EnigmaConsoleCreator enigmaCC =new EnigmaConsoleCreator();

    public void run()
    {
        leftTime=20;
        myObj= LocalTime.now();
        currentSecond=myObj.getMinute()*60+myObj.getSecond();
        printed= new int[100];
        LocalTime myObj = LocalTime.now();
        startSecond=myObj.getMinute()*60+myObj.getSecond();
        StartDecreasingTime();
    }
    public void StartDecreasingTime()
    {


        while(currentSecond-startSecond<20 && !pause)
        {
            myObj = LocalTime.now();
            currentSecond=myObj.getMinute()*60+myObj.getSecond();

            boolean found = false;

            for(int i = 0; i< printed.length; i++){
                if (printed[i] == (currentSecond - startSecond)) {
                    found = true;
                    break;
                }
            }

            if(!found)
            {
                int x =enigmaCC.cn.getTextWindow().getCursorX();
                int y =enigmaCC.cn.getTextWindow().getCursorY();

                enigmaCC.cn.getTextWindow().setCursorPosition(50, 48);
                if((20-(currentSecond-startSecond))>=10)
                    System.out.println("Remaining Time:" +(20-(currentSecond-startSecond)));
                else
                    System.out.println("Remaining Time: " +(20-(currentSecond-startSecond)));
                enigmaCC.cn.getTextWindow().setCursorPosition(x, y);
                for(int j = 0; j< printed.length; j++){
                    if(printed[j] == 0){
                        printed[j] = currentSecond - startSecond;
                        break;
                    }
                }
            }
        }
        if((20-(currentSecond-startSecond))==0) {
            System.out.println();
            System.out.println("Time is finished, press enter to see your prize...");
            isTimeFinished = true;
        }

    }
    public void ReAssignStartTime()
    {
        leftTime=20;
        myObj= LocalTime.now();
        currentSecond=myObj.getMinute()*60+myObj.getSecond();
        printed= new int[100];
        LocalTime myObj = LocalTime.now();
        startSecond=myObj.getMinute()*60+myObj.getSecond();
    }


}
