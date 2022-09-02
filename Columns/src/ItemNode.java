
public class ItemNode {
	private String ItemName;
	private ItemNode next;

	public ItemNode(String dataToAdd) {
		ItemName = dataToAdd;
		next = null;
	}

	public String getItemName() {
		return ItemName;
	}

	public void setItemName(String data) {
		this.ItemName = data;
	}

	public ItemNode getNext() {
		return next;
	}

	public void setNext(ItemNode next) {
		this.next = next;
	}

}
