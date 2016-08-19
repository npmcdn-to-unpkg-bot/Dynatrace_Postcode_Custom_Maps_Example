<%@page import="java.util.ArrayList"%>
<%@page import="com.gardner.postcodes.Postcode"%>
<%@page import="java.util.List"%>
<%@page import="com.gardner.postcodes.LatLong"%>
<%@page import="com.gardner.postcodes.APIHelper"%>
<%@page import="java.util.Map"%>
<%@page import="com.gardner.postcodes.DashboardParser"%>
<%
/*
 * This file is where the map is rendered
 * Usage: You must pass the URL to the XML version of your dashboard in the query parameter.
 * Dont worry if you forget, the page will warn you.
 */
String strDashboard = "";
String strUser = "";
String strPass = "";

// First let's try and get the dashboard, username & password parameters from the query string
try
{
     strDashboard = request.getParameter("dashboard");
     strUser = request.getParameter("user");
     strPass = request.getParameter("pass");
}
catch (Exception e) {}

// Default to admin / admin
if (strUser == null || strUser.isEmpty()) strUser = "admin";
if (strPass == null || strPass.isEmpty()) strPass = "admin";
	
%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
//This list will eventually hold all our Postcode objects which will include the lat & long data.
List<Postcode> oPostcodeList = new ArrayList<Postcode>();

// The dashboard param is mandatory. If its missing, prompt the user to add it.
if (strDashboard == null || strDashboard.isEmpty()) {%>
<h1>Missing query parameter <strong>?dashboard=http://localhost:8020/rest/management/dashboard/PostcodeDashboard</strong></h1>
<%}
else
{
	/* 
	 * Do processing
	 * 1) Get Dashboard XML. Parse and return K-V Map of Postcode + Count eg. "SL6 4AY","31"
     * 2) For each postcode, call the api.postcodes.io. Parse JSON object and retrieve the latitude & longitude.
     * 3) Build a list of PostCode objects which consist of the:
     *	 - Human readable "SL6"
     *	 - LatLong object
     * 	 - Count
     * 3) Use leaflet.js to build map view.
     */
     
     /* Lets build the Key-Value map of postcode and count
      * Note: We still have no concept of latitude and longitudes yet.
      */
     
     Map<String,String> oPostcodeMap = DashboardParser.parse(strDashboard,strUser,strPass);
     
     // Now go away and look up lat,long date from http://api.postcodes.io/
     oPostcodeList = APIHelper.buildPostcodeList(oPostcodeMap);
}%>

<!DOCTYPE html>
<html>
<head>
	<title>Map Test Tomcat</title>
	<meta charset="utf-8" />

	<link rel="stylesheet" href="https://npmcdn.com/leaflet@1.0.0-rc.2/dist/leaflet.css" />
</head>
<body>
	<div id="mapid" style="width: 1000px; height: 1000px;"></div>

	<script src="https://npmcdn.com/leaflet@1.0.0-rc.2/dist/leaflet.js"></script>
	<script>

		var mymap = L.map('mapid').setView([53.128280, -1.732382], 6);

		L.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
			maxZoom: 18,
			attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, ' +
				'<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
				'Imagery © <a href="http://mapbox.com">Mapbox</a>',
			id: 'mapbox.streets'
		}).addTo(mymap);
<%

// Plot all points.
for (Postcode oCode : oPostcodeList)
{
%>
		L.marker([<%=oCode.getLatitude()%>,<%=oCode.getLongitude()%>]).addTo(mymap);
<%}%>
	</script>
</body>
</html>