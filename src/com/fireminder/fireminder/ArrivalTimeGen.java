package com.fireminder.fireminder;

public class ArrivalTimeGen {
	private Integer ARRIVAL_HOUR;
	private Integer ARRIVAL_MINUTE;
	private Integer ARRIVAL_DAY;
	private Integer ARRIVAL_MONTH;
	private Integer ARRIVAL_YEAR;
	
	ArrivalTimeGen(int minute, int hour, int day, int month, int year){
		ARRIVAL_MINUTE = minute;
		ARRIVAL_HOUR = hour;
		ARRIVAL_DAY = day;
		ARRIVAL_MONTH = month;
		ARRIVAL_YEAR = year;
		
		
	}

	public void setArrivalDay(Integer ARRIVAL_DAY) {
		this.ARRIVAL_DAY = ARRIVAL_DAY;
	}

	public Integer getArrivalDay() {
		return ARRIVAL_DAY;
	}

	public void setArrivalYear(Integer aRRIVAL_YEAR) {
		ARRIVAL_YEAR = aRRIVAL_YEAR;
	}

	public Integer getARRIVAL_YEAR() {
		return ARRIVAL_YEAR;
	}

	public void setARRIVAL_MINUTE(Integer aRRIVAL_MINUTE) {
		ARRIVAL_MINUTE = aRRIVAL_MINUTE;
	}

	public Integer getARRIVAL_MINUTE() {
		return ARRIVAL_MINUTE;
	}

	public void setARRIVAL_HOUR(Integer aRRIVAL_HOUR) {
		ARRIVAL_HOUR = aRRIVAL_HOUR;
	}

	public Integer getARRIVAL_HOUR() {
		return ARRIVAL_HOUR;
	}

	public void setARRIVAL_MONTH(Integer aRRIVAL_MONTH) {
		ARRIVAL_MONTH = aRRIVAL_MONTH;
	}

	public Integer getARRIVAL_MONTH() {
		return ARRIVAL_MONTH;
	}
}
