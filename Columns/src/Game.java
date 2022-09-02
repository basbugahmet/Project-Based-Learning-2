import enigma.console.TextAttributes;
import enigma.core.Enigma;
import enigma.event.TextMouseEvent;
import enigma.event.TextMouseListener;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import javax.print.attribute.standard.MediaSize.Engineering;

public class Game {// 100, 80, 20
	static public enigma.console.Console cn = Enigma.getConsole("Columns", 120, 30, 20);
	public KeyListener klis;
	public TextMouseListener tmlis;

	// ------ Standard variables for mouse and keyboard ------
	public int mousepr; // mouse pressed?
	public int mousex, mousey; // mouse text coords.

	public int keypr; // key pressed?
	public int rkey; // key (for press/release)
	public GameArea gameArea = new GameArea(5);
	public static Box box = new Box();
	public SingleLinkedList numbersMoved = new SingleLinkedList();
	public DoubleLinkedList highScoreTable = new DoubleLinkedList();
	static public String drawedCard;
	static public int drawedCardIndex;
	static public boolean isCardDrawed;
	public int lastColumn;
	public static int transfer = 0;
	public static int score = 0;
	public static int px = 0, py = 0;
	public static int width = 5;
	public static int height = 2;

	// ----------------------------------------------------

	@SuppressWarnings({ "unused", "resource" })
	Game() throws Exception { // --- Contructor

		distributor(box, gameArea);
		gameArea.printAllTable(true);

		// ------ Standard code for mouse and keyboard ------ Do not change
		tmlis = new TextMouseListener() {
			public void mouseClicked(TextMouseEvent arg0) {
			}

			public void mousePressed(TextMouseEvent arg0) {
				if (mousepr == 0) {
					mousepr = 1;
					mousex = arg0.getX();
					mousey = arg0.getY();
				}
			}

			public void mouseReleased(TextMouseEvent arg0) {
			}
		};
		cn.getTextWindow().addTextMouseListener(tmlis);
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
		// ----------------------------------------------------

		px = 0;
		py = 2;

		String element = gameArea.listOfElements.get(px / width, (py - 2) / height).toString();
		cn.getTextWindow().setCursorPosition(px, py);
		red();
		System.out.println(element);
		white();
		boolean exit = true;
		while (score < 5000 && exit) {

			if (keypr == 1) { // if keyboard button pressed
				if (rkey == KeyEvent.VK_LEFT && px != 0) {
					int columnRealSizeBefore = gameArea.listOfElements.columnRealSize(px / width - 1);
					int columnRealSizeCurrent = gameArea.listOfElements.columnRealSize(px / width);
					if (py / height > columnRealSizeBefore && columnRealSizeBefore < columnRealSizeCurrent) {
						py = (columnRealSizeBefore + 1) * height - height;
						if (py == 0)
							py += 2;
					}

					px = px - (width + 1);

				}

				if (rkey == KeyEvent.VK_RIGHT && px != (width + 1) * 4) {
					int columnRealSizeAfter = gameArea.listOfElements.columnRealSize(px / width + 1);
					int columnRealSizeCurrent = gameArea.listOfElements.columnRealSize(px / width);
					if (py / height > columnRealSizeAfter && columnRealSizeAfter < columnRealSizeCurrent) {
						py = (columnRealSizeAfter + 1) * height - height;
						if (py == 0)
							py += 2;
					}
					px = px + width + 1;
				}
				if (rkey == KeyEvent.VK_UP && py != 2)
					py -= height;//
				if (rkey == KeyEvent.VK_DOWN
						&& (py) <= (10 - gameArea.listOfElements.numberOfElement(px / width, " ")) * height - height)// -height
					py += height;
				if (rkey == KeyEvent.VK_Z && numbersMoved.size() == 0) {
					for (int i = 9; i > (py - height - 2) / height; i--) {
						Object data = gameArea.listOfElements.deleteByIndex(px / width, i);
						if (data != null) {
							numbersMoved.add(data);
							gameArea.listOfElements.addItem(String.valueOf(px / width), " ");
						}
					}
					if (!gameArea.listOfElements.isColumnSpace(px / width))
						py -= height;
					lastColumn = px / width;

					gameArea.printAllTable();

				}
				if (mousepr == 1) { // if mouse button pressed

					if (gameArea.listOfElements.get(mousex / width, (mousey - 2) / height) != null) {
						px = mousex;
						py = mousey;
					}

					mousepr = 0; // last action
				}

				if (rkey == KeyEvent.VK_B && numbersMoved.size() == 0 && box.elementsOfBox.size() != 0) {
					if (!isCardDrawed) {
						CardVoice.playVoice("flipcard.wav");
						Random random = new Random();
						drawedCardIndex = random.nextInt(box.elementsOfBox.size());
						drawedCard = (String) box.elementsOfBox.get(drawedCardIndex);

						isCardDrawed = true;
					} else {
						String drawedCardWithSign = "D" + drawedCard;

						numbersMoved.add(drawedCardWithSign);
					}
				} //
				if (rkey == KeyEvent.VK_X && numbersMoved.size() != 0 && transferCheck()) {

					int numberOfEmptyPlaceInColumn = gameArea.listOfElements.numberOfElement(px / width, " ");
					int numbersOfMovedNumbers = numbersMoved.size() - numbersMoved.numberOfElement(" ");

					String current = gameArea.listOfElements.get(px / width, (py - 2) / height).toString();
					int j = 0;

					if (numberOfEmptyPlaceInColumn >= numbersOfMovedNumbers) {

						int k = 9;
						int empty = numberOfEmptyPlaceInColumn;
						while (empty > 0) {
							gameArea.listOfElements.deleteByIndex(px / width, k);
							empty--;
							k--;
						}

						for (int i = numbersMoved.size() - 1; i >= 0; i--) {
							if (!Objects.equals(numbersMoved.get(i), " ")) {
								String elementInList = (String) numbersMoved.get(i);
								if (elementInList != null) {
									if (i == numbersMoved.size() - 1)
										transfer++;

									if (elementInList.charAt(0) == 'D') {
										isCardDrawed = false;
										gameArea.listOfElements.addItem(String.valueOf(px / width), drawedCard);
										// box.elementsOfBox.delete(drawedCard);
										box.elementsOfBox.deleteByIndex(drawedCardIndex);

										j++;
									} else {

										gameArea.listOfElements.addItem(String.valueOf(px / width),
												(String) numbersMoved.get(i));
										j++;
									}
								}
							}

						}

						for (int i = 0; i < numberOfEmptyPlaceInColumn - numbersOfMovedNumbers; i++) {

							gameArea.listOfElements.addItem(String.valueOf(px / width), " ");
						}
						numbersMoved = new SingleLinkedList();

					}
					socreUpdater();
					gameArea.printAllTable();
				}

				gameArea.printAllTable();

				element = (String) gameArea.listOfElements.get(px / width, (py - 2) / height);
				cn.getTextWindow().setCursorPosition(px, py);
				red();
				System.out.println(element);
				white();

				if (rkey == KeyEvent.VK_E) {

					exit = false;

				}

				if (rkey == KeyEvent.VK_1) {

					score = 5000;
					gameArea.listOfElements = new GameArea(5).listOfElements;
					gameArea.printAllTable();

				}

			}

			keypr = 0; // last action
			Thread.sleep(20);

		}
		if (score==5000) {
			Scanner sc = new Scanner(System.in);
			if (transfer < 1)
				transfer = 1;
			double endGameScore =  (double)score * (0.1 + 1 / (double)transfer);
			endGameScore=Math.floor(endGameScore);
			cn.getTextWindow().setCursorPosition(0, 15);
			System.out.println("Your endgame score: " + endGameScore);
			System.out.print("Please enter your name: ");
			save(new Participant(sc.nextLine(), endGameScore));
		}
		readHighScoreTable();
		cn.getTextWindow().setCursorPosition(0, 20);
		System.out.println("HIGH SCORE TABLE");
		System.out.println("----------------");
		readHighScoreTable();
		highScoreTable.display(10);
	}

