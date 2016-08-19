package com.gardner.postcodes;

public class LatLong
{
	private String m_strLatitude = "0.00";
	private String m_strLongitude = "0.00";

	public LatLong(String strLat, String strLong)
	{
		m_strLatitude = strLat;
		m_strLongitude = strLong;
	}
	
	public String getLatitude()
	{
		return m_strLatitude;
	}
	
	public String getLongitude()
	{
		return m_strLongitude;
	}
}
