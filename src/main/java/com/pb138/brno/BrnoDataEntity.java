package com.pb138.brno;

public class BrnoDataEntity {
	private String year;
	private String month;
	
	private String region;
	private String damage;
	private String stage;
	private String classification;
	private String onStreet;
	private String weapon;
	
	private String dateOfCrime;
	private String dateResolved;
	
	private String typeOfResolution;
	
	public BrnoDataEntity(String year, String month, String region, String damage, String stage, 
						  String classification, String onStreet, String weapon, String dateOfCrime, 
						  String dateResolved, String typeOfResolution) {
		super();
		this.year = year;
		this.month = month;
		this.region = region;
		this.damage = damage;
		this.stage = stage;
		this.classification = classification;
		this.onStreet = onStreet;
		this.weapon = weapon;
		this.dateOfCrime = dateOfCrime;
		this.dateResolved = dateResolved;
		this.typeOfResolution = typeOfResolution;
	}
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getDamage() {
		return damage;
	}

	public void setDamage(String damage) {
		this.damage = damage;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getOnStreet() {
		return onStreet;
	}

	public void setOnStreet(String onStreet) {
		this.onStreet = onStreet;
	}

	public String getWeapon() {
		return weapon;
	}

	public void setWeapon(String weapon) {
		this.weapon = weapon;
	}

	public String getDateOfCrime() {
		return dateOfCrime;
	}

	public void setDateOfCrime(String dateOfCrime) {
		this.dateOfCrime = dateOfCrime;
	}

	public String getDateResolved() {
		return dateResolved;
	}

	public void setDateResolved(String dateResolved) {
		this.dateResolved = dateResolved;
	}

	public String getTypeOfResolution() {
		return typeOfResolution;
	}

	public void setTypeOfResolution(String typeOfResolution) {
		this.typeOfResolution = typeOfResolution;
	}
}
