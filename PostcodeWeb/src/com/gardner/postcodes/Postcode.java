package com.gardner.postcodes;

public class Postcode
{
	private String m_strPostcode = "";
	private LatLong m_oLatLong = null;
	private String m_strCount = "0";
	
	public Postcode(String strPostcode, LatLong oLatLong, String strCount)
	{
		m_strPostcode = strPostcode;
		m_oLatLong = oLatLong;
		m_strCount = strCount;
	}
	
	public String getPostcode()
	{
		return m_strPostcode;
	}
	
	public String getLatitude()
	{
		return m_oLatLong.getLatitude();
	}
	
	public String getLongitude()
	{
		return m_oLatLong.getLongitude();
	}
	
	public String getCount()
	{
		return m_strCount;
	}

}
