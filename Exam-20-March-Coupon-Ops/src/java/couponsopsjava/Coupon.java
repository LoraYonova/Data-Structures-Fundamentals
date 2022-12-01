package couponsopsjava;

public class Coupon {

    public String code;
    public int discountPercentage;
    public int validity;
	
	public Website website;

    public Coupon(String code, int discountPercentage, int validity) {
        this.code = code;
        this.discountPercentage = discountPercentage;
        this.validity = validity;

    }

    public String getCode() {
        return code;
    }

    public Coupon setCode(String code) {
        this.code = code;
        return this;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public Coupon setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
        return this;
    }

    public int getValidity() {
        return validity;
    }

    public Coupon setValidity(int validity) {
        this.validity = validity;
        return this;
    }

    public Website getWebsite() {
        return website;
    }

    public Coupon setWebsite(Website website) {
        this.website = website;
        return this;
    }
}
