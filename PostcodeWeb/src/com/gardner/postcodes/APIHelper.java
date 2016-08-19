package com.gardner.postcodes;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class APIHelper
{
	
	private static String CONST_POSTCODES_URI = "postcodes/";
	private static String CONST_OUTCODES_URI = "outcodes/";
	private static String CONST_RESULT = "result";
	private static String CONST_LATITUDE = "latitude";
	private static String CONST_LONGITUDE = "longitude";
	/*
	 * This method takes the raw list of human readable postcodes & count : "SL6 4AY", "23"
	 * and transforms (via API calls) into a list of Postcode objects 
	 */
	public static List<Postcode> buildPostcodeList(Map<String,String> oRawCodes)
	{
		List<Postcode> oList = new ArrayList<Postcode>();
		for (String strPostcode : oRawCodes.keySet())
		{
			StringBuilder oBuilder = new StringBuilder("http://api.postcodes.io/");
			/* 
			 * If we're using a full postcode, look up with http://api.postcodes.io/postcode/[POST CODE]
			 * else use outcode lookup http://api.postcodes.io/outcode/BN23
			 */

			if (strPostcode.contains(" ")) oBuilder.append(CONST_POSTCODES_URI);
			else oBuilder.append(CONST_OUTCODES_URI);
					
			try
			{	
				// Whichever API we're looking up, add the postcode.
				oBuilder.append(strPostcode);
				
				URL oURL = new URL(oBuilder.toString());

				// Parse the URL. Get the result object and from that, get the latitude and longitude.
				JsonParser jp = new JsonParser();
				JsonElement root = jp.parse(new InputStreamReader((InputStream) oURL.getContent())); //Convert the input stream to a json element
				JsonObject rootobj = root.getAsJsonObject(); 
				JsonObject oResObj = rootobj.getAsJsonObject(CONST_RESULT);
			 
				String strLat = oResObj.get(CONST_LATITUDE).getAsString();
				String strLong = oResObj.get(CONST_LONGITUDE).getAsString();
			 
				// Build the Postcode object and add to list
				Postcode oCode = new Postcode(strPostcode, new LatLong(strLat, strLong), oRawCodes.get(strPostcode));
			 
				oList.add(oCode);
				 
			}
			catch (Exception e)
			{
				System.out.println("API Exception caught");
			}
		}
		return oList;
	}

}
