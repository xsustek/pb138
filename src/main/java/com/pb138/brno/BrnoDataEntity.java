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

}