	public void socreUpdater() {
		boolean flag1 = false;
		int index = -1;
		boolean flag2 = false;
		for (int i = 0; i < 5; i++) {
			if (gameArea.listOfElements.numberOfElement(i, " ") == 0) {
				for (int j = 0; j < 10; j++) {
					if (j + 1 == Integer.parseInt(gameArea.listOfElements.get(i, j).toString()))
						flag1 = true;

					else {

						flag1 = false;
						break;

					}
				}
			}

			if (flag1) {
				index = i;
				break;
			}

		}
		for (int i = 0; i < 5; i++) {
			if (gameArea.listOfElements.numberOfElement(i, " ") == 0) {
				for (int j = 0; j < 10; j++) {
					if (10 - j == Integer.parseInt(gameArea.listOfElements.get(i, j).toString()))
						flag2 = true;

					else {

						flag2 = false;
						break;
					}
				}
			}
			if (flag2) {
				index = i;
				break;
			}

		}

		if (flag1 || flag2) {
			for (int i = 9; i >= 0; i--) {
				gameArea.listOfElements.deleteByIndex(index, i);
				gameArea.listOfElements.addItem(String.valueOf(index), " ");
			}
			score += 1000;
		}

	}

	public boolean transferCheck() {
		if (px / width == lastColumn)
			return true;

		String frontElementInList = (String) numbersMoved.get(numbersMoved.size() - 1);
		if (!gameArea.listOfElements.isColumnSpace(px / width)) {
			int index = gameArea.listOfElements.sizeItem(px / width)
					- gameArea.listOfElements.numberOfElement(px / width, " ") - 1;
			int lastElementInMultiList = Integer.parseInt(gameArea.listOfElements.get(px / width, index).toString());

			if (frontElementInList.charAt(0) == 'D') {
				int drawedCardInt = Integer.parseInt(drawedCard);
				if (drawedCardInt == lastElementInMultiList || drawedCardInt == lastElementInMultiList - 1
						|| drawedCardInt == lastElementInMultiList + 1)
					return true;

			} else {
				int frontElementInListInt = Integer.parseInt(frontElementInList);
				if (frontElementInListInt == lastElementInMultiList
						|| frontElementInListInt == lastElementInMultiList - 1
						|| frontElementInListInt == lastElementInMultiList + 1)
					return true;
			}
		} else {
			if (frontElementInList.charAt(0) == 'D') {
				int drawedCardInt = Integer.parseInt(drawedCard);
				if (drawedCardInt == 10 || drawedCardInt == 1)
					return true;

			} else {
				int frontElementInListInt = Integer.parseInt(frontElementInList);
				if (frontElementInListInt == 10 || frontElementInListInt == 1)
					return true;
			}
		}
		return false;

	}

