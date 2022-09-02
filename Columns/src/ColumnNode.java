
public class ColumnNode {
	private String ColumnName;
	private ColumnNode down;
	private ItemNode right;

	public ColumnNode(String dataToAdd) {
		ColumnName = dataToAdd;
		down = null;
		right = null;
	}

	public Object getColumnName() {
		return ColumnName;
	}

	public void setColumnName(String data) {
		this.ColumnName = data;
	}

	public ColumnNode getDown() {
		return down;
	}

	public void setDown(ColumnNode down) {
		this.down = down;
	}

	public ItemNode getRight() {
		return right;
	}

	public void setRight(ItemNode right) {
		this.right = right;
	}

}
