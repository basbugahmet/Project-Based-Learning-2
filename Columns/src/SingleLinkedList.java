import java.util.Objects;

public class SingleLinkedList {
	private NodeSingle head;

	public void addWithSort(Object dataToAdd) {
		if (head == null) {
			NodeSingle newNodeSingle = new NodeSingle(dataToAdd);
			head = newNodeSingle;
		}

		else if ((int) dataToAdd <= (int) head.getData()) {
			NodeSingle newNodeSingle = new NodeSingle(dataToAdd);
			newNodeSingle.setLink(head);
			head = newNodeSingle;
		} else {
			NodeSingle temp = head;
			NodeSingle previous = null;
			while (temp != null && (int) dataToAdd > (int) temp.getData()) {
				previous = temp;
				temp = temp.getLink();
			}

			if (temp == null) {
				NodeSingle newNodeSingle = new NodeSingle(dataToAdd);
				if (previous != null) {
					previous.setLink(newNodeSingle);
				}
			} else {
				NodeSingle newNodeSingle = new NodeSingle(dataToAdd);
				newNodeSingle.setLink(temp);
				if (previous != null) {
					previous.setLink(newNodeSingle);
				}
			}
		}
	}

	public void add(Object data) {
		if (head == null) {
			NodeSingle newNodeSingle = new NodeSingle(data);
			head = newNodeSingle;
		} else {
			NodeSingle temp = head;
			while (temp.getLink() != null) {
				temp = temp.getLink();
			}
			NodeSingle newNodeSingle = new NodeSingle(data);
			temp.setLink(newNodeSingle);
		}
	}

	public void print() {
		if (head == null) {
			// System.out.println("linked list is empty");
		} else {
			NodeSingle temp = head;
			while (temp != null) {
				System.out.print(temp.getData()+" ");
				temp = temp.getLink();
			}
		}
	}

	public int size() {
		int count = 0;
		if (head == null) {
			// System.out.println("Linked list is empty");
		} else {
			NodeSingle temp = head;
			while (temp != null) {
				count++;
				temp = temp.getLink();
			}
		}
		return count;
	}

	public Object delete(Object dataToDelete) {
		if (head == null) {
			// System.out.println("linked list is empty");
			return null;
		}

		else {
			while (head != null && head.getData().equals(dataToDelete)) {
				head = head.getLink();
			}
			NodeSingle temp = head;
			NodeSingle previous = null;
			while (temp != null) {
				if (temp.getData() == dataToDelete) {
					previous.setLink(temp.getLink());
					temp = previous;
				}
				previous = temp;
				temp = temp.getLink();
			}
		}
		return dataToDelete;
	}

	public boolean search(Object data) {
		boolean flag = false;
		if (head == null) {
			// System.out.println("linked list is empty");
		} else {
			NodeSingle temp = head;
			while (temp != null) {
				if (data == temp.getData()) {
					flag = true;
				}
				temp = temp.getLink();
			}
		}
		return flag;
	}

	public Object get(int index) {
		if (index >= this.size() || index < 0) {
			// System.out.println("Invalid");
			return null;
		}
		NodeSingle nodeSingle = head;
		for (int i = 0; i < index; i++) {
			nodeSingle = nodeSingle.getLink();
		}
		return nodeSingle.getData();
	}

	public Object deleteByIndex(int index) {
		Object deletedElement = null;
		if (this.get(index) != null) {
			deletedElement = this.get(index);
		}
		if (head == null)
			return null;

		NodeSingle temp = head;

		if (index == 0) {
			head = temp.getLink();
			return null;
		}

		for (int i = 0; temp != null && i < index - 1; i++)
			temp = temp.getLink();

		if (temp == null || temp.getLink() == null)
			return null;

		NodeSingle next = temp.getLink().getLink();

		temp.setLink(next);

		return deletedElement;
	}

	public int numberOfElement(String element) {
		int count = 0;
		for (int i = 0; i < this.size(); i++)
			if (Objects.equals(this.get(i), element))
				count++;

		return count;

	}

}
