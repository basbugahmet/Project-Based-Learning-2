

public class MultiLinkedList {
	ColumnNode head;

	public void addColumn(String dataToAdd) {
		ColumnNode temp;
		if (head == null) {
			temp = new ColumnNode(dataToAdd);
			head = temp;
		} else {
			temp = head;
			while (temp.getDown() != null)
				temp = temp.getDown();
			ColumnNode newnode = new ColumnNode(dataToAdd);
			temp.setDown(newnode);
		}
	}

	public void addItem(String Column, String Item) {
		if (head == null)
			System.out.println("Add a Category before Item");
		else {
			ColumnNode temp = head;
			while (temp != null) {
				if (Column.equals(temp.getColumnName())) {
					ItemNode temp2 = temp.getRight();
					if (temp2 == null) {
						temp2 = new ItemNode(Item);
						temp.setRight(temp2);
					} else {
						while (temp2.getNext() != null)
							temp2 = temp2.getNext();
						ItemNode newnode = new ItemNode(Item);
						temp2.setNext(newnode);
					}
				}
				temp = temp.getDown();
			}
		}
	}

	public int sizeColumn() {
		int count = 0;
		if (head == null)
			System.out.println("linked list is empty");
		else {
			ColumnNode temp = head;
			while (temp != null) {
				count++;
				temp = temp.getDown();
			}
		}
		return count;
	}

	public int sizeItem(int column) {
		int count = 0;
		if (head == null)
			System.out.println("linked list is empty");
		else {
			ColumnNode temp = head;
			while (temp != null) {
				if (String.valueOf(column).equals(temp.getColumnName())) {
					ItemNode temp2 = temp.getRight();
					while (temp2 != null) {
						count++;
						temp2 = temp2.getNext();
					}

					return count;
				}
				temp = temp.getDown();

			}
		}
		return count;
	}

	public void display() {
		if (head == null)
			System.out.println("linked list is empty");
		else {
			ColumnNode temp = head;
			while (temp != null) {
				System.out.print(temp.getColumnName() + " --> ");
				ItemNode temp2 = temp.getRight();
				while (temp2 != null) {
					System.out.print(temp2.getItemName() + " ");
					temp2 = temp2.getNext();
				}
				temp = temp.getDown();
				System.out.println();
			}
		}
	}

	public Object get(int column, int item) {
		if (column >= this.sizeColumn() || column < 0 || item < 0 || item >= this.sizeItem(column)) {
			// System.out.println("Linked list is empty");
			return null;
		} else {
			ColumnNode temp = head;
			while (temp != null) {
				if (String.valueOf(column).equals(temp.getColumnName())) {
					ItemNode temp2 = temp.getRight();

					for (int i = 0; i < item; i++) {
						temp2 = temp2.getNext();
					}
					return temp2.getItemName();

				}
				temp = temp.getDown();
			}
		}

		return null;
	}

	public Object deleteByIndex(int column, int item) {
		Object deletedElement = null;
		if (column >= this.sizeColumn() || column < 0 || item < 0 || item >= this.sizeItem(column)) {
			// System.out.println("Linked list is empty");
			return null;
		} else {
			ColumnNode temp = head;
			while (temp != null) {
				if (String.valueOf(column).equals(temp.getColumnName())) {
					ItemNode temp2 = temp.getRight();
					ItemNode prev = null;

					for (int i = 0; i < item; i++) {
						prev = temp2;
						temp2 = temp2.getNext();
					}
					if (sizeItem(column) == 1) {
						deletedElement = temp2.getItemName();
						temp.setRight(null);
					} else if (item == 0) {
						deletedElement = temp2.getItemName();
						temp.setRight(temp2.getNext());

					} else if (item == sizeItem(column) - 1) {
						deletedElement = temp2.getItemName();
						prev.setNext(null);

					} else {
						deletedElement = temp2.getItemName();
						prev.setNext(temp2.getNext());
					}
					return deletedElement;

				}
				temp = temp.getDown();
			}
		}

		return deletedElement;
	}

	public int numberOfElement(int column, String element) {
		int count = 0;
		if (head == null)
			System.out.println("list is empty");
		else {
			ColumnNode temp = head;
			while (temp != null) {
				if ((String.valueOf(column)).equals(temp.getColumnName())) {
					ItemNode temp2 = temp.getRight();

					while (temp2 != null) {
						if (temp2.getItemName().equals(element))
							count++;
						temp2 = temp2.getNext();
					}
					return count;
				}
				temp = temp.getDown();
			}

		}
		return count;
	}
	public int columnRealSize(int column) {
		return sizeItem(column)-numberOfElement(column, " ");
	}
	public boolean isColumnSpace(int column) {
		return sizeItem(column) == numberOfElement(column, " ");

	}
}
