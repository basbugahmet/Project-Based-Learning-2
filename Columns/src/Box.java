public class Box {

	public SingleLinkedList elementsOfBox = new SingleLinkedList();

	public Box(){

		for (int i = 0; i < 5; i++) {
			for (int j = 1; j <= 10; j++) {
				elementsOfBox.add(String.valueOf(j));
			}
		}
		

	}

}
