package com.fireminder.fireminder;


public class Alarm {
	private long id;
	private String destination;
	private long arrivalTime;
	private long deptTime;
	
	
	public void setId(long id) {
		this.id = id;
	}
	public long getId() {
		return id;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getDestination() {
		return destination;
	}
	public void setArrivalTime(long arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public long getArrivalTime() {
		return arrivalTime;
	}
	public void setDeptTime(long deptTime) {
		this.deptTime = deptTime;
	}
	public long getDeptTime() {
		return deptTime;
	}
	

}
