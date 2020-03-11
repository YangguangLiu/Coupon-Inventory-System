package couponInventorySystem;

public class BinarySearchTree {

	private Node root;

	int count = 0 ;	

	public void add(String data) {
		if (root != null) {
			root.add(data);
		} else {
			root = new Node(data);
		}
	}

	public boolean find(String data) {
		return root.find(data);
	}

	class Node {
		private String data;
		private Node left;
		private Node right;

		public Node(String data) {
			this.data = data;
		}
		
		public void add(String data) {
			
			if (this.data.compareTo(data) > 0) {
				if (this.left == null) {
					left = new Node(data);
				} else {					
					left.add(data);
				}
			} else {
				if (this.right == null) {
					right = new Node(data);
				} else {
					right.add(data);
				}
			}
		}

		public boolean find(String data) {
			
			count++; //Count value will increase when the find function called
			
			if (data.equals(this.data)) {
				return true;
			} else if (data.compareTo(this.data) > 0) {				
				if (right == null) {
					return false;
				}
				return right.find(data);
			} else {
				if (left == null) {
					return false;
				}
				return left.find(data);
			}
		}
	}
}
