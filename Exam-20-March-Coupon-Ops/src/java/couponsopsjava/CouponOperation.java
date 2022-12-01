package couponsopsjava;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class CouponOperation implements ICouponOperation {

    private final Map<Website, List<Coupon>> websiteAndCoupon;
//    private final List<Coupon> coupons;

    public CouponOperation() {
        websiteAndCoupon = new HashMap<>();
//        coupons = new ArrayList<>();
    }

    public void registerSite(Website w) {
        Website website = websiteAndCoupon
                .keySet()
                .stream()
                .filter(web -> web.getDomain().equals(w.getDomain()))
                .findFirst()
                .orElse(null);

        if (website != null) {
            throw new IllegalArgumentException();
        }
        websiteAndCoupon.put(w, new ArrayList<>());
    }

    public void addCoupon(Website w, Coupon c) {

        if (!websiteAndCoupon.containsKey(w))  {
            throw new IllegalArgumentException();
        }

        if (websiteAndCoupon.get(w).contains(c)) {
            throw new IllegalArgumentException();
        }

        websiteAndCoupon.get(w).add(c);

    }

    public Website removeWebsite(String domain) {

        Website website = websiteAndCoupon.keySet().stream().filter(w -> w.getDomain().equals(domain)).findFirst().orElse(null);

        if (website == null) {
            throw new IllegalArgumentException();
        }

        websiteAndCoupon.remove(website);
        return website;
    }

    public Coupon removeCoupon(String code) {
        Coupon coupon = null;
        for (Map.Entry<Website, List<Coupon>> websiteListEntry : websiteAndCoupon.entrySet()) {
            coupon = websiteListEntry.getValue().stream().filter(c -> c.getCode().equals(code)).findFirst().orElse(null);
            if (coupon == null) {
                throw new IllegalArgumentException();
            } else {
                Website key = websiteListEntry.getKey();
                websiteAndCoupon.get(key).remove(coupon);
                break;
            }

        }

        return coupon;
    }

    public boolean exist(Website w) {
        return websiteAndCoupon.containsKey(w);
    }

    public boolean exist(Coupon c) {
        Collection<List<Coupon>> values = websiteAndCoupon.values();
        for (List<Coupon> value : values) {
            if (value.contains(c)) {
                return true;
            }
        }
        return false;
    }

    public Collection<Website> getSites() {
        return websiteAndCoupon.keySet();
    }

    public Collection<Coupon> getCouponsForWebsite(Website w) {

        if (!websiteAndCoupon.containsKey(w)) {
            throw new IllegalArgumentException();
        }

        return websiteAndCoupon.get(w);
    }

    public void useCoupon(Website w, Coupon c) {

        List<Coupon> coupon = websiteAndCoupon.get(w);
        if (!websiteAndCoupon.containsKey(w)) {
            throw new IllegalArgumentException();
        }

        if (!coupon.contains(c)) {
            throw new IllegalArgumentException();
        }

        websiteAndCoupon.get(w).removeAll(coupon);

    }

    public Collection<Coupon> getCouponsOrderedByValidityDescAndDiscountPercentageDesc() {

        Comparator<Coupon> reversed = Comparator.comparing(Coupon::getValidity).thenComparing(Coupon::getDiscountPercentage).reversed();
        List<Coupon> collect = null;

        for (List<Coupon> value : websiteAndCoupon.values()) {
            collect = value.stream().sorted(reversed).collect(Collectors.toList());
        }
        return collect;
    }

    public Collection<Website> getWebsitesOrderedByUserCountAndCouponsCountDesc() {

      return websiteAndCoupon.entrySet().stream().sorted((o1, o2) -> {
            int result = o1.getKey().getUsersCount() - o2.getKey().getUsersCount();
            if (result == 0) {
                result = o2.getValue().size() - o1.getValue().size();
            }
            return result;
        }).map(Map.Entry::getKey)
               .collect(Collectors.toList());




    }
}
