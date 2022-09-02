import enigma.console.TextAttributes;
import enigma.core.Enigma;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;
import java.util.Random;

public class Movement {
   public enigma.console.Console cn = Enigma.getConsole("Star Trek Warp Wars", 150, 150, 18);
   public KeyListener klis;
   Player player = new Player();
   Computer computer = new Computer();
   public static long timeForQueue = 0;
   public static long time = 0;
   public static boolean playerLostItsLife = false;


   public int keypr;
   public int rkey;


   Movement(Object[][] arr, Queue inputQueue) throws Exception {   // --- Contructor

      Random random = new Random();
      int px, py;
      while (true) {

         int randomCoordinateOfPlayerX = random.nextInt(55);
         int randomCoordinateOfPlayerY = random.nextInt(23);

         if (Objects.equals(arr[randomCoordinateOfPlayerY][randomCoordinateOfPlayerX], " ")) {
            arr[randomCoordinateOfPlayerY][randomCoordinateOfPlayerX] = 'P';
            px = randomCoordinateOfPlayerX;
            py = randomCoordinateOfPlayerY;
            break;
         }

      }



      while (true) {

         int randomCoordinateOfComputerX = random.nextInt(55);
         int randomCoordinateOfComputerY = random.nextInt(23);

         if (Objects.equals(arr[randomCoordinateOfComputerY][randomCoordinateOfComputerX], " ")) {
            Treasure defaultComputer = new Treasure("C");
            arr[randomCoordinateOfComputerY][randomCoordinateOfComputerX] = defaultComputer;

            break;
         }

      }
      placedElementsInInputQueueFirst20(arr, inputQueue);

      printAll(arr, inputQueue);


      klis = new KeyListener() {
         public void keyTyped(KeyEvent e) {
         }

         public void keyPressed(KeyEvent e) {
            if (keypr == 0) {
               keypr = 1;
               rkey = e.getKeyCode();
            }
         }

         public void keyReleased(KeyEvent e) {
         }
      };
      cn.getTextWindow().addKeyListener(klis);


      cn.getTextWindow().output(px, py, 'P');
      long oldTime = 0;
      int tourForNumbers= -1;
      int tourForComputer = -1;
      while (true) {



         if(player.lives == 0){
            cn.getTextWindow().setCursorPosition(0,23);
            System.out.print("GAME OVER, ");
            System.out.print("Your Score: " + (player.score - Computer.score));
            break;

         }


         Treasure.deActiveTrap(arr);
         Treasure.deActiveWarp(arr);


         if(playerLostItsLife){

            random = new Random();
            while (true) {

               int randomCoordinateOfPlayerX = random.nextInt(55);
               int randomCoordinateOfPlayerY = random.nextInt(23);

               if (Objects.equals(arr[randomCoordinateOfPlayerY][randomCoordinateOfPlayerX], " ")) {
                  arr[randomCoordinateOfPlayerY][randomCoordinateOfPlayerX] = 'P';
                  px = randomCoordinateOfPlayerX;
                  py = randomCoordinateOfPlayerY;
                  break;
               }

            }
            playerLostItsLife = false;
         }

         Treasure.trapDevice(arr);
         Treasure.warpDevice(arr);
         checkNeighbor(arr);
         printAll(arr, inputQueue);

         Treasure lastMovedNumber = null;
         if(player.energy>0){
            if(tourForNumbers==0){
               for (int i = 0; i < 23; i++) {
                  for (int j = 0; j < 55; j++) {
                     try {
                        if (Objects.equals(((Treasure) (arr[i][j])).value, "4") || Objects.equals(((Treasure) (arr[i][j])).value, "5")) {
                           if (!((Treasure) (arr[i][j])).caught && lastMovedNumber!=((arr[i][j]))) {
                              lastMovedNumber = ((Treasure) arr[i][j]);
                              ((Treasure) (arr[i][j])).moveNumbers(arr,i,j,((Treasure) (arr[i][j])).value);
                              printAll(arr, inputQueue);
                           }
                        }
                     } catch (ClassCastException ignored) {

                     }
                  }
               }
               tourForNumbers = -1;

            }else{
               tourForNumbers++;
            }

         }else{
            for (int i = 0; i < 23; i++) {
               for (int j = 0; j < 55; j++) {
                  try {
                     if (Objects.equals(((Treasure) (arr[i][j])).value, "4") || Objects.equals(((Treasure) (arr[i][j])).value, "5")) {
                        if (!((Treasure) (arr[i][j])).caught && lastMovedNumber!=((arr[i][j]))) {
                           lastMovedNumber = ((Treasure) arr[i][j]);
                           ((Treasure) (arr[i][j])).moveNumbers(arr,i,j,((Treasure) (arr[i][j])).value);
                           printAll(arr, inputQueue);
                        }
                     }
                  } catch (ClassCastException ignored) {

                  }
               }
            }
         }

         Treasure lastMovedComputer = null;
         if(player.energy>0){
            if(tourForComputer ==0){
               for (int i = 0; i < 23; i++) {
                  for (int j = 0; j < 55; j++) {
                     if (!Objects.equals(arr[i][j], " ") && !Objects.equals(arr[i][j], "#") && !Objects.equals(arr[i][j], 'P')) {
                        try {
                           if (Objects.equals(((Treasure) arr[i][j]).value, "C") && lastMovedComputer != arr[i][j] && !((Treasure) arr[i][j]).caught) {
                              if (((Treasure) arr[i][j]).closeTargetRota.isEmpty()) {
                                 ((Treasure) arr[i][j]).xCom = j;
                                 ((Treasure) arr[i][j]).yCom = i;
                                 ((Treasure) arr[i][j]).chooseTargetForComputer(arr);
                              } else {
                                 lastMovedComputer = ((Treasure) arr[i][j]);
                                 ((Treasure) arr[i][j]).moveComputer(arr, computer,player);
                                 printAll(arr, inputQueue);
                              }
                           }
                        } catch (ClassCastException ignored) {
                        }

                     }
                  }
               }
               tourForComputer =-1;
            }else{
               tourForComputer++;
            }
         }else{
            for (int i = 0; i < 23; i++) {
               for (int j = 0; j < 55; j++) {
                  if (!Objects.equals(arr[i][j], " ") && !Objects.equals(arr[i][j], "#") && !Objects.equals(arr[i][j], 'P')) {
                     try {
                        if (Objects.equals(((Treasure) arr[i][j]).value, "C") && lastMovedComputer != arr[i][j] && !((Treasure) arr[i][j]).caught) {
                           if (((Treasure) arr[i][j]).closeTargetRota.isEmpty()) {
                              ((Treasure) arr[i][j]).xCom = j;
                              ((Treasure) arr[i][j]).yCom = i;
                              ((Treasure) arr[i][j]).chooseTargetForComputer(arr);
                           } else {
                              lastMovedComputer = ((Treasure) arr[i][j]);
                              ((Treasure) arr[i][j]).moveComputer(arr, computer,player);
                              printAll(arr, inputQueue);
                           }
                        }
                     } catch (ClassCastException ignored) {
                     }

                  }
               }
            }
         }

         if(player.energy>0){
            Thread.sleep(250);
         }else{
            Thread.sleep(500);
         }

         if (keypr == 1) {    // if keyboard button pressed
            if (rkey == KeyEvent.VK_LEFT) {
               try {
                  if (Objects.equals((arr[py][px - 1]), " ")) {
                     arr[py][px] = " ";
                     arr[py][px - 1] = 'P';
                     px--;
                  } else if (Objects.equals(((Treasure) arr[py][px - 1]).value, "1")
                          || Objects.equals(((Treasure) arr[py][px - 1]).value, "2") || Objects.equals(((Treasure) arr[py][px - 1]).value, "3") ||
                          Objects.equals(((Treasure) arr[py][px - 1]).value, "4") || Objects.equals(((Treasure) arr[py][px - 1]).value, "5") ||
                          Objects.equals(((Treasure) arr[py][px - 1]).value, "*") || Objects.equals(((Treasure) arr[py][px - 1]).value, "=")) {
                     if(Objects.equals(((Treasure) arr[py][px - 1]).value, "=")){
                        Treasure.makeCaughtFalse(py,(px-1),arr);
                     }
                     player.addElementToBackpack((Treasure) arr[py][px - 1]);
                     arr[py][px] = " ";
                     arr[py][px - 1] = 'P';
                     px--;
                  }
               } catch (ClassCastException ignored) {

               }

            }
            if (rkey == KeyEvent.VK_RIGHT) {
               try {
                  if (Objects.equals((arr[py][px + 1]), " ")) {
                     arr[py][px] = " ";
                     arr[py][px + 1] = 'P';
                     px++;
                  } else if (Objects.equals(((Treasure) arr[py][px + 1]).value, "1")
                          || Objects.equals(((Treasure) arr[py][px + 1]).value, "2") || Objects.equals(((Treasure) arr[py][px + 1]).value, "3") ||
                          Objects.equals(((Treasure) arr[py][px + 1]).value, "4") || Objects.equals(((Treasure) arr[py][px + 1]).value, "5") ||
                          Objects.equals(((Treasure) arr[py][px + 1]).value, "*") || Objects.equals(((Treasure) arr[py][px + 1]).value, "=")) {
                     if(Objects.equals(((Treasure) arr[py][px + 1]).value, "=")){
                        Treasure.makeCaughtFalse(py,(px+1),arr);
                     }
                     player.addElementToBackpack((Treasure) arr[py][px + 1]);
                     arr[py][px] = " ";
                     arr[py][px + 1] = 'P';
                     px++;
                  }
               } catch (ClassCastException ignored) {

               }
            }

            if (rkey == KeyEvent.VK_UP) {
               try {
                  if (Objects.equals((arr[py - 1][px]), " ")) {
                     arr[py][px] = " ";
                     arr[py - 1][px] = 'P';
                     py--;
                  } else if (Objects.equals(((Treasure) arr[py - 1][px]).value, "1")
                          || Objects.equals(((Treasure) arr[py - 1][px]).value, "2") || Objects.equals(((Treasure) arr[py - 1][px]).value, "3") ||
                          Objects.equals(((Treasure) arr[py - 1][px]).value, "4") || Objects.equals(((Treasure) arr[py - 1][px]).value, "5") ||
                          Objects.equals(((Treasure) arr[py - 1][px]).value, "*") || Objects.equals(((Treasure) arr[py - 1][px]).value, "=")) {
                     if(Objects.equals(((Treasure) arr[py-1][px]).value, "=")){
                        Treasure.makeCaughtFalse(py-1,(px),arr);
                     }
                     player.addElementToBackpack((Treasure) arr[py - 1][px]);
                     arr[py][px] = " ";
                     arr[py - 1][px] = 'P';
                     py--;
                  }
               } catch (ClassCastException ignored) {

               }
            }
            if (rkey == KeyEvent.VK_DOWN) {
               try {
                  if (Objects.equals((arr[py + 1][px]), " ")) {
                     arr[py][px] = " ";
                     arr[py + 1][px] = 'P';
                     py++;
                  } else if (Objects.equals(((Treasure) arr[py + 1][px]).value, "1")
                          || Objects.equals(((Treasure) arr[py + 1][px]).value, "2") || Objects.equals(((Treasure) arr[py + 1][px]).value, "3") ||
                          Objects.equals(((Treasure) arr[py + 1][px]).value, "4") || Objects.equals(((Treasure) arr[py + 1][px]).value, "5") ||
                          Objects.equals(((Treasure) arr[py + 1][px]).value, "*") || Objects.equals(((Treasure) arr[py + 1][px]).value, "=")) {
                     if(Objects.equals(((Treasure) arr[py+1][px]).value, "=")){
                        Treasure.makeCaughtFalse(py+1,(px),arr);
                     }
                     player.addElementToBackpack((Treasure) arr[py + 1][px]);
                     arr[py][px] = " ";
                     arr[py + 1][px] = 'P';
                     py++;
                  }
               } catch (ClassCastException ignored) {

               }
            }


            if (rkey == KeyEvent.VK_A) {
               if (Objects.equals(arr[py][px - 1], " ")) {
                  if (player.backpack.peek() != null) {
                     Treasure deletedElement = (Treasure) player.backpack.pop();
                     if (!isItNumber(deletedElement.value)) {
                        arr[py][px - 1] = deletedElement;
                     }
                  }

               }
            }

            if (rkey == KeyEvent.VK_D) {
               if (Objects.equals(arr[py][px + 1], " ")) {
                  if (player.backpack.peek() != null) {
                     Treasure deletedElement = ((Treasure) player.backpack.pop());
                     if (!isItNumber(deletedElement.value)) {
                        arr[py][px + 1] = deletedElement;
                     }
                  }
               }
            }

            if (rkey == KeyEvent.VK_W) {
               if (Objects.equals(arr[py - 1][px], " ")) {
                  if (player.backpack.peek() != null) {
                     Treasure deletedElement = ((Treasure) player.backpack.pop());
                     if (!isItNumber(deletedElement.value)) {
                        arr[py - 1][px] = deletedElement;
                     }
                  }
               }

            }
            if (rkey == KeyEvent.VK_S) {
               if (Objects.equals(arr[py + 1][px], " ")) {
                  if (player.backpack.peek() != null) {
                     Treasure deletedElement = ((Treasure) player.backpack.pop());
                     if (!isItNumber(deletedElement.value)) {
                        arr[py + 1][px] = deletedElement;
                     }
                  }
               }

            }


            char rckey = (char) rkey;
            //        left          right          up            down
            if (rckey == '%' || rckey == '\'' || rckey == '&' || rckey == '(') {

               //consoleClear(cn);
               printAll(arr, inputQueue);
            } else cn.getTextWindow().output(rckey);



            keypr = 0;
         }


         if (time < 1000) {
            cn.getTextWindow().setCursorPosition(0, 24);
            System.out.println("Time: " + 0);

            cn.getTextWindow().setCursorPosition(0, 26);

            System.out.println("P.Energy: " + player.energy);
         }


         if (time >= 1000) {
            cn.getTextWindow().setCursorPosition(0, 24);
            System.out.println("Time: " + (time / 1000));

            if ((time - oldTime >= 1000) && (player.energy > 0)) {
               cn.getTextWindow().setCursorPosition(0, 26);
               System.out.println("                            ");
               cn.getTextWindow().setCursorPosition(0, 26);
               player.energy = player.energy - 1;
               System.out.println("P.Energy: " + player.energy);
               oldTime = time;
            }

         }


         if (timeForQueue >= 3000) {
            placedElementsIneEveryThreeSec(arr, inputQueue);
            cn.getTextWindow().setCursorPosition(0, 30);
            System.out.println("INPUT");
            System.out.println("<<<<<<<<<<<<<<<");
            inputQueue.getFirst15();
            System.out.println("\n<<<<<<<<<<<<<<<");
            timeForQueue = 0;
         }


         cn.getTextWindow().setCursorPosition(0,23);

         if(player.energy>0){
            timeForQueue = timeForQueue + 250;
            time = time + 250;
         }else{
            timeForQueue = timeForQueue + 500;
            time = time + 500;
         }
         for (int i = 0; i < 23; i++) {
            for (int j = 0; j < 55; j++) {
               try {
                  if (Objects.equals(((Treasure) (arr[i][j])).value, "=")) {
                     if(player.energy>0){
                        ((Treasure) (arr[i][j])).trapTime = (((Treasure) (arr[i][j])).trapTime + 250);
                     }else{
                        ((Treasure) (arr[i][j])).trapTime = (((Treasure) (arr[i][j])).trapTime + 500);
                     }
                  }
               } catch (ClassCastException ignored) {

               }
            }
         }

         for (int i = 0; i < 23; i++) {
            for (int j = 0; j < 55; j++) {
               try {
                  if (Objects.equals(((Treasure) (arr[i][j])).value, "*")) {
                     if(player.energy>0){
                        ((Treasure) (arr[i][j])).trapTime = (((Treasure) (arr[i][j])).trapTime + 250);
                     }else{
                        ((Treasure) (arr[i][j])).trapTime = (((Treasure) (arr[i][j])).trapTime + 500);
                     }
                  }
               } catch (ClassCastException ignored) {

               }
            }
         }

      }
   }


