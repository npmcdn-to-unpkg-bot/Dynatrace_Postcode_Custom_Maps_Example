package com.gardner.postcodes;

import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.tomcat.util.codec.binary.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 * This class takes the dashboard URL, username and password and parses for the "group" and "count"
 * fields.
 * 
 * It returns postcodes and count data in a Key-Value map.
 * 
 * eg.
 * SL6, 1
 * SL6 4AY, 34
 * NE12 9JA, 12
 */
public class DashboardParser
{
	public static Map<String,String> parse(String strURL, String strUsername, String strPassword)
	{
		Map<String,String> oMap = new HashMap<String,String>();
		
		try
		{
			// Prepares the request
			URL oURL = new URL(strURL);
			URLConnection oConn = oURL.openConnection();
			String userPassword = strUsername + ":" + strPassword;

	        String strEncoding = new String(Base64.encodeBase64(userPassword.getBytes()));
	        Base64.encodeBase64(userPassword.getBytes());

			oConn.setRequestProperty("Authorization", "Basic " + strEncoding);
			
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(oConn.getInputStream());
				
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("transaction");
			
			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
						
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					String strPostcode = eElement.getAttribute("group");
					String strCount = eElement.getAttribute("count");
					
					// More safety - we don't want to plot about empty postcodes. So exclude them.
					if (strPostcode != null && !strPostcode.isEmpty() && strCount != null && !strCount.isEmpty())
					{
						oMap.put(eElement.getAttribute("group"), eElement.getAttribute("count"));
					}
				}
			}
			
			return oMap;
			
		}
		catch (Exception e)
		{
			System.out.println("Exception Caught");
		}
		
		return oMap;
	}
}
