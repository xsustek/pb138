package com.pb138.brno;

/**
 * Model class for crime statistics
 */
public class BrnoCrimeStatistics {
    private int count;
    private int damage;
    private int street;
    private int withWeaponCount;
    private String region;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getStreet() {
        return street;
    }

    public void setStreet(int street) {
        this.street = street;
    }

    public int getWithWeaponCount() {
        return withWeaponCount;
    }

    public void setWithWeaponCount(int withWeaponCount) {
        this.withWeaponCount = withWeaponCount;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