   private static int randomNumberBetween(int lowBound, int highBound) {

      if (lowBound >= highBound) {
         throw new IllegalArgumentException("min cannot be greater than max");
      }

      Random randomNumber = new Random();
      return randomNumber.nextInt((highBound - lowBound) + 1) + lowBound;
   }



   private void printAll(Object[][] arr, Queue inputQueue) {
      cn.getTextWindow().setCursorPosition(0, 0);
      for (int i = 0; i < 23; i++) {
         for (int j = 0; j < 55; j++) {
            try {
               System.out.print(((Treasure) arr[i][j]).value);
            }
             catch (ClassCastException err) {
               System.out.print(arr[i][j]);
            }
         }
         System.out.println();
      }


      cn.getTextWindow().setCursorPosition(0, 25);
      System.out.println("                                    ");
      cn.getTextWindow().setCursorPosition(0, 25);

      System.out.print("Player Backpack: ");
      for (int i = 0; i < player.backpack.size(); i++) {
         System.out.print(((Treasure) player.backpack.get(i)).value);
      }


      cn.getTextWindow().setCursorPosition(0, 27);
      System.out.println("                                    ");
      cn.getTextWindow().setCursorPosition(0, 27);

      System.out.println("P.Score: " + player.score);

      cn.getTextWindow().setCursorPosition(0, 28);
      System.out.println("                                    ");
      cn.getTextWindow().setCursorPosition(0, 28);

      System.out.println("P.Life: " + player.lives);

      cn.getTextWindow().setCursorPosition(0, 29);
      System.out.println("                                    ");
      cn.getTextWindow().setCursorPosition(0, 29);

      System.out.println("C.Score: " + Computer.score);

      cn.getTextWindow().setCursorPosition(0, 30);
      System.out.println("                                    ");
      System.out.println("                                    ");
      System.out.println("                                    ");
      cn.getTextWindow().setCursorPosition(0, 30);

      System.out.println("INPUT");
      System.out.println("<<<<<<<<<<<<<<<");
      inputQueue.getFirst15();
      System.out.println("\n<<<<<<<<<<<<<<<");

   }




