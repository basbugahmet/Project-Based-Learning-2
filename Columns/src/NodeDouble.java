public class NodeDouble {
	private Object data;
	private NodeDouble prev;
	private NodeDouble next;

	public NodeDouble(Object dataToAdd) {
		data = dataToAdd;
		prev = null;
		next = null;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public NodeDouble getPrev() {
		return prev;
	}

	public void setPrev(NodeDouble prev) {
		this.prev = prev;
	}

	public NodeDouble getNext() {

		return next;
	}

	public void setNext(NodeDouble next) {
		this.next = next;
	}
}