package couponInventorySystem;

public class SortedLinkedList<T> extends UnsortedLinkedList<T> {
	// Add element
	public void addCoupon(T element, String str) {
		LLNode<T> newNode = new LLNode<T>(element);
		location = head;
		while (true) {
			// location is in the last node or isEmpty().
			if (location == null) {
				add(element);
				location = newNode;
				previous = location;
				break;
			}

			// compare the relation of previous element and adding element.
			if (compareTo(location.getInfo(), element, str) == 1) {
				previous = location;
				location = location.getLink();

				if (location == null) {
					previous.setLink(newNode);
					newNode.setLink(null);
					numElements++;
					break;
				}
			// compare the relation of previous element and adding element.
			} else if (compareTo(previous.getInfo(), element, str) == 1) {
				previous.setLink(newNode);
				newNode.setLink(location);
				numElements++;
				break;
			// Add new element into front.
			} else {				
				head = newNode;
				head.setLink(location);
				numElements++;
				break;
			}
		}
	}

	public int compareTo(Object preElement, Object newElement, String str) {
		Coupon preCoupon = (Coupon) preElement;
		Coupon newCoupon = (Coupon) newElement;
		
		if(str.contentEquals("Coupon Provider")) {
			if (preCoupon.getCouponSite().compareTo(newCoupon.getCouponSite()) < 0 ) 
				return 1;
			else
				return 0;
		}
		
		if(str.contentEquals("Product Name")) {
			if (preCoupon.getProductName().compareTo(newCoupon.getProductName()) < 0 ) 
				return 1;
			else
				return 0;
		}
		
		if(str.contentEquals("Original Price")) {
			if (Double.parseDouble(preCoupon.getOriginalPrice()) < Double.parseDouble(newCoupon.getOriginalPrice())) 
				return 1;
			else
				return 0;
		}
			
		if(str.contentEquals("Discount Rate")) {
			if (preCoupon.getDiscountRate()< newCoupon.getDiscountRate())
				return 1;
			else
				return 0;
		}
		
		if(str.contentEquals("Expiration Period")) {
			if (preCoupon.getExpirationPeriod()< newCoupon.getExpirationPeriod())
				return 1;
			else
				return 0;
		}
		
		if(str.contentEquals("Coupon Status")) {
			if (preCoupon.getCouponStatus().compareTo(newCoupon.getCouponStatus()) < 0 )
				return 1;
			else
				return 0;
		}
		
		if(str.contentEquals("Final Price")) {
			if (Double.parseDouble(preCoupon.getFinalPrice()) < Double.parseDouble(newCoupon.getFinalPrice()))
				return 1;
			else
				return 0;
		}
		
		return 0;
		
	}
	
}