   private void placedElementsInInputQueueFirst20(Object[][] arr, Queue inputQueue) throws InterruptedException {

      int placed = 0;
      while (placed < 20) {
         int randomX = randomNumberBetween(1, 54);
         int randomY = randomNumberBetween(1, 22);

         if (Objects.equals(arr[randomY][randomX], " ")) {
            Treasure treasure = (Treasure) inputQueue.dequeue();
            arr[randomY][randomX] = treasure;
            placed++;
         }
      }
   }

   private void placedElementsIneEveryThreeSec(Object[][] arr, Queue inputQueue) {
      boolean placed = false;
      while (!placed) {
         int randomX = randomNumberBetween(1, 54);
         int randomY = randomNumberBetween(1, 22);

         if (Objects.equals(arr[randomY][randomX], " ")) {
            Treasure treasure = (Treasure) inputQueue.dequeue();
            arr[randomY][randomX] = treasure;
            placed = true;
         }
      }
      printAll(arr, inputQueue);

   }

   public static boolean isItNumber(String str) {
      try {
         Double.parseDouble(str);
         return true;
      } catch (NumberFormatException e) {
         return false;
      }
   }




   public void checkNeighbor(Object[][] arr) {

      for (int i = 0; i < 23; i++) {
         for (int j = 0; j < 55; j++) {
            try {
               if (Objects.equals(((Treasure) arr[i][j]).value, "C")) {
                  try {
                     if (Objects.equals((arr[i + 1][j]), 'P')) {
                        player.backpack.pop();
                        player.backpack.pop();
                     }
                  } catch (ClassCastException ignored) {

                  }

                  try {
                     if (Objects.equals((arr[i][j + 1]), 'P')) {
                        player.backpack.pop();
                        player.backpack.pop();
                     }
                  } catch (ClassCastException ignored) {

                  }

                  try {
                     if (Objects.equals((arr[i - 1][j]), 'P')) {
                        player.backpack.pop();
                        player.backpack.pop();
                     }
                  } catch (ClassCastException ignored) {

                  }

                  try {
                     if (Objects.equals((arr[i][j - 1]), 'P')) {
                        player.backpack.pop();
                        player.backpack.pop();
                     }
                  } catch (ClassCastException ignored) {

                  }

                  try {
                     if (Objects.equals((arr[i + 1][j + 1]), 'P')) {
                        player.backpack.pop();
                        player.backpack.pop();
                     }
                  } catch (ClassCastException ignored) {

                  }

                  try {
                     if (Objects.equals((arr[i - 1][j - 1]), 'P')) {
                        player.backpack.pop();
                        player.backpack.pop();
                     }
                  } catch (ClassCastException ignored) {

                  }

                  try {
                     if (Objects.equals((arr[i + 1][j - 1]), 'P')) {
                        player.backpack.pop();
                        player.backpack.pop();
                     }
                  } catch (ClassCastException ignored) {

                  }

                  try {
                     if (Objects.equals((arr[i - 1][j + 1]), 'P')) {
                        player.backpack.pop();
                        player.backpack.pop();
                     }
                  } catch (ClassCastException ignored) {

                  }


               }

            } catch (ClassCastException ignored) {

            }

         }

      }
   }

   public void white() {
      TextAttributes  write = new TextAttributes(Color.white);
      cn.setTextAttributes(write);
   }
   public void green() {
      TextAttributes  write = new TextAttributes(Color.green);
      cn.setTextAttributes(write);
   }
   public void blue() {
      TextAttributes  write = new TextAttributes(Color.blue);
      cn.setTextAttributes(write);
   }

   public void red() {
      TextAttributes  write = new TextAttributes(Color.red);
      cn.setTextAttributes(write);
   }

}