	public void distributor(Box box, GameArea gameArea) {

		Random random = new Random();
		for (int i = 0; i < gameArea.listOfElements.sizeColumn(); i++) {
			for (int j = 0; j < 6; j++) {

				boolean distributeOK = false;
				while (!distributeOK) {
					int index = random.nextInt(51);
					try {

						String elementWillBeAdded = (String) box.elementsOfBox.get(index);
						if (elementWillBeAdded != null) {
							gameArea.listOfElements.addItem(String.valueOf(i), elementWillBeAdded);
							box.elementsOfBox.deleteByIndex(index);
							distributeOK = true;
						}
					} catch (IndexOutOfBoundsException outOfBound) {
					}
				}
			}

		}

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 4; j++) {
				gameArea.listOfElements.addItem(String.valueOf(i), " ");
			}
		}
	}

	public static void red() {
		TextAttributes red = new TextAttributes(Color.red);
		cn.setTextAttributes(red);
	}

	public static void white() {
		TextAttributes write = new TextAttributes(Color.white);
		cn.setTextAttributes(write);
	}

	public void readHighScoreTable() {
		DoubleLinkedList linkedList = new DoubleLinkedList();
		File file = new File("highscore.txt");
		Scanner sc = null;
		try {
			sc = new Scanner(file);
			while (sc.hasNextLine())
				linkedList.add(new Participant(sc.nextLine()));
		} catch (IOException exp) {
			exp.printStackTrace();
		}
		sc.close();
		highScoreTable = linkedList;
	}

	@SuppressWarnings("unused")
	public void save(Participant participant) throws IOException {
		highScoreTable.add(participant);
		FileWriter fileWriter = new FileWriter(new File("highscore.txt"), true);
		String str = " ".repeat(15 - participant.getName().length());
		fileWriter.write("\n" + participant.getName() + " ".repeat(15 - participant.getName().length())
				+ participant.getScore());
		fileWriter.close();
	}
}
