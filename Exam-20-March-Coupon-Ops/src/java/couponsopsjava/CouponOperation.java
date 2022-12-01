package couponsopsjava;

import java.util.*;


public class CouponOperation implements ICouponOperation {

    private final Map<Website, List<Coupon>> websiteAndCoupon;
    private final List<Coupon> coupons;

    public CouponOperation() {
        websiteAndCoupon = new HashMap<>();
        coupons = new ArrayList<>();
    }

    public void registerSite(Website w) {
        if (websiteAndCoupon.containsKey(w)) {
            throw new IllegalArgumentException();
        }
        websiteAndCoupon.put(w, new ArrayList<>());
    }

    public void addCoupon(Website w, Coupon c) {

        if (!websiteAndCoupon.containsKey(w)) {
            throw new IllegalArgumentException();
        }
    }

    public Website removeWebsite(String domain) {
        return null;
    }

    public Coupon removeCoupon(String code) {
        return null;
    }

    public boolean exist(Website w) {
        return false;
    }

    public boolean exist(Coupon c) {
        return false;
    }

    public Collection<Website> getSites() {
        return null;
    }

    public Collection<Coupon> getCouponsForWebsite(Website w) {
        return null;
    }

    public void useCoupon(Website w, Coupon c) {
    }

    public Collection<Coupon> getCouponsOrderedByValidityDescAndDiscountPercentageDesc() {
        return null;
    }

    public Collection<Website> getWebsitesOrderedByUserCountAndCouponsCountDesc() {
        return null;
    }
}
