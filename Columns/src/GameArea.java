import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Random;

public class GameArea {


	public MultiLinkedList listOfElements = new MultiLinkedList();


	public GameArea(int columnSize) {

		for (int i = 0; i < columnSize; i++) {
			listOfElements.addColumn(String.valueOf(i));
		}
	}

	public void printAllTable(boolean flag) throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {

		Game.cn.getTextWindow().setCursorPosition(35, 4);
		System.out.print("Box( "+Game.box.elementsOfBox.size()+" )");
		Game.cn.getTextWindow().setCursorPosition(35, 5);
		System.out.println("+--+");
		Game.cn.getTextWindow().setCursorPosition(35, 6);
		System.out.println("|  |");
		Game.cn.getTextWindow().setCursorPosition(35, 7);
		System.out.println("+--+");
		Game.cn.getTextWindow().setCursorPosition(0, 0);
		System.out.println(("C1\tC2\tC3\tC4\tC5\t\tTransfer:  " + Game.transfer + " "));
		System.out.println(("--\t--\t--\t--\t--\t\tScore:  " + Game.score + " "));

		Random random = new Random();
		CardVoice.playVoice("printcards.wav");
		for(int i = 0;i<20;i++){
			int randomNumber = random.nextInt(10);
			randomNumber +=1;
			Game.cn.getTextWindow().setCursorPosition(36, 6);
			System.out.println("  ");
			Game.cn.getTextWindow().setCursorPosition(36, 6);
			System.out.println(randomNumber);
			Thread.sleep(50);
		}
		Thread.sleep(500);
		Game.cn.getTextWindow().setCursorPosition(36, 6);
		System.out.println("  ");

		Game.cn.getTextWindow().setCursorPosition(0, 2);
		for (int i = 0; i < 10; i++) {
			if(i<1)
			CardVoice.playVoice("printcards.wav");
			for (int j = 0; j < listOfElements.sizeColumn(); j++) {
				if(i<6){
					Thread.sleep(100);
				}
				if (listOfElements.get(j, i) != null) {
					

					System.out.print(listOfElements.get(j, i) + " ".repeat(Game.width + 1 - listOfElements.get(j, i).toString().length()));
				}
				else
					System.out.print(" ".repeat(Game.width+1));
			}

			System.out.print("\n".repeat(Game.height));
		}
		

		

	}


	public void printAllTable() throws InterruptedException {
		Game.cn.getTextWindow().setCursorPosition(0, 0);
		System.out.println(("C1\tC2\tC3\tC4\tC5\t\tTransfer:  " + Game.transfer + " "));
		System.out.println(("--\t--\t--\t--\t--\t\tScore:  " + Game.score + " "));

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < listOfElements.sizeColumn(); j++) {
				if (listOfElements.get(j, i) != null)
					System.out.print(listOfElements.get(j, i) + " ".repeat(Game.width+1 - listOfElements.get(j, i).toString().length()));
				else
					System.out.print(" ".repeat(Game.width+1));
			}

			System.out.print("\n".repeat(Game.height));
		}

		Game.cn.getTextWindow().setCursorPosition(35, 4);
		System.out.print("Box( "+Game.box.elementsOfBox.size()+" )");
		Game.cn.getTextWindow().setCursorPosition(35, 5);
		System.out.println("+--+");
		Game.cn.getTextWindow().setCursorPosition(35, 6);
		System.out.println("|  |");
		if (Game.box.elementsOfBox.size() != 0) {
			if (Game.isCardDrawed) {
				Game.cn.getTextWindow().setCursorPosition(36, 6);
				System.out.println(Game.drawedCard);
			} else {
				Game.cn.getTextWindow().setCursorPosition(36, 6);
				System.out.println(" ");
			}
		} else {
			Game.cn.getTextWindow().setCursorPosition(36, 6);
			System.out.println("X");

		}

		Game.cn.getTextWindow().setCursorPosition(35, 7);
		System.out.println("+--+");


	}

}
