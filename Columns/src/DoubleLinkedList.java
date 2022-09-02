public class DoubleLinkedList {
	private NodeDouble head;
	private NodeDouble tail;

	public DoubleLinkedList() {
		head = null;
		tail = null;
	}

	public void add(Object dataToAdd) {
		if (head == null && tail == null) {
			NodeDouble newNode = new NodeDouble(dataToAdd);
			head = newNode;
			tail = newNode;
		} else {
			NodeDouble newNode = new NodeDouble(dataToAdd);
			newNode.setPrev(tail);
			tail.setNext(newNode);
			tail = newNode;
		}
	}

	public void add(Participant data) {
		if (head == null && tail == null) {
			NodeDouble newNode = new NodeDouble(data);
			head = newNode;
			tail = newNode;
		} else {
			NodeDouble newNode = new NodeDouble(data);
			if (data.getScore() >= ((Participant) head.getData()).getScore()) {
				newNode.setNext(head);
				head.setPrev(newNode);
				head = newNode;
			} else {
				NodeDouble temp = head;
				while (temp.getNext() != null
						&& data.getScore() <= ((Participant) temp.getNext().getData()).getScore()) {
					temp = temp.getNext();
				}
				newNode.setPrev(temp);
				newNode.setNext(temp.getNext());
				if (temp.getNext() != null) {
					temp.getNext().setPrev(newNode);
				} else {
					tail = newNode;
				}
				temp.setNext(newNode);
			}
		}
	}

	public int size() {
		int count = 0;
		NodeDouble temp = head;
		while (temp != null) {
			count++;
			temp = temp.getNext();
		}
		return count;
	}

	public void display(int index) {
		NodeDouble current = head;
		if (head == null) {
			System.out.println("linked list is empty");
			return;
		}
		while (current != null && index > 0) {
			System.out.println(((Participant) current.getData()).getName() + "\t\t"
					+ ((Participant) current.getData()).getScore());
			current = current.getNext();
			index--;
		}
	}

	public Object get(int index) {
		NodeDouble node = head;
		if (index >= this.size() || index < 0) {
			return null;
		}
		for (int i = 0; i < index && node != null; i++) {
			node = node.getNext();
		}
		if (node != null) {
			return node.getData();
		}
		return null;
	}
}
