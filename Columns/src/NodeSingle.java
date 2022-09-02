public class NodeSingle {
	Object data;
	NodeSingle link;

	public Object getData() {

		return data;
	}

	public void setData(Object data) {

		this.data = data;
	}

	public NodeSingle getLink() {

		return link;
	}

	public void setLink(NodeSingle link) {

		this.link = link;
	}

	public NodeSingle(Object dataToAdd) {
		data = dataToAdd;
		link = null;
	}

}