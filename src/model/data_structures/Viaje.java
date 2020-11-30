package model.data_structures;

import java.util.Date;

public class Viaje 
{
	private double time;
	
	private int bikeId;
	
	private Date start;
	
	private Date stop;
	
	public Viaje(double time, int bike, Date start, Date stop)
	{
		this.time = time;
		this.bikeId = bike;
		this.start = start;
		this.stop = stop;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public int getBikeId() {
		return bikeId;
	}

	public void setBikeId(int bikeId) {
		this.bikeId = bikeId;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getStop() {
		return stop;
	}

	public void setStop(Date stop) {
		this.stop = stop;
	}
	
	
}
