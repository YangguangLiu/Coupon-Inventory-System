package couponInventorySystem;

public class LLNode<T> {
	
		private T info;
		private LLNode<T> link;

		public LLNode(T info){
		      this.info = info;
		      link = null; 
		}
		
		// Set information
		public void setInfo(T info) {
			this.info = info;
		}
		
		// Return information
		public T getInfo() {
			return info;
		}
		
		//Set link
		public void setLink(LLNode<T> link) {
			this.link = link;
		}
		
		//Return link
		public LLNode<T> getLink() {
			return link;
		}
}
