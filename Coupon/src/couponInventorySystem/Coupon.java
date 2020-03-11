package couponInventorySystem;

public class Coupon {
	private String couponSite; 
    private String productName; 
    private String originalPrice; 
    private double discountRate; // [0, 1)
    private int expirationPeriod;   //days
    private String couponStatus; //unused/redeemed/expired
    private String finalPrice; // 
    
    public String getCouponSite() {
		return couponSite;
	}
    
	public void setCouponSite(String provider) {
		couponSite = provider;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String name) {
		this.productName = name;
	}
	
	public String getOriginalPrice() {
		return originalPrice;
	}
	
	public void setOriginalPrice(String price) {
		this.originalPrice = price;
	}
	
    public double getDiscountRate() {
		return discountRate;
	}
    
	public void setDiscountRate(double rate) {
		this.discountRate = rate;
	}
	
	public int getExpirationPeriod() {
		return expirationPeriod;
	}
	
	public void setExpirationPeriod(int period) {
		expirationPeriod = period;
	}	

    public String getCouponStatus() {
		return couponStatus;
	}
	
	public void setCouponStatus(String status) {
		this.couponStatus = status;
	}
	
    public String getFinalPrice() {
    		return finalPrice;
	}
    
	public void setFinalPrice(String finalprice) {
		this.finalPrice = finalprice;
	}	
	
	@Override
	public String toString() {
		return couponSite + " " + productName + " " + originalPrice + " " + discountRate
				+ " " + expirationPeriod + " " + couponStatus + " " + finalPrice ;
	}
    
}
