package com.pb138.brno;

/**
 * Model class for crime statistics
 */
public class BrnoCrimeStatistics {
    private int count;
    private int damage;
    private int street;
    private int withWeaponCount;
    private int zlocin;
    private int precin;
    private int planned;
    private int prepared;
    private int executed;
    private int average_time;
    private double average_cases;
    private int cold;
    private int preparators;
    private int population;
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
 
    public int getZlocin() {
        return zlocin;
    }

    public void setZlocin(int zlocin) {
        this.zlocin = zlocin;
    }

    public int getPrecin() {
        return precin;
    }

    public void setPrecin(int precin) {
        this.precin = precin;
    }

    public int getPlanned() {
        return planned;
    }

    public void setPlanned(int planned) {
        this.planned = planned;
    }

    public int getPrepared() {
        return prepared;
    }

    public void setPrepared(int prepared) {
        this.prepared = prepared;
    }

    public int getExecuted() {
        return executed;
    }

    public void setExecuted(int executed) {
        this.executed = executed;
    }

    public int getAverageTime() {
        return average_time;
    }

    public void setAverageTime(int average) {
        this.average_time = average;
    }

    public double getAverageCases() {
        return average_cases;
    }

    public void setAverageCases(double average) {
        this.average_cases = average;
    }

    public int getCold() {
        return cold;
    }

    public void setCold(int cold) {
        this.cold = cold;
    }

    public int getPreparators() {
        return preparators;
    }

    public void setPreparators(int preparators) {
        this.preparators = preparators;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
}
