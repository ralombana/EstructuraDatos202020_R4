package model.data_structures;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Accident 
{
	
	public static final DateTimeFormatter yyyyMMddHHmmss_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private String id;
	
	private int severity;
	
	private LocalDateTime startTime;

	public Accident(String id, int sev, String date)
	{
		this.id = id;
		this.severity = sev;
		try
		{
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime tmp = LocalDateTime.parse(date, formatter);
			this.startTime = tmp;
			
		}
		catch(Exception e)
		{
			this.startTime = null;
		}
	}
	
	public String getId()
	{
		return id;
	}
	
	public int getSeverity()
	{
		return severity;
	}
	
	public LocalDateTime getStartTime()
	{
		return startTime;
	}
	
	public Date getDate()
	{
		
	    String formatDateTime = startTime.format(yyyyMMddHHmmss_DATE_FORMAT);
	    Date outputDate = null;
		try {
			outputDate = new SimpleDateFormat("yyyy-MM-dd").parse(formatDateTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	    
	    return outputDate;
	}
}
